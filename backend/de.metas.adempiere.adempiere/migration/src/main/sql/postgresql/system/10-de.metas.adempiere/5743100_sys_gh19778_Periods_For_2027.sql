--- Jan.-27

DO
$$
    DECLARE
        year_id numeric;
BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-01-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-01-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:43:13.137Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540355,year_id,TO_TIMESTAMP('2025-01-13 09:43:13.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-01-31','YYYY-MM-DD'),'Y','Jan.-27',1,'S','N',TO_TIMESTAMP('2027-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:43:13.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:13.141Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540355 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:43:13.265Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553352,TO_TIMESTAMP('2025-01-13 09:43:13.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:13.342Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553353,TO_TIMESTAMP('2025-01-13 09:43:13.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:13.429Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553354,TO_TIMESTAMP('2025-01-13 09:43:13.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:13.515Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553355,TO_TIMESTAMP('2025-01-13 09:43:13.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:13.597Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553356,TO_TIMESTAMP('2025-01-13 09:43:13.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:13.697Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553357,TO_TIMESTAMP('2025-01-13 09:43:13.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:13.778Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553358,TO_TIMESTAMP('2025-01-13 09:43:13.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:13.858Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553359,TO_TIMESTAMP('2025-01-13 09:43:13.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:13.940Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553360,TO_TIMESTAMP('2025-01-13 09:43:13.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.020Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553361,TO_TIMESTAMP('2025-01-13 09:43:13.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:13.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.096Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553362,TO_TIMESTAMP('2025-01-13 09:43:14.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.176Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553363,TO_TIMESTAMP('2025-01-13 09:43:14.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.256Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553364,TO_TIMESTAMP('2025-01-13 09:43:14.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.339Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553365,TO_TIMESTAMP('2025-01-13 09:43:14.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.421Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553366,TO_TIMESTAMP('2025-01-13 09:43:14.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.503Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553367,TO_TIMESTAMP('2025-01-13 09:43:14.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.585Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553368,TO_TIMESTAMP('2025-01-13 09:43:14.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.663Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553369,TO_TIMESTAMP('2025-01-13 09:43:14.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.745Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553370,TO_TIMESTAMP('2025-01-13 09:43:14.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.834Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553371,TO_TIMESTAMP('2025-01-13 09:43:14.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:14.914Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553372,TO_TIMESTAMP('2025-01-13 09:43:14.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.010Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553373,TO_TIMESTAMP('2025-01-13 09:43:14.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:14.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.102Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553374,TO_TIMESTAMP('2025-01-13 09:43:15.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.186Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553375,TO_TIMESTAMP('2025-01-13 09:43:15.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.264Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553376,TO_TIMESTAMP('2025-01-13 09:43:15.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.348Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553377,TO_TIMESTAMP('2025-01-13 09:43:15.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.433Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553378,TO_TIMESTAMP('2025-01-13 09:43:15.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.514Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553379,TO_TIMESTAMP('2025-01-13 09:43:15.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.598Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553380,TO_TIMESTAMP('2025-01-13 09:43:15.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.690Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553381,TO_TIMESTAMP('2025-01-13 09:43:15.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.774Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553382,TO_TIMESTAMP('2025-01-13 09:43:15.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.862Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553383,TO_TIMESTAMP('2025-01-13 09:43:15.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:15.947Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553384,TO_TIMESTAMP('2025-01-13 09:43:15.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:16.032Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553385,TO_TIMESTAMP('2025-01-13 09:43:15.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:15.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:16.113Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553386,TO_TIMESTAMP('2025-01-13 09:43:16.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:16.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:16.192Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553387,TO_TIMESTAMP('2025-01-13 09:43:16.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:16.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:16.277Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553388,TO_TIMESTAMP('2025-01-13 09:43:16.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:16.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:16.365Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553389,TO_TIMESTAMP('2025-01-13 09:43:16.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:16.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:16.460Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553390,TO_TIMESTAMP('2025-01-13 09:43:16.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:16.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:16.545Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540355,553391,TO_TIMESTAMP('2025-01-13 09:43:16.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:16.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
END
$$;

--- Feb.-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-02-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-02-28','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:43:42.262Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540356,year_id,TO_TIMESTAMP('2025-01-13 09:43:42.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-02-28','YYYY-MM-DD'),'Y','Feb.-27',2,'S','N',TO_TIMESTAMP('2027-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:43:42.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:42.268Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540356 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:43:42.422Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553392,TO_TIMESTAMP('2025-01-13 09:43:42.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:42.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:42.534Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553393,TO_TIMESTAMP('2025-01-13 09:43:42.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:42.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:42.617Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553394,TO_TIMESTAMP('2025-01-13 09:43:42.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:42.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:42.700Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553395,TO_TIMESTAMP('2025-01-13 09:43:42.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:42.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:42.787Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553396,TO_TIMESTAMP('2025-01-13 09:43:42.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:42.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:42.873Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553397,TO_TIMESTAMP('2025-01-13 09:43:42.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:42.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:42.964Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553398,TO_TIMESTAMP('2025-01-13 09:43:42.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:42.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.054Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553399,TO_TIMESTAMP('2025-01-13 09:43:42.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:42.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.142Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553400,TO_TIMESTAMP('2025-01-13 09:43:43.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.222Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553401,TO_TIMESTAMP('2025-01-13 09:43:43.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.306Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553402,TO_TIMESTAMP('2025-01-13 09:43:43.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.398Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553403,TO_TIMESTAMP('2025-01-13 09:43:43.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.482Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553404,TO_TIMESTAMP('2025-01-13 09:43:43.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.566Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553405,TO_TIMESTAMP('2025-01-13 09:43:43.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.651Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553406,TO_TIMESTAMP('2025-01-13 09:43:43.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.733Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553407,TO_TIMESTAMP('2025-01-13 09:43:43.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.819Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553408,TO_TIMESTAMP('2025-01-13 09:43:43.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.914Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553409,TO_TIMESTAMP('2025-01-13 09:43:43.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:43.996Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553410,TO_TIMESTAMP('2025-01-13 09:43:43.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:43.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.086Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553411,TO_TIMESTAMP('2025-01-13 09:43:44.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.182Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553412,TO_TIMESTAMP('2025-01-13 09:43:44.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.270Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553413,TO_TIMESTAMP('2025-01-13 09:43:44.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.348Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553414,TO_TIMESTAMP('2025-01-13 09:43:44.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.435Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553415,TO_TIMESTAMP('2025-01-13 09:43:44.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.512Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553416,TO_TIMESTAMP('2025-01-13 09:43:44.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.590Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553417,TO_TIMESTAMP('2025-01-13 09:43:44.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.682Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553418,TO_TIMESTAMP('2025-01-13 09:43:44.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.766Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553419,TO_TIMESTAMP('2025-01-13 09:43:44.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.850Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553420,TO_TIMESTAMP('2025-01-13 09:43:44.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:44.932Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553421,TO_TIMESTAMP('2025-01-13 09:43:44.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.009Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553422,TO_TIMESTAMP('2025-01-13 09:43:44.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:44.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.089Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553423,TO_TIMESTAMP('2025-01-13 09:43:45.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:45.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.173Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553424,TO_TIMESTAMP('2025-01-13 09:43:45.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:45.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.261Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553425,TO_TIMESTAMP('2025-01-13 09:43:45.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:45.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.346Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553426,TO_TIMESTAMP('2025-01-13 09:43:45.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:45.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.429Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553427,TO_TIMESTAMP('2025-01-13 09:43:45.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:45.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.512Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553428,TO_TIMESTAMP('2025-01-13 09:43:45.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:45.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.595Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553429,TO_TIMESTAMP('2025-01-13 09:43:45.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:45.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.684Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553430,TO_TIMESTAMP('2025-01-13 09:43:45.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:45.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:43:45.771Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540356,553431,TO_TIMESTAMP('2025-01-13 09:43:45.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:43:45.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- März-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-03-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-03-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:44:10.216Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540357,year_id,TO_TIMESTAMP('2025-01-13 09:44:10.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-03-31','YYYY-MM-DD'),'Y','März-27',3,'S','N',TO_TIMESTAMP('2027-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:44:10.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:10.226Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540357 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:44:10.382Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553432,TO_TIMESTAMP('2025-01-13 09:44:10.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:10.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:10.475Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553433,TO_TIMESTAMP('2025-01-13 09:44:10.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:10.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:10.564Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553434,TO_TIMESTAMP('2025-01-13 09:44:10.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:10.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:10.663Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553435,TO_TIMESTAMP('2025-01-13 09:44:10.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:10.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:10.753Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553436,TO_TIMESTAMP('2025-01-13 09:44:10.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:10.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:10.842Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553437,TO_TIMESTAMP('2025-01-13 09:44:10.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:10.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:10.947Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553438,TO_TIMESTAMP('2025-01-13 09:44:10.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:10.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.044Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553439,TO_TIMESTAMP('2025-01-13 09:44:10.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:10.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.127Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553440,TO_TIMESTAMP('2025-01-13 09:44:11.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.223Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553441,TO_TIMESTAMP('2025-01-13 09:44:11.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.308Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553442,TO_TIMESTAMP('2025-01-13 09:44:11.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.403Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553443,TO_TIMESTAMP('2025-01-13 09:44:11.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.507Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553444,TO_TIMESTAMP('2025-01-13 09:44:11.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.606Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553445,TO_TIMESTAMP('2025-01-13 09:44:11.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.697Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553446,TO_TIMESTAMP('2025-01-13 09:44:11.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.791Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553447,TO_TIMESTAMP('2025-01-13 09:44:11.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.881Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553448,TO_TIMESTAMP('2025-01-13 09:44:11.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:11.973Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553449,TO_TIMESTAMP('2025-01-13 09:44:11.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.066Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553450,TO_TIMESTAMP('2025-01-13 09:44:11.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:11.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.159Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553451,TO_TIMESTAMP('2025-01-13 09:44:12.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.247Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553452,TO_TIMESTAMP('2025-01-13 09:44:12.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.338Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553453,TO_TIMESTAMP('2025-01-13 09:44:12.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.437Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553454,TO_TIMESTAMP('2025-01-13 09:44:12.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.533Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553455,TO_TIMESTAMP('2025-01-13 09:44:12.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.628Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553456,TO_TIMESTAMP('2025-01-13 09:44:12.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.716Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553457,TO_TIMESTAMP('2025-01-13 09:44:12.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.817Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553458,TO_TIMESTAMP('2025-01-13 09:44:12.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:12.914Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553459,TO_TIMESTAMP('2025-01-13 09:44:12.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.015Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553460,TO_TIMESTAMP('2025-01-13 09:44:12.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:12.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.116Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553461,TO_TIMESTAMP('2025-01-13 09:44:13.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.210Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553462,TO_TIMESTAMP('2025-01-13 09:44:13.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.305Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553463,TO_TIMESTAMP('2025-01-13 09:44:13.214000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.214000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.395Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553464,TO_TIMESTAMP('2025-01-13 09:44:13.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.484Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553465,TO_TIMESTAMP('2025-01-13 09:44:13.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.576Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553466,TO_TIMESTAMP('2025-01-13 09:44:13.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.668Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553467,TO_TIMESTAMP('2025-01-13 09:44:13.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.764Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553468,TO_TIMESTAMP('2025-01-13 09:44:13.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.858Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553469,TO_TIMESTAMP('2025-01-13 09:44:13.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:13.947Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553470,TO_TIMESTAMP('2025-01-13 09:44:13.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:14.042Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540357,553471,TO_TIMESTAMP('2025-01-13 09:44:13.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:13.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Apr.-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-04-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-04-30','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:44:38.960Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540358,year_id,TO_TIMESTAMP('2025-01-13 09:44:38.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-04-30','YYYY-MM-DD'),'Y','Apr.-27',4,'S','N',TO_TIMESTAMP('2027-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:44:38.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:38.964Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540358 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:44:39.110Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553472,TO_TIMESTAMP('2025-01-13 09:44:38.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:38.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:39.206Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553473,TO_TIMESTAMP('2025-01-13 09:44:39.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:39.295Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553474,TO_TIMESTAMP('2025-01-13 09:44:39.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:39.379Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553475,TO_TIMESTAMP('2025-01-13 09:44:39.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:39.472Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553476,TO_TIMESTAMP('2025-01-13 09:44:39.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:39.558Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553477,TO_TIMESTAMP('2025-01-13 09:44:39.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:39.653Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553478,TO_TIMESTAMP('2025-01-13 09:44:39.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:39.748Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553479,TO_TIMESTAMP('2025-01-13 09:44:39.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:39.841Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553480,TO_TIMESTAMP('2025-01-13 09:44:39.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:39.926Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553481,TO_TIMESTAMP('2025-01-13 09:44:39.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.012Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553482,TO_TIMESTAMP('2025-01-13 09:44:39.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:39.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.098Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553483,TO_TIMESTAMP('2025-01-13 09:44:40.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.190Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553484,TO_TIMESTAMP('2025-01-13 09:44:40.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.278Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553485,TO_TIMESTAMP('2025-01-13 09:44:40.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.364Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553486,TO_TIMESTAMP('2025-01-13 09:44:40.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.453Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553487,TO_TIMESTAMP('2025-01-13 09:44:40.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.534Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553488,TO_TIMESTAMP('2025-01-13 09:44:40.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.634Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553489,TO_TIMESTAMP('2025-01-13 09:44:40.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.729Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553490,TO_TIMESTAMP('2025-01-13 09:44:40.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.835Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553491,TO_TIMESTAMP('2025-01-13 09:44:40.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:40.923Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553492,TO_TIMESTAMP('2025-01-13 09:44:40.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.013Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553493,TO_TIMESTAMP('2025-01-13 09:44:40.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:40.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.114Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553494,TO_TIMESTAMP('2025-01-13 09:44:41.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.198Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553495,TO_TIMESTAMP('2025-01-13 09:44:41.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.288Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553496,TO_TIMESTAMP('2025-01-13 09:44:41.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.379Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553497,TO_TIMESTAMP('2025-01-13 09:44:41.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.472Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553498,TO_TIMESTAMP('2025-01-13 09:44:41.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.561Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553499,TO_TIMESTAMP('2025-01-13 09:44:41.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.656Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553500,TO_TIMESTAMP('2025-01-13 09:44:41.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.745Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553501,TO_TIMESTAMP('2025-01-13 09:44:41.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.834Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553502,TO_TIMESTAMP('2025-01-13 09:44:41.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:41.924Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553503,TO_TIMESTAMP('2025-01-13 09:44:41.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:42.015Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553504,TO_TIMESTAMP('2025-01-13 09:44:41.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:41.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:42.097Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553505,TO_TIMESTAMP('2025-01-13 09:44:42.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:42.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:42.184Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553506,TO_TIMESTAMP('2025-01-13 09:44:42.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:42.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:42.277Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553507,TO_TIMESTAMP('2025-01-13 09:44:42.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:42.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:42.365Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553508,TO_TIMESTAMP('2025-01-13 09:44:42.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:42.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:42.446Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553509,TO_TIMESTAMP('2025-01-13 09:44:42.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:42.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:42.551Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553510,TO_TIMESTAMP('2025-01-13 09:44:42.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:42.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:44:42.640Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540358,553511,TO_TIMESTAMP('2025-01-13 09:44:42.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:44:42.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Mai-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-05-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-05-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:45:22.301Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540359,year_id,TO_TIMESTAMP('2025-01-13 09:45:22.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-05-31','YYYY-MM-DD'),'Y','Mai-27',5,'S','N',TO_TIMESTAMP('2027-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:45:22.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:22.305Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540359 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:45:22.488Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553512,TO_TIMESTAMP('2025-01-13 09:45:22.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:22.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:22.578Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553513,TO_TIMESTAMP('2025-01-13 09:45:22.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:22.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:22.675Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553514,TO_TIMESTAMP('2025-01-13 09:45:22.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:22.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:22.764Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553515,TO_TIMESTAMP('2025-01-13 09:45:22.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:22.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:22.875Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553516,TO_TIMESTAMP('2025-01-13 09:45:22.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:22.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:22.982Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553517,TO_TIMESTAMP('2025-01-13 09:45:22.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:22.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.078Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553518,TO_TIMESTAMP('2025-01-13 09:45:22.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:22.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.165Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553519,TO_TIMESTAMP('2025-01-13 09:45:23.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.253Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553520,TO_TIMESTAMP('2025-01-13 09:45:23.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.347Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553521,TO_TIMESTAMP('2025-01-13 09:45:23.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.441Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553522,TO_TIMESTAMP('2025-01-13 09:45:23.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.536Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553523,TO_TIMESTAMP('2025-01-13 09:45:23.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.623Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553524,TO_TIMESTAMP('2025-01-13 09:45:23.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.714Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553525,TO_TIMESTAMP('2025-01-13 09:45:23.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.800Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553526,TO_TIMESTAMP('2025-01-13 09:45:23.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.891Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553527,TO_TIMESTAMP('2025-01-13 09:45:23.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:23.996Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553528,TO_TIMESTAMP('2025-01-13 09:45:23.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.084Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553529,TO_TIMESTAMP('2025-01-13 09:45:23.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:23.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.179Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553530,TO_TIMESTAMP('2025-01-13 09:45:24.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.278Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553531,TO_TIMESTAMP('2025-01-13 09:45:24.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.371Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553532,TO_TIMESTAMP('2025-01-13 09:45:24.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.469Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553533,TO_TIMESTAMP('2025-01-13 09:45:24.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.560Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553534,TO_TIMESTAMP('2025-01-13 09:45:24.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.649Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553535,TO_TIMESTAMP('2025-01-13 09:45:24.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.748Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553536,TO_TIMESTAMP('2025-01-13 09:45:24.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.835Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553537,TO_TIMESTAMP('2025-01-13 09:45:24.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:24.938Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553538,TO_TIMESTAMP('2025-01-13 09:45:24.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.037Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553539,TO_TIMESTAMP('2025-01-13 09:45:24.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:24.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.133Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553540,TO_TIMESTAMP('2025-01-13 09:45:25.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.231Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553541,TO_TIMESTAMP('2025-01-13 09:45:25.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.326Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553542,TO_TIMESTAMP('2025-01-13 09:45:25.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.432Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553543,TO_TIMESTAMP('2025-01-13 09:45:25.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.520Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553544,TO_TIMESTAMP('2025-01-13 09:45:25.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.620Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553545,TO_TIMESTAMP('2025-01-13 09:45:25.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.715Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553546,TO_TIMESTAMP('2025-01-13 09:45:25.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.804Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553547,TO_TIMESTAMP('2025-01-13 09:45:25.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.894Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553548,TO_TIMESTAMP('2025-01-13 09:45:25.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:25.989Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553549,TO_TIMESTAMP('2025-01-13 09:45:25.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:26.077Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553550,TO_TIMESTAMP('2025-01-13 09:45:25.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:25.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:26.172Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540359,553551,TO_TIMESTAMP('2025-01-13 09:45:26.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:26.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Juni-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-06-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-06-30','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:45:55.533Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540360,year_id,TO_TIMESTAMP('2025-01-13 09:45:55.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-06-30','YYYY-MM-DD'),'Y','Juni-27',6,'S','N',TO_TIMESTAMP('2027-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:45:55.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:55.537Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540360 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:45:55.667Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553552,TO_TIMESTAMP('2025-01-13 09:45:55.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:55.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:55.754Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553553,TO_TIMESTAMP('2025-01-13 09:45:55.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:55.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:55.841Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553554,TO_TIMESTAMP('2025-01-13 09:45:55.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:55.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:55.926Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553555,TO_TIMESTAMP('2025-01-13 09:45:55.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:55.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.012Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553556,TO_TIMESTAMP('2025-01-13 09:45:55.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:55.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.099Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553557,TO_TIMESTAMP('2025-01-13 09:45:56.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.178Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553558,TO_TIMESTAMP('2025-01-13 09:45:56.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.258Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553559,TO_TIMESTAMP('2025-01-13 09:45:56.180000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.180000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.347Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553560,TO_TIMESTAMP('2025-01-13 09:45:56.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.435Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553561,TO_TIMESTAMP('2025-01-13 09:45:56.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.519Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553562,TO_TIMESTAMP('2025-01-13 09:45:56.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.602Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553563,TO_TIMESTAMP('2025-01-13 09:45:56.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.693Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553564,TO_TIMESTAMP('2025-01-13 09:45:56.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.788Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553565,TO_TIMESTAMP('2025-01-13 09:45:56.696000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.696000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.867Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553566,TO_TIMESTAMP('2025-01-13 09:45:56.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:56.949Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553567,TO_TIMESTAMP('2025-01-13 09:45:56.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.031Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553568,TO_TIMESTAMP('2025-01-13 09:45:56.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:56.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.118Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553569,TO_TIMESTAMP('2025-01-13 09:45:57.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.209Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553570,TO_TIMESTAMP('2025-01-13 09:45:57.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.302Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553571,TO_TIMESTAMP('2025-01-13 09:45:57.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.382Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553572,TO_TIMESTAMP('2025-01-13 09:45:57.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.464Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553573,TO_TIMESTAMP('2025-01-13 09:45:57.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.549Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553574,TO_TIMESTAMP('2025-01-13 09:45:57.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.631Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553575,TO_TIMESTAMP('2025-01-13 09:45:57.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.725Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553576,TO_TIMESTAMP('2025-01-13 09:45:57.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.836Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553577,TO_TIMESTAMP('2025-01-13 09:45:57.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.915Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553578,TO_TIMESTAMP('2025-01-13 09:45:57.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:57.995Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553579,TO_TIMESTAMP('2025-01-13 09:45:57.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.073Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553580,TO_TIMESTAMP('2025-01-13 09:45:57.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:57.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.154Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553581,TO_TIMESTAMP('2025-01-13 09:45:58.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.272Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553582,TO_TIMESTAMP('2025-01-13 09:45:58.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.355Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553583,TO_TIMESTAMP('2025-01-13 09:45:58.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.444Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553584,TO_TIMESTAMP('2025-01-13 09:45:58.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.532Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553585,TO_TIMESTAMP('2025-01-13 09:45:58.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.614Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553586,TO_TIMESTAMP('2025-01-13 09:45:58.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.701Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553587,TO_TIMESTAMP('2025-01-13 09:45:58.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.788Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553588,TO_TIMESTAMP('2025-01-13 09:45:58.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.871Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553589,TO_TIMESTAMP('2025-01-13 09:45:58.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:58.955Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553590,TO_TIMESTAMP('2025-01-13 09:45:58.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:45:59.049Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540360,553591,TO_TIMESTAMP('2025-01-13 09:45:58.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:45:58.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Juli-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-07-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-07-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:46:35.875Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540361,year_id,TO_TIMESTAMP('2025-01-13 09:46:35.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-07-31','YYYY-MM-DD'),'Y','Juli-27',7,'S','N',TO_TIMESTAMP('2027-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:46:35.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:35.877Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540361 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:46:36.017Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553592,TO_TIMESTAMP('2025-01-13 09:46:35.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:35.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.096Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553593,TO_TIMESTAMP('2025-01-13 09:46:36.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.177Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553594,TO_TIMESTAMP('2025-01-13 09:46:36.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.256Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553595,TO_TIMESTAMP('2025-01-13 09:46:36.180000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.180000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.340Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553596,TO_TIMESTAMP('2025-01-13 09:46:36.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.420Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553597,TO_TIMESTAMP('2025-01-13 09:46:36.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.498Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553598,TO_TIMESTAMP('2025-01-13 09:46:36.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.575Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553599,TO_TIMESTAMP('2025-01-13 09:46:36.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.657Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553600,TO_TIMESTAMP('2025-01-13 09:46:36.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.740Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553601,TO_TIMESTAMP('2025-01-13 09:46:36.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.823Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553602,TO_TIMESTAMP('2025-01-13 09:46:36.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.902Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553603,TO_TIMESTAMP('2025-01-13 09:46:36.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:36.975Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553604,TO_TIMESTAMP('2025-01-13 09:46:36.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.056Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553605,TO_TIMESTAMP('2025-01-13 09:46:36.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:36.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.137Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553606,TO_TIMESTAMP('2025-01-13 09:46:37.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.223Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553607,TO_TIMESTAMP('2025-01-13 09:46:37.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.313Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553608,TO_TIMESTAMP('2025-01-13 09:46:37.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.396Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553609,TO_TIMESTAMP('2025-01-13 09:46:37.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.482Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553610,TO_TIMESTAMP('2025-01-13 09:46:37.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.569Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553611,TO_TIMESTAMP('2025-01-13 09:46:37.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.656Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553612,TO_TIMESTAMP('2025-01-13 09:46:37.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.757Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553613,TO_TIMESTAMP('2025-01-13 09:46:37.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.851Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553614,TO_TIMESTAMP('2025-01-13 09:46:37.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:37.933Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553615,TO_TIMESTAMP('2025-01-13 09:46:37.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.026Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553616,TO_TIMESTAMP('2025-01-13 09:46:37.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:37.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.113Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553617,TO_TIMESTAMP('2025-01-13 09:46:38.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.194Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553618,TO_TIMESTAMP('2025-01-13 09:46:38.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.287Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553619,TO_TIMESTAMP('2025-01-13 09:46:38.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.368Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553620,TO_TIMESTAMP('2025-01-13 09:46:38.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.450Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553621,TO_TIMESTAMP('2025-01-13 09:46:38.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.533Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553622,TO_TIMESTAMP('2025-01-13 09:46:38.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.618Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553623,TO_TIMESTAMP('2025-01-13 09:46:38.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.700Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553624,TO_TIMESTAMP('2025-01-13 09:46:38.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.780Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553625,TO_TIMESTAMP('2025-01-13 09:46:38.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.860Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553626,TO_TIMESTAMP('2025-01-13 09:46:38.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:38.942Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553627,TO_TIMESTAMP('2025-01-13 09:46:38.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:39.023Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553628,TO_TIMESTAMP('2025-01-13 09:46:38.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:38.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:39.099Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553629,TO_TIMESTAMP('2025-01-13 09:46:39.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:39.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:39.179Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553630,TO_TIMESTAMP('2025-01-13 09:46:39.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:39.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:46:39.264Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540361,553631,TO_TIMESTAMP('2025-01-13 09:46:39.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:46:39.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Aug.-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-08-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-08-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:47:11.563Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540362,year_id,TO_TIMESTAMP('2025-01-13 09:47:11.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-08-31','YYYY-MM-DD'),'Y','Aug.-27',8,'S','N',TO_TIMESTAMP('2027-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:47:11.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:11.567Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540362 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:47:11.705Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553632,TO_TIMESTAMP('2025-01-13 09:47:11.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:11.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:11.789Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553633,TO_TIMESTAMP('2025-01-13 09:47:11.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:11.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:11.878Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553634,TO_TIMESTAMP('2025-01-13 09:47:11.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:11.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:11.978Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553635,TO_TIMESTAMP('2025-01-13 09:47:11.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:11.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.063Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553636,TO_TIMESTAMP('2025-01-13 09:47:11.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:11.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.182Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553637,TO_TIMESTAMP('2025-01-13 09:47:12.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.268Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553638,TO_TIMESTAMP('2025-01-13 09:47:12.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.351Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553639,TO_TIMESTAMP('2025-01-13 09:47:12.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.434Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553640,TO_TIMESTAMP('2025-01-13 09:47:12.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.519Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553641,TO_TIMESTAMP('2025-01-13 09:47:12.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.605Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553642,TO_TIMESTAMP('2025-01-13 09:47:12.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.722Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553643,TO_TIMESTAMP('2025-01-13 09:47:12.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.812Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553644,TO_TIMESTAMP('2025-01-13 09:47:12.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.902Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553645,TO_TIMESTAMP('2025-01-13 09:47:12.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:12.989Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553646,TO_TIMESTAMP('2025-01-13 09:47:12.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.075Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553647,TO_TIMESTAMP('2025-01-13 09:47:12.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:12.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.157Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553648,TO_TIMESTAMP('2025-01-13 09:47:13.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.244Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553649,TO_TIMESTAMP('2025-01-13 09:47:13.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.332Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553650,TO_TIMESTAMP('2025-01-13 09:47:13.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.425Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553651,TO_TIMESTAMP('2025-01-13 09:47:13.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.510Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553652,TO_TIMESTAMP('2025-01-13 09:47:13.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.597Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553653,TO_TIMESTAMP('2025-01-13 09:47:13.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.677Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553654,TO_TIMESTAMP('2025-01-13 09:47:13.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.768Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553655,TO_TIMESTAMP('2025-01-13 09:47:13.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.860Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553656,TO_TIMESTAMP('2025-01-13 09:47:13.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:13.959Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553657,TO_TIMESTAMP('2025-01-13 09:47:13.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.054Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553658,TO_TIMESTAMP('2025-01-13 09:47:13.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:13.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.139Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553659,TO_TIMESTAMP('2025-01-13 09:47:14.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.233Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553660,TO_TIMESTAMP('2025-01-13 09:47:14.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.318Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553661,TO_TIMESTAMP('2025-01-13 09:47:14.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.404Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553662,TO_TIMESTAMP('2025-01-13 09:47:14.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.487Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553663,TO_TIMESTAMP('2025-01-13 09:47:14.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.570Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553664,TO_TIMESTAMP('2025-01-13 09:47:14.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.661Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553665,TO_TIMESTAMP('2025-01-13 09:47:14.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.738Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553666,TO_TIMESTAMP('2025-01-13 09:47:14.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.824Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553667,TO_TIMESTAMP('2025-01-13 09:47:14.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.905Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553668,TO_TIMESTAMP('2025-01-13 09:47:14.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:14.996Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553669,TO_TIMESTAMP('2025-01-13 09:47:14.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:14.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:15.085Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553670,TO_TIMESTAMP('2025-01-13 09:47:15.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:15.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:15.175Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540362,553671,TO_TIMESTAMP('2025-01-13 09:47:15.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:15.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Sept.-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-09-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-09-30','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:47:47.188Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540363,year_id,TO_TIMESTAMP('2025-01-13 09:47:47.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-09-30','YYYY-MM-DD'),'Y','Sept.-27',9,'S','N',TO_TIMESTAMP('2027-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:47:47.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:47.190Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540363 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:47:47.325Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553672,TO_TIMESTAMP('2025-01-13 09:47:47.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:47.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:47.404Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553673,TO_TIMESTAMP('2025-01-13 09:47:47.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:47.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:47.511Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553674,TO_TIMESTAMP('2025-01-13 09:47:47.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:47.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:47.594Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553675,TO_TIMESTAMP('2025-01-13 09:47:47.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:47.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:47.688Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553676,TO_TIMESTAMP('2025-01-13 09:47:47.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:47.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:47.774Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553677,TO_TIMESTAMP('2025-01-13 09:47:47.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:47.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:47.871Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553678,TO_TIMESTAMP('2025-01-13 09:47:47.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:47.778000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:47.970Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553679,TO_TIMESTAMP('2025-01-13 09:47:47.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:47.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.059Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553680,TO_TIMESTAMP('2025-01-13 09:47:47.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:47.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.148Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553681,TO_TIMESTAMP('2025-01-13 09:47:48.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.236Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553682,TO_TIMESTAMP('2025-01-13 09:47:48.153000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.153000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.335Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553683,TO_TIMESTAMP('2025-01-13 09:47:48.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.422Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553684,TO_TIMESTAMP('2025-01-13 09:47:48.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.507Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553685,TO_TIMESTAMP('2025-01-13 09:47:48.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.593Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553686,TO_TIMESTAMP('2025-01-13 09:47:48.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.673Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553687,TO_TIMESTAMP('2025-01-13 09:47:48.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.766Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553688,TO_TIMESTAMP('2025-01-13 09:47:48.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.859Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553689,TO_TIMESTAMP('2025-01-13 09:47:48.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:48.934Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553690,TO_TIMESTAMP('2025-01-13 09:47:48.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.031Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553691,TO_TIMESTAMP('2025-01-13 09:47:48.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:48.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.127Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553692,TO_TIMESTAMP('2025-01-13 09:47:49.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.214Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553693,TO_TIMESTAMP('2025-01-13 09:47:49.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.298Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553694,TO_TIMESTAMP('2025-01-13 09:47:49.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.383Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553695,TO_TIMESTAMP('2025-01-13 09:47:49.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.472Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553696,TO_TIMESTAMP('2025-01-13 09:47:49.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.552Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553697,TO_TIMESTAMP('2025-01-13 09:47:49.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.647Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553698,TO_TIMESTAMP('2025-01-13 09:47:49.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.737Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553699,TO_TIMESTAMP('2025-01-13 09:47:49.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.822Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553700,TO_TIMESTAMP('2025-01-13 09:47:49.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.907Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553701,TO_TIMESTAMP('2025-01-13 09:47:49.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:49.992Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553702,TO_TIMESTAMP('2025-01-13 09:47:49.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:50.076Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553703,TO_TIMESTAMP('2025-01-13 09:47:49.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:49.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:50.164Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553704,TO_TIMESTAMP('2025-01-13 09:47:50.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:50.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:50.253Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553705,TO_TIMESTAMP('2025-01-13 09:47:50.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:50.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:50.331Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553706,TO_TIMESTAMP('2025-01-13 09:47:50.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:50.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:50.420Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553707,TO_TIMESTAMP('2025-01-13 09:47:50.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:50.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:50.525Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553708,TO_TIMESTAMP('2025-01-13 09:47:50.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:50.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:50.607Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553709,TO_TIMESTAMP('2025-01-13 09:47:50.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:50.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:50.690Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553710,TO_TIMESTAMP('2025-01-13 09:47:50.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:50.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:47:50.772Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540363,553711,TO_TIMESTAMP('2025-01-13 09:47:50.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:47:50.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Okt.-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-10-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-10-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:48:18.630Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540364,year_id,TO_TIMESTAMP('2025-01-13 09:48:18.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-10-31','YYYY-MM-DD'),'Y','Okt.-27',10,'S','N',TO_TIMESTAMP('2027-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:48:18.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:18.634Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540364 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:48:18.769Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553712,TO_TIMESTAMP('2025-01-13 09:48:18.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:18.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:18.859Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553713,TO_TIMESTAMP('2025-01-13 09:48:18.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:18.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:18.946Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553714,TO_TIMESTAMP('2025-01-13 09:48:18.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:18.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.032Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553715,TO_TIMESTAMP('2025-01-13 09:48:18.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:18.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.122Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553716,TO_TIMESTAMP('2025-01-13 09:48:19.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.214Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553717,TO_TIMESTAMP('2025-01-13 09:48:19.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.305Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553718,TO_TIMESTAMP('2025-01-13 09:48:19.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.410Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553719,TO_TIMESTAMP('2025-01-13 09:48:19.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.500Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553720,TO_TIMESTAMP('2025-01-13 09:48:19.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.584Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553721,TO_TIMESTAMP('2025-01-13 09:48:19.504000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.504000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.672Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553722,TO_TIMESTAMP('2025-01-13 09:48:19.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.756Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553723,TO_TIMESTAMP('2025-01-13 09:48:19.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.845Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553724,TO_TIMESTAMP('2025-01-13 09:48:19.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:19.932Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553725,TO_TIMESTAMP('2025-01-13 09:48:19.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.020Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553726,TO_TIMESTAMP('2025-01-13 09:48:19.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:19.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.111Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553727,TO_TIMESTAMP('2025-01-13 09:48:20.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.205Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553728,TO_TIMESTAMP('2025-01-13 09:48:20.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.295Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553729,TO_TIMESTAMP('2025-01-13 09:48:20.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.384Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553730,TO_TIMESTAMP('2025-01-13 09:48:20.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.469Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553731,TO_TIMESTAMP('2025-01-13 09:48:20.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.561Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553732,TO_TIMESTAMP('2025-01-13 09:48:20.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.643Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553733,TO_TIMESTAMP('2025-01-13 09:48:20.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.735Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553734,TO_TIMESTAMP('2025-01-13 09:48:20.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.827Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553735,TO_TIMESTAMP('2025-01-13 09:48:20.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:20.921Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553736,TO_TIMESTAMP('2025-01-13 09:48:20.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.018Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553737,TO_TIMESTAMP('2025-01-13 09:48:20.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:20.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.106Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553738,TO_TIMESTAMP('2025-01-13 09:48:21.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.194Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553739,TO_TIMESTAMP('2025-01-13 09:48:21.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.284Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553740,TO_TIMESTAMP('2025-01-13 09:48:21.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.382Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553741,TO_TIMESTAMP('2025-01-13 09:48:21.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.473Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553742,TO_TIMESTAMP('2025-01-13 09:48:21.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.555Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553743,TO_TIMESTAMP('2025-01-13 09:48:21.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.638Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553744,TO_TIMESTAMP('2025-01-13 09:48:21.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.728Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553745,TO_TIMESTAMP('2025-01-13 09:48:21.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.818Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553746,TO_TIMESTAMP('2025-01-13 09:48:21.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.904Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553747,TO_TIMESTAMP('2025-01-13 09:48:21.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:21.993Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553748,TO_TIMESTAMP('2025-01-13 09:48:21.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:22.077Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553749,TO_TIMESTAMP('2025-01-13 09:48:21.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:21.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:22.167Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553750,TO_TIMESTAMP('2025-01-13 09:48:22.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:22.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:22.255Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540364,553751,TO_TIMESTAMP('2025-01-13 09:48:22.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:22.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Nov.-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-11-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-11-30','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:48:49.862Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540365,year_id,TO_TIMESTAMP('2025-01-13 09:48:49.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-11-30','YYYY-MM-DD'),'Y','Nov.-27',11,'S','N',TO_TIMESTAMP('2027-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:48:49.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:49.864Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540365 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:48:50.003Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553752,TO_TIMESTAMP('2025-01-13 09:48:49.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:49.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.081Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553753,TO_TIMESTAMP('2025-01-13 09:48:50.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.164Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553754,TO_TIMESTAMP('2025-01-13 09:48:50.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.254Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553755,TO_TIMESTAMP('2025-01-13 09:48:50.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.340Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553756,TO_TIMESTAMP('2025-01-13 09:48:50.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.432Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553757,TO_TIMESTAMP('2025-01-13 09:48:50.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.518Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553758,TO_TIMESTAMP('2025-01-13 09:48:50.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.605Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553759,TO_TIMESTAMP('2025-01-13 09:48:50.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.696Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553760,TO_TIMESTAMP('2025-01-13 09:48:50.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.786Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553761,TO_TIMESTAMP('2025-01-13 09:48:50.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.880Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553762,TO_TIMESTAMP('2025-01-13 09:48:50.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:50.968Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553763,TO_TIMESTAMP('2025-01-13 09:48:50.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.053Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553764,TO_TIMESTAMP('2025-01-13 09:48:50.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:50.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.137Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553765,TO_TIMESTAMP('2025-01-13 09:48:51.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.226Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553766,TO_TIMESTAMP('2025-01-13 09:48:51.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.310Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553767,TO_TIMESTAMP('2025-01-13 09:48:51.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.401Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553768,TO_TIMESTAMP('2025-01-13 09:48:51.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.487Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553769,TO_TIMESTAMP('2025-01-13 09:48:51.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.578Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553770,TO_TIMESTAMP('2025-01-13 09:48:51.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.666Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553771,TO_TIMESTAMP('2025-01-13 09:48:51.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.754Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553772,TO_TIMESTAMP('2025-01-13 09:48:51.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.842Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553773,TO_TIMESTAMP('2025-01-13 09:48:51.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:51.931Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553774,TO_TIMESTAMP('2025-01-13 09:48:51.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.019Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553775,TO_TIMESTAMP('2025-01-13 09:48:51.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:51.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.110Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553776,TO_TIMESTAMP('2025-01-13 09:48:52.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.208Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553777,TO_TIMESTAMP('2025-01-13 09:48:52.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.302Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553778,TO_TIMESTAMP('2025-01-13 09:48:52.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.393Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553779,TO_TIMESTAMP('2025-01-13 09:48:52.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.479Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553780,TO_TIMESTAMP('2025-01-13 09:48:52.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.563Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553781,TO_TIMESTAMP('2025-01-13 09:48:52.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.653Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553782,TO_TIMESTAMP('2025-01-13 09:48:52.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.739Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553783,TO_TIMESTAMP('2025-01-13 09:48:52.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.829Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553784,TO_TIMESTAMP('2025-01-13 09:48:52.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:52.921Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553785,TO_TIMESTAMP('2025-01-13 09:48:52.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:53.003Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553786,TO_TIMESTAMP('2025-01-13 09:48:52.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:52.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:53.090Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553787,TO_TIMESTAMP('2025-01-13 09:48:53.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:53.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:53.173Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553788,TO_TIMESTAMP('2025-01-13 09:48:53.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:53.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:53.264Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553789,TO_TIMESTAMP('2025-01-13 09:48:53.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:53.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:53.361Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553790,TO_TIMESTAMP('2025-01-13 09:48:53.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:53.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:48:53.455Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540365,553791,TO_TIMESTAMP('2025-01-13 09:48:53.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:48:53.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;

--- Dez.-27

DO
$$
    DECLARE
        year_id numeric;
    BEGIN
        year_id := (SELECT c_year_id from c_year where fiscalyear = '2027' and c_calendar_id = 1000000);

        IF not exists(select 1
                      from C_Period
                      where c_year_id = year_id and startdate = TO_TIMESTAMP('2027-12-01','YYYY-MM-DD') and enddate = TO_TIMESTAMP('2027-12-31','YYYY-MM-DD')) THEN

            -- 2025-01-13T09:49:24.008Z
            INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540366,year_id,TO_TIMESTAMP('2025-01-13 09:49:24.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2027-12-31','YYYY-MM-DD'),'Y','Dez.-27',12,'S','N',TO_TIMESTAMP('2027-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2025-01-13 09:49:24.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.012Z
            INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540366 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
            ;

            -- 2025-01-13T09:49:24.149Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553792,TO_TIMESTAMP('2025-01-13 09:49:24.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AVI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.237Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553793,TO_TIMESTAMP('2025-01-13 09:49:24.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HRP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.326Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553794,TO_TIMESTAMP('2025-01-13 09:49:24.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MCC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.412Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553795,TO_TIMESTAMP('2025-01-13 09:49:24.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'API','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.501Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553796,TO_TIMESTAMP('2025-01-13 09:49:24.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.585Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553797,TO_TIMESTAMP('2025-01-13 09:49:24.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.677Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553798,TO_TIMESTAMP('2025-01-13 09:49:24.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.764Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553799,TO_TIMESTAMP('2025-01-13 09:49:24.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.859Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553800,TO_TIMESTAMP('2025-01-13 09:49:24.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AEI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:24.946Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553801,TO_TIMESTAMP('2025-01-13 09:49:24.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.034Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553802,TO_TIMESTAMP('2025-01-13 09:49:24.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:24.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.136Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553803,TO_TIMESTAMP('2025-01-13 09:49:25.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.220Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553804,TO_TIMESTAMP('2025-01-13 09:49:25.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MXP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.305Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553805,TO_TIMESTAMP('2025-01-13 09:49:25.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.394Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553806,TO_TIMESTAMP('2025-01-13 09:49:25.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.479Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553807,TO_TIMESTAMP('2025-01-13 09:49:25.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.575Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553808,TO_TIMESTAMP('2025-01-13 09:49:25.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PJI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.665Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553809,TO_TIMESTAMP('2025-01-13 09:49:25.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GLD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.763Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553810,TO_TIMESTAMP('2025-01-13 09:49:25.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.855Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553811,TO_TIMESTAMP('2025-01-13 09:49:25.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:25.931Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553812,TO_TIMESTAMP('2025-01-13 09:49:25.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMA','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.014Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553813,TO_TIMESTAMP('2025-01-13 09:49:25.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMS','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:25.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.099Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553814,TO_TIMESTAMP('2025-01-13 09:49:26.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.190Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553815,TO_TIMESTAMP('2025-01-13 09:49:26.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.276Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553816,TO_TIMESTAMP('2025-01-13 09:49:26.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CON','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.361Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553817,TO_TIMESTAMP('2025-01-13 09:49:26.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MMR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.445Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553818,TO_TIMESTAMP('2025-01-13 09:49:26.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MST','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.530Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553819,TO_TIMESTAMP('2025-01-13 09:49:26.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.614Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553820,TO_TIMESTAMP('2025-01-13 09:49:26.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ARR','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.716Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553821,TO_TIMESTAMP('2025-01-13 09:49:26.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.798Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553822,TO_TIMESTAMP('2025-01-13 09:49:26.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MQO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.879Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553823,TO_TIMESTAMP('2025-01-13 09:49:26.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOP','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:26.966Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553824,TO_TIMESTAMP('2025-01-13 09:49:26.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MOF','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:27.044Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553825,TO_TIMESTAMP('2025-01-13 09:49:26.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DUN','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:26.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:27.130Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553826,TO_TIMESTAMP('2025-01-13 09:49:27.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CUI','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:27.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:27.216Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553827,TO_TIMESTAMP('2025-01-13 09:49:27.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDC','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:27.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:27.307Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553828,TO_TIMESTAMP('2025-01-13 09:49:27.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SDD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:27.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:27.394Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553829,TO_TIMESTAMP('2025-01-13 09:49:27.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BOM','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:27.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:27.480Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553830,TO_TIMESTAMP('2025-01-13 09:49:27.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DOO','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:27.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

            -- 2025-01-13T09:49:27.573Z
            INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540366,553831,TO_TIMESTAMP('2025-01-13 09:49:27.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CRD','Y','N','N','N',TO_TIMESTAMP('2025-01-13 09:49:27.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
            ;

        END IF;
    END
$$;
