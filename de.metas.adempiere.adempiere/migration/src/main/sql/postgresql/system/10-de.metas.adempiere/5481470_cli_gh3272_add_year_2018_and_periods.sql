DO $$
BEGIN
	IF NOT EXISTS(select 1 from C_Year where FiscalYear='2018' and C_Calendar_ID=1000000) THEN

		-- 2018-01-02T19:53:45.484
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,C_Year_ID,FiscalYear,IsActive,Processing,Updated,UpdatedBy)
		VALUES (1000000,0,1000000,TO_TIMESTAMP('2018-01-02 19:53:45','YYYY-MM-DD HH24:MI:SS'),100,540005,'2018','Y','N',TO_TIMESTAMP('2018-01-02 19:53:45','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:50.968
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540026,TO_TIMESTAMP('2018-01-02 19:53:50','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-01-31','YYYY-MM-DD'),'Y','Jan-18',1,'S','N',TO_TIMESTAMP('2018-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:53:50','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:50.969
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540026 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:53:51.017
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540794,540026,TO_TIMESTAMP('2018-01-02 19:53:50','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:50','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.043
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540795,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.072
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540796,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.098
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540797,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.124
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540798,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.151
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540799,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.182
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540800,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.215
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540801,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.250
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540802,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.283
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540803,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.316
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540804,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.348
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540805,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.382
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540806,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.416
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540807,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.447
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540808,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.480
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540809,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.514
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540810,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.542
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540811,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.574
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540812,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.605
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540813,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.637
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540814,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.672
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540815,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.707
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540816,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.740
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540817,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.767
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540818,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.794
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540819,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.826
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540820,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.859
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540821,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.890
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540822,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.922
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540823,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.955
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540824,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:51.987
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540825,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.024
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540826,540026,TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:51','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.065
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-02-28','YYYY-MM-DD'),'Y','Feb-18',2,'S','N',TO_TIMESTAMP('2018-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.067
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540027 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:53:52.108
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540827,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.137
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540828,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.169
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540829,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.426
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540830,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.457
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540831,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.492
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540832,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.521
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540833,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.550
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540834,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.583
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540835,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.615
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540836,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.647
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540837,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.678
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540838,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.710
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540839,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.740
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540840,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.767
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540841,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.793
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540842,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.819
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540843,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.847
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540844,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.883
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540845,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.916
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540846,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.948
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540847,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:52.983
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540848,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.014
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540849,540027,TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:52','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.045
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540850,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.078
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540851,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.112
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540852,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.144
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540853,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.173
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540854,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.198
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540855,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.222
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540856,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.247
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540857,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.273
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540858,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.296
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540859,540027,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.322
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-03-31','YYYY-MM-DD'),'Y','Mär-18',3,'S','N',TO_TIMESTAMP('2018-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.322
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540028 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:53:53.348
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540860,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.372
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540861,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.396
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540862,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.423
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540863,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.449
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540864,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.479
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540865,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.511
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540866,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.545
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540867,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.580
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540868,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.620
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540869,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.645
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540870,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.672
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540871,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.699
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540872,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.729
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540873,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.759
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540874,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.787
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540875,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.816
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540876,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.847
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540877,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.880
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540878,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.913
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540879,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.945
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540880,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:53.981
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540881,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.008
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540882,540028,TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:53','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.033
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540883,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.063
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540884,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.097
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540885,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.130
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540886,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.162
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540887,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.195
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540888,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.228
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540889,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.258
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540890,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.288
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540891,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.320
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540892,540028,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.358
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-04-30','YYYY-MM-DD'),'Y','Apr-18',4,'S','N',TO_TIMESTAMP('2018-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.359
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540029 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:53:54.393
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540893,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.419
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540894,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.445
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540895,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.472
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540896,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.504
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540897,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.534
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540898,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.561
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540899,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.593
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540900,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.623
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540901,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.655
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540902,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.685
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540903,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.716
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540904,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.756
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540905,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.794
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540906,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.823
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540907,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.853
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540908,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.884
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540909,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.916
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540910,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.948
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540911,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:54.981
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540912,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.013
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540913,540029,TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:54','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.045
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540914,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.085
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540915,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.114
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540916,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.145
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540917,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.179
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540918,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.213
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540919,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.248
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540920,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.279
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540921,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.311
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540922,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.342
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540923,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.374
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540924,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.407
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540925,540029,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.449
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-05-31','YYYY-MM-DD'),'Y','Mai-18',5,'S','N',TO_TIMESTAMP('2018-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.451
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540030 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:53:55.495
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540926,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.519
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540927,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.542
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540928,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.569
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540929,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.595
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540930,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.619
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540931,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.649
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540932,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.674
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540933,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.699
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540934,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.724
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540935,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.747
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540936,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.771
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540937,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.795
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540938,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.819
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540939,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.843
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540940,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.871
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540941,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.898
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540942,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.925
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540943,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.952
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540944,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:55.980
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540945,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.007
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540946,540030,TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:55','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.034
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540947,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.067
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540948,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.100
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540949,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.134
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540950,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.164
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540951,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.195
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540952,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.225
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540953,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.255
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540954,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.285
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540955,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.316
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540956,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.347
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540957,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.387
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540958,540030,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.651
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-06-30','YYYY-MM-DD'),'Y','Jun-18',6,'S','N',TO_TIMESTAMP('2018-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.653
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540031 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:53:56.693
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540959,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.731
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540960,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.758
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540961,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.782
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540962,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.807
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540963,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.832
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540964,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.856
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540965,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.880
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540966,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.905
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540967,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.932
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540968,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.965
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540969,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:56.998
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540970,540031,TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:56','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.027
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540971,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.055
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540972,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.083
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540973,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.339
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540974,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.370
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540975,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.401
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540976,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.431
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540977,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.459
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540978,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.488
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540979,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.520
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540980,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.551
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540981,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.586
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540982,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.613
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540983,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.640
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540984,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.671
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540985,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.704
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540986,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:57.736
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540987,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:58.768
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540988,540031,TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:57','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:58.797
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540989,540031,TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:58.825
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540990,540031,TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:58.855
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540991,540031,TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:58.894
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540032,TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-07-31','YYYY-MM-DD'),'Y','Jul-18',7,'S','N',TO_TIMESTAMP('2018-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:58.895
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540032 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:53:58.921
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540992,540032,TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:58.951
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540993,540032,TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:58.978
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540994,540032,TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.009
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540995,540032,TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:58','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.047
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540996,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.077
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540997,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.109
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540998,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.139
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540999,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.171
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541000,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.204
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541001,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.236
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541002,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.268
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541003,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.300
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541004,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.331
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541005,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.363
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541006,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.395
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541007,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.427
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541008,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.463
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541009,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.490
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541010,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.519
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541011,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.550
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541012,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.583
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541013,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.618
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541014,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.649
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541015,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.681
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541016,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.712
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541017,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.742
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541018,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.770
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541019,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.800
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541020,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.838
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541021,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.869
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541022,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.900
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541023,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.932
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541024,540032,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.973
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540033,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-08-31','YYYY-MM-DD'),'Y','Aug-18',8,'S','N',TO_TIMESTAMP('2018-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:53:59.975
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540033 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:54:00.013
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541025,540033,TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:53:59','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.049
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541026,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.085
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541027,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.127
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541028,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.153
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541029,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.179
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541030,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.207
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541031,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.231
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541032,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.255
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541033,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.280
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541034,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.305
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541035,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.328
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541036,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.352
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541037,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.379
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541038,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.411
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541039,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.441
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541040,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.471
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541041,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.502
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541042,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.538
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541043,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.566
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541044,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.592
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541045,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.619
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541046,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.647
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541047,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.678
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541048,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.936
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541049,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:00.969
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541050,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.005
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541051,540033,TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:00','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.036
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541052,540033,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.068
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541053,540033,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.109
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541054,540033,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.149
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541055,540033,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.189
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541056,540033,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.230
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541057,540033,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.277
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-09-30','YYYY-MM-DD'),'Y','Sep-18',9,'S','N',TO_TIMESTAMP('2018-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.278
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540034 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:54:01.320
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541058,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.364
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541059,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.395
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541060,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.427
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541061,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.458
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541062,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.488
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541063,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.531
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541064,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.559
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541065,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.586
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541066,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.615
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541067,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.644
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541068,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.676
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541069,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.707
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541070,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.743
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541071,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.773
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541072,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.801
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541073,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.831
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541074,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.861
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541075,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.887
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541076,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.916
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541077,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.948
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541078,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:01.978
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541079,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.005
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541080,540034,TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:01','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.033
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541081,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.063
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541082,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.096
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541083,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.128
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541084,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.163
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541085,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.197
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541086,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.229
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541087,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.263
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541088,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.296
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541089,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.330
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541090,540034,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.367
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-10-31','YYYY-MM-DD'),'Y','Okt-18',10,'S','N',TO_TIMESTAMP('2018-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.369
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540035 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:54:02.407
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541091,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.652
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541092,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.679
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541093,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.712
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541094,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.747
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541095,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.778
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541096,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.809
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541097,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.839
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541098,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.872
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541099,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.904
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541100,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.941
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541101,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:02.970
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541102,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541103,540035,TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:02','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.028
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541104,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.059
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541105,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.094
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541106,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.125
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541107,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.155
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541108,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.185
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541109,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.219
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541110,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.250
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541111,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.281
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541112,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.314
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541113,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.347
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541114,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.379
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541115,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.412
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541116,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.444
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541117,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.476
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541118,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.508
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541119,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.540
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541120,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.573
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541121,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.606
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541122,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.639
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541123,540035,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.685
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-11-30','YYYY-MM-DD'),'Y','Nov-18',11,'S','N',TO_TIMESTAMP('2018-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.686
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540036 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:54:03.717
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541124,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.744
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541125,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.773
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541126,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.802
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541127,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.835
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541128,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.867
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541129,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.900
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541130,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.931
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541131,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.963
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541132,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:03.995
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541133,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.025
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541134,540036,TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:03','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.054
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541135,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.085
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541136,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.117
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541137,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.149
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541138,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.181
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541139,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.214
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541140,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.245
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541141,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.276
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541142,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.309
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541143,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.340
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541144,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.371
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541145,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.402
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541146,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.432
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541147,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.463
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541148,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.489
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541149,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.515
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541150,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.541
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541151,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.571
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541152,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.600
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541153,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.631
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541154,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.661
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541155,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.690
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541156,540036,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.716
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,Created,CreatedBy,C_Year_ID,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,540005,TO_TIMESTAMP('2018-12-31','YYYY-MM-DD'),'Y','Dez-18',12,'S','N',TO_TIMESTAMP('2018-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.717
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_Period t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_Period_ID=540037 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
		;

		-- 2018-01-02T19:54:04.742
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541157,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.768
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541158,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'API','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.792
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541159,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.817
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541160,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.840
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541161,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.867
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541162,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.891
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541163,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.916
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541164,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.940
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541165,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.964
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541166,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:04.990
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541167,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.018
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541168,540037,TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:04','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.276
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541169,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'CMC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.309
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541170,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.343
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541171,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.376
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541172,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.411
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541173,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.443
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541174,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.475
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541175,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.511
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541176,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.542
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541177,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.574
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541178,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.609
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541179,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.641
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541180,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.673
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541181,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.708
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541182,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.750
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541183,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.781
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541184,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.810
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541185,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.840
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541186,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.867
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541187,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.892
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541188,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

		-- 2018-01-02T19:54:05.918
		-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
		INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_PeriodControl_ID,C_Period_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,541189,540037,TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2018-01-02 19:54:05','YYYY-MM-DD HH24:MI:SS'),100)
		;

	END IF;
END;
$$;
