-- 2023-08-28T10:44:06.401637Z
INSERT INTO C_Calendar (AD_Client_ID,AD_Org_ID,C_Calendar_ID,Created,CreatedBy,IsActive,IsDefault,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540014,TO_TIMESTAMP('2023-08-28 13:44:06.39','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Erntekalender',TO_TIMESTAMP('2023-08-28 13:44:06.39','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:09.417490900Z
UPDATE C_Calendar SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2023-08-28 13:44:09.417','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_Calendar_ID=540014
;

-- 2023-08-28T10:44:23.860868400Z
INSERT INTO C_Year (AD_Client_ID,AD_Org_ID,C_Calendar_ID,C_Year_ID,Created,CreatedBy,FiscalYear,IsActive,Processing,Updated,UpdatedBy) VALUES (1000000,0,540014,540020,TO_TIMESTAMP('2023-08-28 13:44:23.858','YYYY-MM-DD HH24:MI:SS.US'),100,'2023','Y','N',TO_TIMESTAMP('2023-08-28 13:44:23.858','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:49.908158700Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540195,540020,TO_TIMESTAMP('2023-08-28 13:44:49.779','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2023-07-31','YYYY-MM-DD'),'Y','Juli-23',1,'S','N',TO_TIMESTAMP('2023-07-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:44:49.779','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:49.917150900Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540195 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:44:50.083519700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546902,TO_TIMESTAMP('2023-08-28 13:44:49.981','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:49.981','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:50.182970700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546903,TO_TIMESTAMP('2023-08-28 13:44:50.092','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.092','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:50.270609100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546904,TO_TIMESTAMP('2023-08-28 13:44:50.186','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.186','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:50.360515500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546905,TO_TIMESTAMP('2023-08-28 13:44:50.275','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.275','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:50.465330300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546906,TO_TIMESTAMP('2023-08-28 13:44:50.365','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.365','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:50.560345800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546907,TO_TIMESTAMP('2023-08-28 13:44:50.47','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.47','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:50.642828300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546908,TO_TIMESTAMP('2023-08-28 13:44:50.564','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.564','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:50.734231800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546909,TO_TIMESTAMP('2023-08-28 13:44:50.645','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.645','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:50.825568900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546910,TO_TIMESTAMP('2023-08-28 13:44:50.739','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.739','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:50.923066800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546911,TO_TIMESTAMP('2023-08-28 13:44:50.829','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.829','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.018351400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546912,TO_TIMESTAMP('2023-08-28 13:44:50.929','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:50.929','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.107863300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546913,TO_TIMESTAMP('2023-08-28 13:44:51.022','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.022','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.196309200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546914,TO_TIMESTAMP('2023-08-28 13:44:51.11','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.11','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.286253100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546915,TO_TIMESTAMP('2023-08-28 13:44:51.2','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.2','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.379807900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546916,TO_TIMESTAMP('2023-08-28 13:44:51.29','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.29','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.472155600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546917,TO_TIMESTAMP('2023-08-28 13:44:51.383','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.383','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.559096300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546918,TO_TIMESTAMP('2023-08-28 13:44:51.477','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.477','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.644187500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546919,TO_TIMESTAMP('2023-08-28 13:44:51.563','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.563','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.731091900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546920,TO_TIMESTAMP('2023-08-28 13:44:51.648','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.648','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.810735800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546921,TO_TIMESTAMP('2023-08-28 13:44:51.734','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.734','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.899842700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546922,TO_TIMESTAMP('2023-08-28 13:44:51.812','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.812','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:51.989093300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546923,TO_TIMESTAMP('2023-08-28 13:44:51.902','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.902','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.072583300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546924,TO_TIMESTAMP('2023-08-28 13:44:51.993','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:51.993','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.162034500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546925,TO_TIMESTAMP('2023-08-28 13:44:52.077','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.077','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.254130800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546926,TO_TIMESTAMP('2023-08-28 13:44:52.167','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.167','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.343866100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546927,TO_TIMESTAMP('2023-08-28 13:44:52.257','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.257','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.431722600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546928,TO_TIMESTAMP('2023-08-28 13:44:52.348','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.348','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.522588Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546929,TO_TIMESTAMP('2023-08-28 13:44:52.435','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.435','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.617291200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546930,TO_TIMESTAMP('2023-08-28 13:44:52.527','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.527','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.701973500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546931,TO_TIMESTAMP('2023-08-28 13:44:52.622','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.622','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.792167200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546932,TO_TIMESTAMP('2023-08-28 13:44:52.707','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.707','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.887798400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546933,TO_TIMESTAMP('2023-08-28 13:44:52.796','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.796','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:52.972734800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546934,TO_TIMESTAMP('2023-08-28 13:44:52.89','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.89','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.053641800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546935,TO_TIMESTAMP('2023-08-28 13:44:52.977','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:52.977','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.143644100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546936,TO_TIMESTAMP('2023-08-28 13:44:53.057','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.057','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.230705200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546937,TO_TIMESTAMP('2023-08-28 13:44:53.148','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.148','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.312819300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546938,TO_TIMESTAMP('2023-08-28 13:44:53.235','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.235','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.398586500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546939,TO_TIMESTAMP('2023-08-28 13:44:53.316','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.316','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.487243300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546940,TO_TIMESTAMP('2023-08-28 13:44:53.402','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.402','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.576073300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546941,TO_TIMESTAMP('2023-08-28 13:44:53.491','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.491','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.676366Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540195,546942,TO_TIMESTAMP('2023-08-28 13:44:53.581','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.581','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.760281Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540196,540020,TO_TIMESTAMP('2023-08-28 13:44:53.681','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2023-08-31','YYYY-MM-DD'),'Y','Aug.-23',2,'S','N',TO_TIMESTAMP('2023-08-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:44:53.681','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.763423600Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540196 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:44:53.907323600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546943,TO_TIMESTAMP('2023-08-28 13:44:53.82','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.82','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:53.986508200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546944,TO_TIMESTAMP('2023-08-28 13:44:53.911','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.911','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.085164800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546945,TO_TIMESTAMP('2023-08-28 13:44:53.99','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:53.99','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.172914900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546946,TO_TIMESTAMP('2023-08-28 13:44:54.089','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.089','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.266216900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546947,TO_TIMESTAMP('2023-08-28 13:44:54.178','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.178','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.357794400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546948,TO_TIMESTAMP('2023-08-28 13:44:54.271','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.271','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.441745600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546949,TO_TIMESTAMP('2023-08-28 13:44:54.362','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.362','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.542147400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546950,TO_TIMESTAMP('2023-08-28 13:44:54.446','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.446','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.632975100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546951,TO_TIMESTAMP('2023-08-28 13:44:54.547','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.547','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.722246400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546952,TO_TIMESTAMP('2023-08-28 13:44:54.637','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.637','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.807433700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546953,TO_TIMESTAMP('2023-08-28 13:44:54.724','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.724','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.898969200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546954,TO_TIMESTAMP('2023-08-28 13:44:54.811','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.811','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:54.984950700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546955,TO_TIMESTAMP('2023-08-28 13:44:54.902','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.902','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.073152700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546956,TO_TIMESTAMP('2023-08-28 13:44:54.987','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:54.987','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.166613300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546957,TO_TIMESTAMP('2023-08-28 13:44:55.075','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.075','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.260921400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546958,TO_TIMESTAMP('2023-08-28 13:44:55.17','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.17','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.345260800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546959,TO_TIMESTAMP('2023-08-28 13:44:55.264','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.264','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.444137400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546960,TO_TIMESTAMP('2023-08-28 13:44:55.349','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.349','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.544687900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546961,TO_TIMESTAMP('2023-08-28 13:44:55.448','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.448','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.648489500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546962,TO_TIMESTAMP('2023-08-28 13:44:55.548','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.548','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.745599100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546963,TO_TIMESTAMP('2023-08-28 13:44:55.653','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.653','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.841970700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546964,TO_TIMESTAMP('2023-08-28 13:44:55.749','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.749','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:55.929434700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546965,TO_TIMESTAMP('2023-08-28 13:44:55.845','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.017064Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546966,TO_TIMESTAMP('2023-08-28 13:44:55.934','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:55.934','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.110629900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546967,TO_TIMESTAMP('2023-08-28 13:44:56.021','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.021','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.216889500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546968,TO_TIMESTAMP('2023-08-28 13:44:56.115','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.115','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.306819800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546969,TO_TIMESTAMP('2023-08-28 13:44:56.221','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.221','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.417248900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546970,TO_TIMESTAMP('2023-08-28 13:44:56.311','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.311','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.511128900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546971,TO_TIMESTAMP('2023-08-28 13:44:56.419','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.419','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.599071500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546972,TO_TIMESTAMP('2023-08-28 13:44:56.515','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.515','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.687575400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546973,TO_TIMESTAMP('2023-08-28 13:44:56.604','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.604','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.776703Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546974,TO_TIMESTAMP('2023-08-28 13:44:56.692','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.692','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.858829200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546975,TO_TIMESTAMP('2023-08-28 13:44:56.781','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.781','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:56.950146100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546976,TO_TIMESTAMP('2023-08-28 13:44:56.863','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.863','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.053755800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546977,TO_TIMESTAMP('2023-08-28 13:44:56.954','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:56.954','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.146818600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546978,TO_TIMESTAMP('2023-08-28 13:44:57.057','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.057','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.233769800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546979,TO_TIMESTAMP('2023-08-28 13:44:57.15','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.15','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.325922700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546980,TO_TIMESTAMP('2023-08-28 13:44:57.237','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.237','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.409214700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546981,TO_TIMESTAMP('2023-08-28 13:44:57.33','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.33','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.501800500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546982,TO_TIMESTAMP('2023-08-28 13:44:57.414','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.414','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.583910500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540196,546983,TO_TIMESTAMP('2023-08-28 13:44:57.504','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.504','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.673318Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540197,540020,TO_TIMESTAMP('2023-08-28 13:44:57.587','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2023-09-30','YYYY-MM-DD'),'Y','Sept.-23',3,'S','N',TO_TIMESTAMP('2023-09-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:44:57.587','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.675327100Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540197 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:44:57.810373Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546984,TO_TIMESTAMP('2023-08-28 13:44:57.715','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.715','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.896338300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546985,TO_TIMESTAMP('2023-08-28 13:44:57.814','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.814','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:57.983570200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546986,TO_TIMESTAMP('2023-08-28 13:44:57.9','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.9','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.074248500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546987,TO_TIMESTAMP('2023-08-28 13:44:57.988','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:57.988','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.158632900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546988,TO_TIMESTAMP('2023-08-28 13:44:58.08','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.08','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.240806800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546989,TO_TIMESTAMP('2023-08-28 13:44:58.163','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.163','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.332632100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546990,TO_TIMESTAMP('2023-08-28 13:44:58.245','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.245','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.423770600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546991,TO_TIMESTAMP('2023-08-28 13:44:58.335','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.335','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.516094600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546992,TO_TIMESTAMP('2023-08-28 13:44:58.428','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.428','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.604513400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546993,TO_TIMESTAMP('2023-08-28 13:44:58.521','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.521','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.690497300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546994,TO_TIMESTAMP('2023-08-28 13:44:58.609','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.609','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.780230800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546995,TO_TIMESTAMP('2023-08-28 13:44:58.694','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.694','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.871816700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546996,TO_TIMESTAMP('2023-08-28 13:44:58.784','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.784','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:58.961915600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546997,TO_TIMESTAMP('2023-08-28 13:44:58.875','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.875','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.051109Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546998,TO_TIMESTAMP('2023-08-28 13:44:58.967','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:58.967','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.171942200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,546999,TO_TIMESTAMP('2023-08-28 13:44:59.055','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.055','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.255243400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547000,TO_TIMESTAMP('2023-08-28 13:44:59.175','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.175','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.338729700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547001,TO_TIMESTAMP('2023-08-28 13:44:59.259','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.259','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.423270100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547002,TO_TIMESTAMP('2023-08-28 13:44:59.342','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.342','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.501281700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547003,TO_TIMESTAMP('2023-08-28 13:44:59.426','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.426','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.584298400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547004,TO_TIMESTAMP('2023-08-28 13:44:59.506','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.506','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.675459100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547005,TO_TIMESTAMP('2023-08-28 13:44:59.589','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.589','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.769021200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547006,TO_TIMESTAMP('2023-08-28 13:44:59.68','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.68','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.855486200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547007,TO_TIMESTAMP('2023-08-28 13:44:59.774','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.774','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:44:59.946088200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547008,TO_TIMESTAMP('2023-08-28 13:44:59.859','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.859','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.036976500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547009,TO_TIMESTAMP('2023-08-28 13:44:59.95','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:44:59.95','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.120356400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547010,TO_TIMESTAMP('2023-08-28 13:45:00.04','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.04','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.198289400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547011,TO_TIMESTAMP('2023-08-28 13:45:00.122','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.122','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.287604900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547012,TO_TIMESTAMP('2023-08-28 13:45:00.201','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.201','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.376752700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547013,TO_TIMESTAMP('2023-08-28 13:45:00.292','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.292','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.452833700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547014,TO_TIMESTAMP('2023-08-28 13:45:00.38','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.38','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.530082700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547015,TO_TIMESTAMP('2023-08-28 13:45:00.455','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.455','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.606898100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547016,TO_TIMESTAMP('2023-08-28 13:45:00.534','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.534','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.687225400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547017,TO_TIMESTAMP('2023-08-28 13:45:00.608','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.608','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.769691600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547018,TO_TIMESTAMP('2023-08-28 13:45:00.69','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.69','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.858783Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547019,TO_TIMESTAMP('2023-08-28 13:45:00.773','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.773','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:00.939723800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547020,TO_TIMESTAMP('2023-08-28 13:45:00.86','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.86','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.020352400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547021,TO_TIMESTAMP('2023-08-28 13:45:00.942','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:00.942','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.104167900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547022,TO_TIMESTAMP('2023-08-28 13:45:01.022','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.022','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.187012700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547023,TO_TIMESTAMP('2023-08-28 13:45:01.107','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.107','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.280140700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540197,547024,TO_TIMESTAMP('2023-08-28 13:45:01.19','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.19','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.372548Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540198,540020,TO_TIMESTAMP('2023-08-28 13:45:01.284','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2023-10-31','YYYY-MM-DD'),'Y','Okt.-23',4,'S','N',TO_TIMESTAMP('2023-10-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:45:01.284','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.375594500Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540198 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:45:01.510483300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547025,TO_TIMESTAMP('2023-08-28 13:45:01.43','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.43','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.595224Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547026,TO_TIMESTAMP('2023-08-28 13:45:01.513','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.513','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.687101900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547027,TO_TIMESTAMP('2023-08-28 13:45:01.6','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.6','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.765112400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547028,TO_TIMESTAMP('2023-08-28 13:45:01.69','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.69','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.844224500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547029,TO_TIMESTAMP('2023-08-28 13:45:01.767','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.767','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:01.930264800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547030,TO_TIMESTAMP('2023-08-28 13:45:01.847','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.847','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.014353900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547031,TO_TIMESTAMP('2023-08-28 13:45:01.932','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:01.932','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.100416400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547032,TO_TIMESTAMP('2023-08-28 13:45:02.017','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.017','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.181069100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547033,TO_TIMESTAMP('2023-08-28 13:45:02.103','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.103','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.254927600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547034,TO_TIMESTAMP('2023-08-28 13:45:02.184','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.184','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.334929Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547035,TO_TIMESTAMP('2023-08-28 13:45:02.256','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.256','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.412936400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547036,TO_TIMESTAMP('2023-08-28 13:45:02.336','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.336','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.507578200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547037,TO_TIMESTAMP('2023-08-28 13:45:02.415','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.415','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.582191Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547038,TO_TIMESTAMP('2023-08-28 13:45:02.509','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.509','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.655705800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547039,TO_TIMESTAMP('2023-08-28 13:45:02.584','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.584','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.733715700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547040,TO_TIMESTAMP('2023-08-28 13:45:02.658','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.658','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.814731800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547041,TO_TIMESTAMP('2023-08-28 13:45:02.735','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.735','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.887807800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547042,TO_TIMESTAMP('2023-08-28 13:45:02.816','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.816','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:02.967802700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547043,TO_TIMESTAMP('2023-08-28 13:45:02.891','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.891','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.051836300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547044,TO_TIMESTAMP('2023-08-28 13:45:02.971','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:02.971','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.129803700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547045,TO_TIMESTAMP('2023-08-28 13:45:03.053','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.053','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.211848Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547046,TO_TIMESTAMP('2023-08-28 13:45:03.132','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.132','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.292369300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547047,TO_TIMESTAMP('2023-08-28 13:45:03.214','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.214','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.366890100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547048,TO_TIMESTAMP('2023-08-28 13:45:03.295','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.295','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.447471100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547049,TO_TIMESTAMP('2023-08-28 13:45:03.37','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.37','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.531042Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547050,TO_TIMESTAMP('2023-08-28 13:45:03.451','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.451','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.605089800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547051,TO_TIMESTAMP('2023-08-28 13:45:03.533','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.533','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.679614900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547052,TO_TIMESTAMP('2023-08-28 13:45:03.607','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.607','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.773611200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547053,TO_TIMESTAMP('2023-08-28 13:45:03.682','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.682','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.853132500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547054,TO_TIMESTAMP('2023-08-28 13:45:03.776','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.776','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:03.934653100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547055,TO_TIMESTAMP('2023-08-28 13:45:03.855','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.855','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.019687200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547056,TO_TIMESTAMP('2023-08-28 13:45:03.937','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:03.937','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.099716100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547057,TO_TIMESTAMP('2023-08-28 13:45:04.021','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.021','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.180715100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547058,TO_TIMESTAMP('2023-08-28 13:45:04.102','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.102','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.264767500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547059,TO_TIMESTAMP('2023-08-28 13:45:04.183','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.183','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.353851200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547060,TO_TIMESTAMP('2023-08-28 13:45:04.267','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.267','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.431853500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547061,TO_TIMESTAMP('2023-08-28 13:45:04.356','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.356','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.516890Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547062,TO_TIMESTAMP('2023-08-28 13:45:04.435','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.435','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.602890900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547063,TO_TIMESTAMP('2023-08-28 13:45:04.52','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.52','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.683411200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547064,TO_TIMESTAMP('2023-08-28 13:45:04.606','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.606','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.762484Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540198,547065,TO_TIMESTAMP('2023-08-28 13:45:04.687','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.687','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.839516100Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540199,540020,TO_TIMESTAMP('2023-08-28 13:45:04.767','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2023-11-30','YYYY-MM-DD'),'Y','Nov.-23',5,'S','N',TO_TIMESTAMP('2023-11-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:45:04.767','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:04.842513Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540199 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:45:04.994510600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547066,TO_TIMESTAMP('2023-08-28 13:45:04.906','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.906','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.078510100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547067,TO_TIMESTAMP('2023-08-28 13:45:04.997','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:04.997','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.154046800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547068,TO_TIMESTAMP('2023-08-28 13:45:05.082','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.082','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.236583700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547069,TO_TIMESTAMP('2023-08-28 13:45:05.158','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.158','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.315584800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547070,TO_TIMESTAMP('2023-08-28 13:45:05.24','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.24','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.398636400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547071,TO_TIMESTAMP('2023-08-28 13:45:05.318','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.318','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.477166900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547072,TO_TIMESTAMP('2023-08-28 13:45:05.401','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.401','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.558226200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547073,TO_TIMESTAMP('2023-08-28 13:45:05.48','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.48','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.638222200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547074,TO_TIMESTAMP('2023-08-28 13:45:05.561','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.561','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.710218600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547075,TO_TIMESTAMP('2023-08-28 13:45:05.64','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.64','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.797753600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547076,TO_TIMESTAMP('2023-08-28 13:45:05.712','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.712','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.889000600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547077,TO_TIMESTAMP('2023-08-28 13:45:05.799','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.799','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:05.965000500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547078,TO_TIMESTAMP('2023-08-28 13:45:05.892','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.892','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.047999400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547079,TO_TIMESTAMP('2023-08-28 13:45:05.967','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:05.967','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.129527200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547080,TO_TIMESTAMP('2023-08-28 13:45:06.051','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.051','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.211139300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547081,TO_TIMESTAMP('2023-08-28 13:45:06.132','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.132','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.288202900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547082,TO_TIMESTAMP('2023-08-28 13:45:06.216','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.216','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.366734100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547083,TO_TIMESTAMP('2023-08-28 13:45:06.292','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.292','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.442733200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547084,TO_TIMESTAMP('2023-08-28 13:45:06.37','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.37','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.525256Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547085,TO_TIMESTAMP('2023-08-28 13:45:06.445','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.445','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.600274400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547086,TO_TIMESTAMP('2023-08-28 13:45:06.528','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.528','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.676809300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547087,TO_TIMESTAMP('2023-08-28 13:45:06.602','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.602','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.753503400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547088,TO_TIMESTAMP('2023-08-28 13:45:06.679','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.679','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.834054800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547089,TO_TIMESTAMP('2023-08-28 13:45:06.756','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.756','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.920630300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547090,TO_TIMESTAMP('2023-08-28 13:45:06.837','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.837','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:06.993063200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547091,TO_TIMESTAMP('2023-08-28 13:45:06.923','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.923','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.076106Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547092,TO_TIMESTAMP('2023-08-28 13:45:06.996','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:06.996','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.157226900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547093,TO_TIMESTAMP('2023-08-28 13:45:07.079','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.079','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.236245500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547094,TO_TIMESTAMP('2023-08-28 13:45:07.16','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.16','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.316771800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547095,TO_TIMESTAMP('2023-08-28 13:45:07.24','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.24','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.386295300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547096,TO_TIMESTAMP('2023-08-28 13:45:07.32','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.32','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.505819800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547097,TO_TIMESTAMP('2023-08-28 13:45:07.39','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.39','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.588314300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547098,TO_TIMESTAMP('2023-08-28 13:45:07.508','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.508','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.666805500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547099,TO_TIMESTAMP('2023-08-28 13:45:07.591','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.591','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.748385100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547100,TO_TIMESTAMP('2023-08-28 13:45:07.67','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.67','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.829384700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547101,TO_TIMESTAMP('2023-08-28 13:45:07.75','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.75','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.914384100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547102,TO_TIMESTAMP('2023-08-28 13:45:07.831','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.831','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:07.989386Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547103,TO_TIMESTAMP('2023-08-28 13:45:07.917','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.917','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.071990100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547104,TO_TIMESTAMP('2023-08-28 13:45:07.991','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:07.991','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.163145100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547105,TO_TIMESTAMP('2023-08-28 13:45:08.074','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:08.074','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.245261700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540199,547106,TO_TIMESTAMP('2023-08-28 13:45:08.166','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:08.166','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.343324500Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540200,540020,TO_TIMESTAMP('2023-08-28 13:45:08.249','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2023-12-31','YYYY-MM-DD'),'Y','Dez.-23',6,'S','N',TO_TIMESTAMP('2023-12-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:45:08.249','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.346326400Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540200 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:45:08.535848700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547107,TO_TIMESTAMP('2023-08-28 13:45:08.445','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:08.445','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.614854600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547108,TO_TIMESTAMP('2023-08-28 13:45:08.538','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:08.538','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.695721800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547109,TO_TIMESTAMP('2023-08-28 13:45:08.617','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:08.617','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.794765700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547110,TO_TIMESTAMP('2023-08-28 13:45:08.699','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:08.699','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.887289400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547111,TO_TIMESTAMP('2023-08-28 13:45:08.797','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:08.797','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:08.960053900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547112,TO_TIMESTAMP('2023-08-28 13:45:08.889','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:08.889','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.045559900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547113,TO_TIMESTAMP('2023-08-28 13:45:08.963','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:08.963','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.127560100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547114,TO_TIMESTAMP('2023-08-28 13:45:09.048','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.048','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.216148800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547115,TO_TIMESTAMP('2023-08-28 13:45:09.13','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.13','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.308172Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547116,TO_TIMESTAMP('2023-08-28 13:45:09.219','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.219','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.393728500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547117,TO_TIMESTAMP('2023-08-28 13:45:09.311','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.311','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.479760400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547118,TO_TIMESTAMP('2023-08-28 13:45:09.395','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.395','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.558728100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547119,TO_TIMESTAMP('2023-08-28 13:45:09.482','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.482','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.639727300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547120,TO_TIMESTAMP('2023-08-28 13:45:09.561','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.561','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.726820300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547121,TO_TIMESTAMP('2023-08-28 13:45:09.642','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.642','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.814782300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547122,TO_TIMESTAMP('2023-08-28 13:45:09.728','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.728','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.902475100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547123,TO_TIMESTAMP('2023-08-28 13:45:09.818','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.818','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:09.982192300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547124,TO_TIMESTAMP('2023-08-28 13:45:09.906','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.906','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.064186300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547125,TO_TIMESTAMP('2023-08-28 13:45:09.985','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:09.985','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.160184400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547126,TO_TIMESTAMP('2023-08-28 13:45:10.068','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.068','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.250235700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547127,TO_TIMESTAMP('2023-08-28 13:45:10.162','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.162','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.341760500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547128,TO_TIMESTAMP('2023-08-28 13:45:10.253','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.253','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.431919500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547129,TO_TIMESTAMP('2023-08-28 13:45:10.344','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.344','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.514499100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547130,TO_TIMESTAMP('2023-08-28 13:45:10.435','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.435','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.601033900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547131,TO_TIMESTAMP('2023-08-28 13:45:10.519','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.519','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.684030100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547132,TO_TIMESTAMP('2023-08-28 13:45:10.605','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.605','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.762567100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547133,TO_TIMESTAMP('2023-08-28 13:45:10.686','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.686','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.852077900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547134,TO_TIMESTAMP('2023-08-28 13:45:10.767','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.767','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:10.947606500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547135,TO_TIMESTAMP('2023-08-28 13:45:10.855','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.855','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.031609300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547136,TO_TIMESTAMP('2023-08-28 13:45:10.95','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:10.95','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.119640600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547137,TO_TIMESTAMP('2023-08-28 13:45:11.034','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.034','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.198606900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547138,TO_TIMESTAMP('2023-08-28 13:45:11.122','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.122','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.280673100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547139,TO_TIMESTAMP('2023-08-28 13:45:11.202','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.202','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.371031800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547140,TO_TIMESTAMP('2023-08-28 13:45:11.284','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.284','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.455309200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547141,TO_TIMESTAMP('2023-08-28 13:45:11.375','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.375','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.537415800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547142,TO_TIMESTAMP('2023-08-28 13:45:11.458','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.458','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.616070100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547143,TO_TIMESTAMP('2023-08-28 13:45:11.54','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.54','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.697186400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547144,TO_TIMESTAMP('2023-08-28 13:45:11.619','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.619','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.793889300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547145,TO_TIMESTAMP('2023-08-28 13:45:11.701','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.701','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.874568300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547146,TO_TIMESTAMP('2023-08-28 13:45:11.798','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.798','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:11.962102400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540200,547147,TO_TIMESTAMP('2023-08-28 13:45:11.878','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:11.878','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.052641900Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540201,540020,TO_TIMESTAMP('2023-08-28 13:45:11.968','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2024-01-31','YYYY-MM-DD'),'Y','Jan.-24',7,'S','N',TO_TIMESTAMP('2024-01-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:45:11.968','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.056643700Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540201 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:45:12.241646700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547148,TO_TIMESTAMP('2023-08-28 13:45:12.162','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.162','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.326266200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547149,TO_TIMESTAMP('2023-08-28 13:45:12.246','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.246','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.403795100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547150,TO_TIMESTAMP('2023-08-28 13:45:12.331','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.331','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.487320800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547151,TO_TIMESTAMP('2023-08-28 13:45:12.407','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.407','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.568751300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547152,TO_TIMESTAMP('2023-08-28 13:45:12.491','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.491','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.652342100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547153,TO_TIMESTAMP('2023-08-28 13:45:12.572','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.572','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.728358700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547154,TO_TIMESTAMP('2023-08-28 13:45:12.655','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.655','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.811952600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547155,TO_TIMESTAMP('2023-08-28 13:45:12.732','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.732','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.903644Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547156,TO_TIMESTAMP('2023-08-28 13:45:12.815','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.815','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:12.984185100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547157,TO_TIMESTAMP('2023-08-28 13:45:12.907','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.907','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.067704500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547158,TO_TIMESTAMP('2023-08-28 13:45:12.988','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:12.988','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.150704200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547159,TO_TIMESTAMP('2023-08-28 13:45:13.07','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.07','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.236706100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547160,TO_TIMESTAMP('2023-08-28 13:45:13.153','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.153','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.329287500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547161,TO_TIMESTAMP('2023-08-28 13:45:13.239','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.239','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.421462100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547162,TO_TIMESTAMP('2023-08-28 13:45:13.332','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.332','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.504451400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547163,TO_TIMESTAMP('2023-08-28 13:45:13.425','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.425','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.580448300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547164,TO_TIMESTAMP('2023-08-28 13:45:13.506','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.506','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.658451500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547165,TO_TIMESTAMP('2023-08-28 13:45:13.582','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.582','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.749449500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547166,TO_TIMESTAMP('2023-08-28 13:45:13.661','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.661','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.826485700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547167,TO_TIMESTAMP('2023-08-28 13:45:13.752','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.752','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.905448400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547168,TO_TIMESTAMP('2023-08-28 13:45:13.828','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.828','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:13.984451700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547169,TO_TIMESTAMP('2023-08-28 13:45:13.907','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.907','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.064450Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547170,TO_TIMESTAMP('2023-08-28 13:45:13.987','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:13.987','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.144453400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547171,TO_TIMESTAMP('2023-08-28 13:45:14.068','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.068','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.221448600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547172,TO_TIMESTAMP('2023-08-28 13:45:14.146','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.146','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.299453700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547173,TO_TIMESTAMP('2023-08-28 13:45:14.223','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.223','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.385449900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547174,TO_TIMESTAMP('2023-08-28 13:45:14.302','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.302','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.459462900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547175,TO_TIMESTAMP('2023-08-28 13:45:14.389','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.389','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.550468200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547176,TO_TIMESTAMP('2023-08-28 13:45:14.461','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.461','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.637479100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547177,TO_TIMESTAMP('2023-08-28 13:45:14.553','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.553','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.711468200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547178,TO_TIMESTAMP('2023-08-28 13:45:14.639','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.639','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.799750800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547179,TO_TIMESTAMP('2023-08-28 13:45:14.715','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.715','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.878065300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547180,TO_TIMESTAMP('2023-08-28 13:45:14.803','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.803','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:14.961323500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547181,TO_TIMESTAMP('2023-08-28 13:45:14.882','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.882','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.039753200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547182,TO_TIMESTAMP('2023-08-28 13:45:14.965','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:14.965','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.123283900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547183,TO_TIMESTAMP('2023-08-28 13:45:15.044','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.044','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.220405Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547184,TO_TIMESTAMP('2023-08-28 13:45:15.127','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.127','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.299004Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547185,TO_TIMESTAMP('2023-08-28 13:45:15.225','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.225','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.388782900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547186,TO_TIMESTAMP('2023-08-28 13:45:15.303','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.303','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.476561400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547187,TO_TIMESTAMP('2023-08-28 13:45:15.392','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.392','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.569346500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540201,547188,TO_TIMESTAMP('2023-08-28 13:45:15.48','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.48','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.658543600Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540202,540020,TO_TIMESTAMP('2023-08-28 13:45:15.574','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2024-02-29','YYYY-MM-DD'),'Y','Feb.-24',8,'S','N',TO_TIMESTAMP('2024-02-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:45:15.574','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.663542900Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540202 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:45:15.804196800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547189,TO_TIMESTAMP('2023-08-28 13:45:15.717','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.717','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.885121500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547190,TO_TIMESTAMP('2023-08-28 13:45:15.807','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.807','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:15.971623100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547191,TO_TIMESTAMP('2023-08-28 13:45:15.889','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.889','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.056403Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547192,TO_TIMESTAMP('2023-08-28 13:45:15.975','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:15.975','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.129810500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547193,TO_TIMESTAMP('2023-08-28 13:45:16.058','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.058','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.252634800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547194,TO_TIMESTAMP('2023-08-28 13:45:16.133','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.133','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.334355200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547195,TO_TIMESTAMP('2023-08-28 13:45:16.255','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.255','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.420999700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547196,TO_TIMESTAMP('2023-08-28 13:45:16.337','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.337','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.513858100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547197,TO_TIMESTAMP('2023-08-28 13:45:16.424','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.424','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.603891800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547198,TO_TIMESTAMP('2023-08-28 13:45:16.517','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.517','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.688421800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547199,TO_TIMESTAMP('2023-08-28 13:45:16.607','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.607','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.782852Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547200,TO_TIMESTAMP('2023-08-28 13:45:16.692','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.692','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.871405100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547201,TO_TIMESTAMP('2023-08-28 13:45:16.787','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.787','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:16.957405600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547202,TO_TIMESTAMP('2023-08-28 13:45:16.875','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.875','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.046924200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547203,TO_TIMESTAMP('2023-08-28 13:45:16.961','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:16.961','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.130772600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547204,TO_TIMESTAMP('2023-08-28 13:45:17.049','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.049','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.219824200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547205,TO_TIMESTAMP('2023-08-28 13:45:17.135','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.135','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.306720600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547206,TO_TIMESTAMP('2023-08-28 13:45:17.223','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.223','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.392303300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547207,TO_TIMESTAMP('2023-08-28 13:45:17.309','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.309','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.483593100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547208,TO_TIMESTAMP('2023-08-28 13:45:17.396','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.396','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.572439400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547209,TO_TIMESTAMP('2023-08-28 13:45:17.487','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.487','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.659495Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547210,TO_TIMESTAMP('2023-08-28 13:45:17.576','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.576','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.747496500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547211,TO_TIMESTAMP('2023-08-28 13:45:17.662','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.662','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.843279500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547212,TO_TIMESTAMP('2023-08-28 13:45:17.751','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.751','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:17.927556100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547213,TO_TIMESTAMP('2023-08-28 13:45:17.845','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.021060700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547214,TO_TIMESTAMP('2023-08-28 13:45:17.931','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:17.931','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.107547200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547215,TO_TIMESTAMP('2023-08-28 13:45:18.025','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.025','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.203156200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547216,TO_TIMESTAMP('2023-08-28 13:45:18.111','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.111','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.286783600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547217,TO_TIMESTAMP('2023-08-28 13:45:18.207','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.207','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.390041500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547218,TO_TIMESTAMP('2023-08-28 13:45:18.29','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.29','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.479134400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547219,TO_TIMESTAMP('2023-08-28 13:45:18.394','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.394','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.562971400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547220,TO_TIMESTAMP('2023-08-28 13:45:18.481','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.481','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.647518400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547221,TO_TIMESTAMP('2023-08-28 13:45:18.566','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.566','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.725840100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547222,TO_TIMESTAMP('2023-08-28 13:45:18.648','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.648','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.814956300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547223,TO_TIMESTAMP('2023-08-28 13:45:18.728','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.728','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.900950Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547224,TO_TIMESTAMP('2023-08-28 13:45:18.818','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.818','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:18.986980300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547225,TO_TIMESTAMP('2023-08-28 13:45:18.905','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.905','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.069709700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547226,TO_TIMESTAMP('2023-08-28 13:45:18.988','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:18.988','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.155706600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547227,TO_TIMESTAMP('2023-08-28 13:45:19.072','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:19.072','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.243825900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547228,TO_TIMESTAMP('2023-08-28 13:45:19.157','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:19.157','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.329970500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540202,547229,TO_TIMESTAMP('2023-08-28 13:45:19.246','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:19.246','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.434671500Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540203,540020,TO_TIMESTAMP('2023-08-28 13:45:19.336','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2024-03-31','YYYY-MM-DD'),'Y','März-24',9,'S','N',TO_TIMESTAMP('2024-03-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:45:19.336','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.438466900Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540203 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:45:19.580170800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547230,TO_TIMESTAMP('2023-08-28 13:45:19.493','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:19.493','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.663785100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547231,TO_TIMESTAMP('2023-08-28 13:45:19.584','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:19.584','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.753725Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547232,TO_TIMESTAMP('2023-08-28 13:45:19.667','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:19.667','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.843847400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547233,TO_TIMESTAMP('2023-08-28 13:45:19.755','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:19.755','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:19.933225400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547234,TO_TIMESTAMP('2023-08-28 13:45:19.845','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:19.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.025347400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547235,TO_TIMESTAMP('2023-08-28 13:45:19.937','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:19.937','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.108054500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547236,TO_TIMESTAMP('2023-08-28 13:45:20.029','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.029','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.197432Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547237,TO_TIMESTAMP('2023-08-28 13:45:20.112','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.112','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.290383400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547238,TO_TIMESTAMP('2023-08-28 13:45:20.201','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.201','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.372158400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547239,TO_TIMESTAMP('2023-08-28 13:45:20.294','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.294','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.456641600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547240,TO_TIMESTAMP('2023-08-28 13:45:20.376','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.376','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.538055100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547241,TO_TIMESTAMP('2023-08-28 13:45:20.46','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.46','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.630002800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547242,TO_TIMESTAMP('2023-08-28 13:45:20.541','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.541','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.719371600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547243,TO_TIMESTAMP('2023-08-28 13:45:20.634','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.634','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.802937700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547244,TO_TIMESTAMP('2023-08-28 13:45:20.722','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.722','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.891137700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547245,TO_TIMESTAMP('2023-08-28 13:45:20.807','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.807','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:20.976133500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547246,TO_TIMESTAMP('2023-08-28 13:45:20.895','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.895','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.060696600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547247,TO_TIMESTAMP('2023-08-28 13:45:20.98','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:20.98','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.147621800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547248,TO_TIMESTAMP('2023-08-28 13:45:21.064','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.064','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.241349700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547249,TO_TIMESTAMP('2023-08-28 13:45:21.151','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.151','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.321442500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547250,TO_TIMESTAMP('2023-08-28 13:45:21.246','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.246','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.415922200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547251,TO_TIMESTAMP('2023-08-28 13:45:21.325','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.325','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.503783600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547252,TO_TIMESTAMP('2023-08-28 13:45:21.419','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.419','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.580814300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547253,TO_TIMESTAMP('2023-08-28 13:45:21.507','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.507','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.664600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547254,TO_TIMESTAMP('2023-08-28 13:45:21.584','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.584','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.756767800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547255,TO_TIMESTAMP('2023-08-28 13:45:21.668','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.668','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.847690900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547256,TO_TIMESTAMP('2023-08-28 13:45:21.758','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.758','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:21.930040800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547257,TO_TIMESTAMP('2023-08-28 13:45:21.849','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.849','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.018361200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547258,TO_TIMESTAMP('2023-08-28 13:45:21.932','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:21.932','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.103575500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547259,TO_TIMESTAMP('2023-08-28 13:45:22.021','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.021','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.206366200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547260,TO_TIMESTAMP('2023-08-28 13:45:22.107','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.107','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.284064900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547261,TO_TIMESTAMP('2023-08-28 13:45:22.208','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.208','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.380810500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547262,TO_TIMESTAMP('2023-08-28 13:45:22.286','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.286','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.472830100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547263,TO_TIMESTAMP('2023-08-28 13:45:22.384','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.384','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.562317300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547264,TO_TIMESTAMP('2023-08-28 13:45:22.476','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.476','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.648183700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547265,TO_TIMESTAMP('2023-08-28 13:45:22.566','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.566','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.736632600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547266,TO_TIMESTAMP('2023-08-28 13:45:22.652','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.652','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.821676700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547267,TO_TIMESTAMP('2023-08-28 13:45:22.74','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.74','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:22.915330500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547268,TO_TIMESTAMP('2023-08-28 13:45:22.825','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.825','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.001325400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547269,TO_TIMESTAMP('2023-08-28 13:45:22.919','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:22.919','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.091978500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540203,547270,TO_TIMESTAMP('2023-08-28 13:45:23.005','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.005','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.178798100Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540204,540020,TO_TIMESTAMP('2023-08-28 13:45:23.098','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2024-04-30','YYYY-MM-DD'),'Y','Apr.-24',10,'S','N',TO_TIMESTAMP('2024-04-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:45:23.098','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.182546800Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540204 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:45:23.329242700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547271,TO_TIMESTAMP('2023-08-28 13:45:23.239','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.239','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.417848100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547272,TO_TIMESTAMP('2023-08-28 13:45:23.332','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.332','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.508153400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547273,TO_TIMESTAMP('2023-08-28 13:45:23.421','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.421','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.591798600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547274,TO_TIMESTAMP('2023-08-28 13:45:23.512','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.512','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.677587700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547275,TO_TIMESTAMP('2023-08-28 13:45:23.595','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.595','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.785065400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547276,TO_TIMESTAMP('2023-08-28 13:45:23.681','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.681','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.873778300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547277,TO_TIMESTAMP('2023-08-28 13:45:23.79','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.79','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:23.963133900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547278,TO_TIMESTAMP('2023-08-28 13:45:23.877','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.877','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.042761200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547279,TO_TIMESTAMP('2023-08-28 13:45:23.967','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:23.967','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.121843800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547280,TO_TIMESTAMP('2023-08-28 13:45:24.045','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.045','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.205749200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547281,TO_TIMESTAMP('2023-08-28 13:45:24.124','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.124','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.292553Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547282,TO_TIMESTAMP('2023-08-28 13:45:24.207','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.207','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.382009700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547283,TO_TIMESTAMP('2023-08-28 13:45:24.296','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.296','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.472341300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547284,TO_TIMESTAMP('2023-08-28 13:45:24.385','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.385','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.567242200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547285,TO_TIMESTAMP('2023-08-28 13:45:24.475','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.475','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.654589900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547286,TO_TIMESTAMP('2023-08-28 13:45:24.571','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.571','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.741838300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547287,TO_TIMESTAMP('2023-08-28 13:45:24.657','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.657','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.824755700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547288,TO_TIMESTAMP('2023-08-28 13:45:24.745','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.745','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:24.918726700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547289,TO_TIMESTAMP('2023-08-28 13:45:24.828','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.828','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.008507900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547290,TO_TIMESTAMP('2023-08-28 13:45:24.921','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:24.921','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.091707500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547291,TO_TIMESTAMP('2023-08-28 13:45:25.012','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.012','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.218587200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547292,TO_TIMESTAMP('2023-08-28 13:45:25.095','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.095','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.310016900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547293,TO_TIMESTAMP('2023-08-28 13:45:25.22','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.22','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.401028800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547294,TO_TIMESTAMP('2023-08-28 13:45:25.314','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.314','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.489763700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547295,TO_TIMESTAMP('2023-08-28 13:45:25.405','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.405','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.577209Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547296,TO_TIMESTAMP('2023-08-28 13:45:25.493','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.493','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.667016700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547297,TO_TIMESTAMP('2023-08-28 13:45:25.581','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.581','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.748359100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547298,TO_TIMESTAMP('2023-08-28 13:45:25.669','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.669','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.844662800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547299,TO_TIMESTAMP('2023-08-28 13:45:25.752','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.752','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:25.937331900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547300,TO_TIMESTAMP('2023-08-28 13:45:25.848','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.848','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.025332100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547301,TO_TIMESTAMP('2023-08-28 13:45:25.942','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:25.942','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.112534500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547302,TO_TIMESTAMP('2023-08-28 13:45:26.029','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.029','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.205150900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547303,TO_TIMESTAMP('2023-08-28 13:45:26.116','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.116','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.284192800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547304,TO_TIMESTAMP('2023-08-28 13:45:26.209','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.209','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.375893Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547305,TO_TIMESTAMP('2023-08-28 13:45:26.289','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.289','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.460747100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547306,TO_TIMESTAMP('2023-08-28 13:45:26.379','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.379','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.545296300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547307,TO_TIMESTAMP('2023-08-28 13:45:26.464','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.464','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.636211Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547308,TO_TIMESTAMP('2023-08-28 13:45:26.548','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.548','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.729740100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547309,TO_TIMESTAMP('2023-08-28 13:45:26.641','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.641','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.824023500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547310,TO_TIMESTAMP('2023-08-28 13:45:26.733','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.733','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:26.917038600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540204,547311,TO_TIMESTAMP('2023-08-28 13:45:26.828','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:26.828','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.010188600Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540205,540020,TO_TIMESTAMP('2023-08-28 13:45:26.923','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2024-05-31','YYYY-MM-DD'),'Y','Mai-24',11,'S','N',TO_TIMESTAMP('2024-05-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:45:26.923','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.013205800Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540205 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:45:27.159569200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547312,TO_TIMESTAMP('2023-08-28 13:45:27.066','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.066','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.260127800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547313,TO_TIMESTAMP('2023-08-28 13:45:27.163','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.163','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.361834800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547314,TO_TIMESTAMP('2023-08-28 13:45:27.264','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.264','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.451444200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547315,TO_TIMESTAMP('2023-08-28 13:45:27.364','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.364','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.541902500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547316,TO_TIMESTAMP('2023-08-28 13:45:27.456','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.456','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.628619900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547317,TO_TIMESTAMP('2023-08-28 13:45:27.545','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.545','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.724236100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547318,TO_TIMESTAMP('2023-08-28 13:45:27.632','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.632','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.809969100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547319,TO_TIMESTAMP('2023-08-28 13:45:27.727','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.727','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.895726300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547320,TO_TIMESTAMP('2023-08-28 13:45:27.813','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.813','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:27.984774400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547321,TO_TIMESTAMP('2023-08-28 13:45:27.899','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.899','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.066103800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547322,TO_TIMESTAMP('2023-08-28 13:45:27.986','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:27.986','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.159239700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547323,TO_TIMESTAMP('2023-08-28 13:45:28.069','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.069','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.243058900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547324,TO_TIMESTAMP('2023-08-28 13:45:28.163','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.163','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.330504700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547325,TO_TIMESTAMP('2023-08-28 13:45:28.247','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.247','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.420705700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547326,TO_TIMESTAMP('2023-08-28 13:45:28.334','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.334','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.508299900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547327,TO_TIMESTAMP('2023-08-28 13:45:28.425','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.425','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.592607200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547328,TO_TIMESTAMP('2023-08-28 13:45:28.512','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.512','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.684204600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547329,TO_TIMESTAMP('2023-08-28 13:45:28.596','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.596','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.771261300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547330,TO_TIMESTAMP('2023-08-28 13:45:28.688','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.688','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.855628200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547331,TO_TIMESTAMP('2023-08-28 13:45:28.776','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.776','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:28.944285500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547332,TO_TIMESTAMP('2023-08-28 13:45:28.859','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.859','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.036775500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547333,TO_TIMESTAMP('2023-08-28 13:45:28.948','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:28.948','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.130207400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547334,TO_TIMESTAMP('2023-08-28 13:45:29.041','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.041','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.219294100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547335,TO_TIMESTAMP('2023-08-28 13:45:29.133','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.133','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.300783800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547336,TO_TIMESTAMP('2023-08-28 13:45:29.223','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.223','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.385041700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547337,TO_TIMESTAMP('2023-08-28 13:45:29.304','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.304','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.472823Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547338,TO_TIMESTAMP('2023-08-28 13:45:29.389','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.389','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.567685300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547339,TO_TIMESTAMP('2023-08-28 13:45:29.477','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.477','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.666877Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547340,TO_TIMESTAMP('2023-08-28 13:45:29.569','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.569','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.759902Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547341,TO_TIMESTAMP('2023-08-28 13:45:29.67','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.67','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.846185Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547342,TO_TIMESTAMP('2023-08-28 13:45:29.762','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.762','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:29.941036600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547343,TO_TIMESTAMP('2023-08-28 13:45:29.849','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.849','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.025536100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547344,TO_TIMESTAMP('2023-08-28 13:45:29.945','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:29.945','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.116850300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547345,TO_TIMESTAMP('2023-08-28 13:45:30.029','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.029','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.207562700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547346,TO_TIMESTAMP('2023-08-28 13:45:30.12','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.12','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.290574800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547347,TO_TIMESTAMP('2023-08-28 13:45:30.211','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.211','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.380221800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547348,TO_TIMESTAMP('2023-08-28 13:45:30.294','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.294','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.469301700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547349,TO_TIMESTAMP('2023-08-28 13:45:30.384','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.384','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.564349700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547350,TO_TIMESTAMP('2023-08-28 13:45:30.474','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.474','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.646974400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547351,TO_TIMESTAMP('2023-08-28 13:45:30.568','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.568','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.733333Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540205,547352,TO_TIMESTAMP('2023-08-28 13:45:30.648','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.648','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.834103600Z
INSERT INTO C_Period (AD_Client_ID,AD_Org_ID,C_Period_ID,C_Year_ID,Created,CreatedBy,EndDate,IsActive,Name,PeriodNo,PeriodType,Processing,StartDate,Updated,UpdatedBy) VALUES (1000000,0,540206,540020,TO_TIMESTAMP('2023-08-28 13:45:30.739','YYYY-MM-DD HH24:MI:SS.US'),100,TO_TIMESTAMP('2024-06-30','YYYY-MM-DD'),'Y','Juni-24',12,'S','N',TO_TIMESTAMP('2024-06-01','YYYY-MM-DD'),TO_TIMESTAMP('2023-08-28 13:45:30.739','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:30.837134100Z
INSERT INTO C_Period_Trl (AD_Language,C_Period_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Period_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Period t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_Period_ID=540206 AND NOT EXISTS (SELECT 1 FROM C_Period_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Period_ID=t.C_Period_ID)
;

-- 2023-08-28T10:45:30.973998200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547353,TO_TIMESTAMP('2023-08-28 13:45:30.888','YYYY-MM-DD HH24:MI:SS.US'),100,'AVI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.888','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.064587900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547354,TO_TIMESTAMP('2023-08-28 13:45:30.977','YYYY-MM-DD HH24:MI:SS.US'),100,'HRP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:30.977','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.161150600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547355,TO_TIMESTAMP('2023-08-28 13:45:31.068','YYYY-MM-DD HH24:MI:SS.US'),100,'MCC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.068','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.251593500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547356,TO_TIMESTAMP('2023-08-28 13:45:31.165','YYYY-MM-DD HH24:MI:SS.US'),100,'AEI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.165','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.342831300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547357,TO_TIMESTAMP('2023-08-28 13:45:31.255','YYYY-MM-DD HH24:MI:SS.US'),100,'GLJ','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.255','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.429601700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547358,TO_TIMESTAMP('2023-08-28 13:45:31.346','YYYY-MM-DD HH24:MI:SS.US'),100,'ARC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.346','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.524149200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547359,TO_TIMESTAMP('2023-08-28 13:45:31.433','YYYY-MM-DD HH24:MI:SS.US'),100,'MXI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.433','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.620377500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547360,TO_TIMESTAMP('2023-08-28 13:45:31.528','YYYY-MM-DD HH24:MI:SS.US'),100,'MXP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.528','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.709294900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547361,TO_TIMESTAMP('2023-08-28 13:45:31.625','YYYY-MM-DD HH24:MI:SS.US'),100,'POR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.625','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.791411200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547362,TO_TIMESTAMP('2023-08-28 13:45:31.712','YYYY-MM-DD HH24:MI:SS.US'),100,'MMM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.712','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.877186800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547363,TO_TIMESTAMP('2023-08-28 13:45:31.793','YYYY-MM-DD HH24:MI:SS.US'),100,'MMP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.793','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:31.965044500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547364,TO_TIMESTAMP('2023-08-28 13:45:31.881','YYYY-MM-DD HH24:MI:SS.US'),100,'PJI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.881','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.054316600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547365,TO_TIMESTAMP('2023-08-28 13:45:31.97','YYYY-MM-DD HH24:MI:SS.US'),100,'GLD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:31.97','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.145821100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547366,TO_TIMESTAMP('2023-08-28 13:45:32.056','YYYY-MM-DD HH24:MI:SS.US'),100,'ARF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.056','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.227345100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547367,TO_TIMESTAMP('2023-08-28 13:45:32.149','YYYY-MM-DD HH24:MI:SS.US'),100,'CMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.149','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.318328600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547368,TO_TIMESTAMP('2023-08-28 13:45:32.23','YYYY-MM-DD HH24:MI:SS.US'),100,'POO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.23','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.410477600Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547369,TO_TIMESTAMP('2023-08-28 13:45:32.322','YYYY-MM-DD HH24:MI:SS.US'),100,'SOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.322','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.504196900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547370,TO_TIMESTAMP('2023-08-28 13:45:32.414','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.414','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.586167100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547371,TO_TIMESTAMP('2023-08-28 13:45:32.508','YYYY-MM-DD HH24:MI:SS.US'),100,'CON','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.508','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.665045500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547372,TO_TIMESTAMP('2023-08-28 13:45:32.589','YYYY-MM-DD HH24:MI:SS.US'),100,'MMR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.589','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.748199300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547373,TO_TIMESTAMP('2023-08-28 13:45:32.669','YYYY-MM-DD HH24:MI:SS.US'),100,'MST','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.669','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.842594200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547374,TO_TIMESTAMP('2023-08-28 13:45:32.752','YYYY-MM-DD HH24:MI:SS.US'),100,'CMB','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.752','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:32.935333200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547375,TO_TIMESTAMP('2023-08-28 13:45:32.845','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.019903100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547376,TO_TIMESTAMP('2023-08-28 13:45:32.939','YYYY-MM-DD HH24:MI:SS.US'),100,'API','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:32.939','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.111946700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547377,TO_TIMESTAMP('2023-08-28 13:45:33.023','YYYY-MM-DD HH24:MI:SS.US'),100,'ARR','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.023','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.204192Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547378,TO_TIMESTAMP('2023-08-28 13:45:33.114','YYYY-MM-DD HH24:MI:SS.US'),100,'APP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.114','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.297984Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547379,TO_TIMESTAMP('2023-08-28 13:45:33.208','YYYY-MM-DD HH24:MI:SS.US'),100,'MQO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.208','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.397001500Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547380,TO_TIMESTAMP('2023-08-28 13:45:33.302','YYYY-MM-DD HH24:MI:SS.US'),100,'MOP','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.302','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.480262200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547381,TO_TIMESTAMP('2023-08-28 13:45:33.399','YYYY-MM-DD HH24:MI:SS.US'),100,'MOF','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.399','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.583958300Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547382,TO_TIMESTAMP('2023-08-28 13:45:33.484','YYYY-MM-DD HH24:MI:SS.US'),100,'DUN','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.484','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.669443700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547383,TO_TIMESTAMP('2023-08-28 13:45:33.587','YYYY-MM-DD HH24:MI:SS.US'),100,'APC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.587','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.769473800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547384,TO_TIMESTAMP('2023-08-28 13:45:33.671','YYYY-MM-DD HH24:MI:SS.US'),100,'MMI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.671','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.860354400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547385,TO_TIMESTAMP('2023-08-28 13:45:33.771','YYYY-MM-DD HH24:MI:SS.US'),100,'CUI','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.771','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:33.954007100Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547386,TO_TIMESTAMP('2023-08-28 13:45:33.862','YYYY-MM-DD HH24:MI:SS.US'),100,'SDC','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.862','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:34.043149700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547387,TO_TIMESTAMP('2023-08-28 13:45:33.956','YYYY-MM-DD HH24:MI:SS.US'),100,'SDD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:33.956','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:34.121299200Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547388,TO_TIMESTAMP('2023-08-28 13:45:34.046','YYYY-MM-DD HH24:MI:SS.US'),100,'MRO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:34.046','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:34.222800Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547389,TO_TIMESTAMP('2023-08-28 13:45:34.123','YYYY-MM-DD HH24:MI:SS.US'),100,'RMA','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:34.123','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:34.340894Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547390,TO_TIMESTAMP('2023-08-28 13:45:34.225','YYYY-MM-DD HH24:MI:SS.US'),100,'CRD','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:34.225','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:34.426847900Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547391,TO_TIMESTAMP('2023-08-28 13:45:34.344','YYYY-MM-DD HH24:MI:SS.US'),100,'DOO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:34.344','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:34.513607700Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547392,TO_TIMESTAMP('2023-08-28 13:45:34.43','YYYY-MM-DD HH24:MI:SS.US'),100,'BOM','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:34.43','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-28T10:45:34.591580400Z
INSERT INTO C_PeriodControl (AD_Client_ID,AD_Org_ID,C_Period_ID,C_PeriodControl_ID,Created,CreatedBy,DocBaseType,IsActive,PeriodAction,PeriodStatus,Processing,Updated,UpdatedBy) VALUES (1000000,0,540206,547393,TO_TIMESTAMP('2023-08-28 13:45:34.517','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','Y','N','N','N',TO_TIMESTAMP('2023-08-28 13:45:34.517','YYYY-MM-DD HH24:MI:SS.US'),100)
;

