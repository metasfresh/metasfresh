-- 2019-07-29T13:15:13.109Z
-- URL zum Konzept
INSERT INTO R_StatusCategory (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsDefault,Name,R_StatusCategory_ID,Updated,UpdatedBy) VALUES (1000000,0,TO_TIMESTAMP('2019-07-29 15:15:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Projekt',540009,TO_TIMESTAMP('2019-07-29 15:15:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:15:35.241Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-07-29 15:15:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','Planung',540009,540026,0,0,TO_TIMESTAMP('2019-07-29 15:15:35','YYYY-MM-DD HH24:MI:SS'),100,'1000004')
;

-- 2019-07-29T13:15:35.242Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540026 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

-- 2019-07-29T13:16:01.815Z
-- URL zum Konzept
UPDATE R_Status SET Value='01',Updated=TO_TIMESTAMP('2019-07-29 15:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_Status_ID=540026
;

-- 2019-07-29T13:16:19.838Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-07-29 15:16:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','Ausführung',540009,540027,20,0,TO_TIMESTAMP('2019-07-29 15:16:19','YYYY-MM-DD HH24:MI:SS'),100,'02')
;

-- 2019-07-29T13:16:19.839Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540027 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

-- 2019-07-29T13:16:23.184Z
-- URL zum Konzept
UPDATE R_Status SET SeqNo=10,Updated=TO_TIMESTAMP('2019-07-29 15:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_Status_ID=540026
;

-- 2019-07-29T13:16:36.902Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-07-29 15:16:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Abnahme',540009,540028,30,0,TO_TIMESTAMP('2019-07-29 15:16:36','YYYY-MM-DD HH24:MI:SS'),100,'03')
;

-- 2019-07-29T13:16:36.903Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540028 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

-- 2019-07-29T13:16:47.933Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-07-29 15:16:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Erledigt',540009,540029,40,0,TO_TIMESTAMP('2019-07-29 15:16:47','YYYY-MM-DD HH24:MI:SS'),100,'04')
;

-- 2019-07-29T13:16:47.934Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540029 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

-- 2019-07-29T13:16:53.262Z
-- URL zum Konzept
UPDATE R_Status SET IsClosed='Y',Updated=TO_TIMESTAMP('2019-07-29 15:16:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_Status_ID=540029
;

-- 2019-07-29T13:17:06.056Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-07-29 15:17:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','Archiviert',540009,540030,50,0,TO_TIMESTAMP('2019-07-29 15:17:05','YYYY-MM-DD HH24:MI:SS'),100,'05')
;

-- 2019-07-29T13:17:06.057Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540030 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

