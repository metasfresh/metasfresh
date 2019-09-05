-- 2019-09-05T16:10:08.269Z
-- URL zum Konzept
INSERT INTO R_StatusCategory (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsDefault,Name,R_StatusCategory_ID,Updated,UpdatedBy) VALUES (1000000,0,TO_TIMESTAMP('2019-09-05 18:10:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Seminar',540010,TO_TIMESTAMP('2019-09-05 18:10:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T16:10:29.163Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-09-05 18:10:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','geplant',540010,540031,10,0,TO_TIMESTAMP('2019-09-05 18:10:29','YYYY-MM-DD HH24:MI:SS'),100,'10')
;

-- 2019-09-05T16:10:29.164Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540031 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

-- 2019-09-05T16:10:50.014Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-09-05 18:10:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','gebucht',540010,540032,20,0,TO_TIMESTAMP('2019-09-05 18:10:49','YYYY-MM-DD HH24:MI:SS'),100,'20')
;

-- 2019-09-05T16:10:50.014Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540032 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

-- 2019-09-05T16:11:28.678Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-09-05 18:11:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','läuft...',540010,540033,30,0,TO_TIMESTAMP('2019-09-05 18:11:28','YYYY-MM-DD HH24:MI:SS'),100,'30')
;

-- 2019-09-05T16:11:28.680Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540033 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

-- 2019-09-05T16:11:40.507Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-09-05 18:11:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','Nachbearbeitung',540010,540034,40,0,TO_TIMESTAMP('2019-09-05 18:11:40','YYYY-MM-DD HH24:MI:SS'),100,'40')
;

-- 2019-09-05T16:11:40.507Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540034 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

-- 2019-09-05T16:11:51.106Z
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2019-09-05 18:11:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','N','N','Erledigt',540010,540035,50,0,TO_TIMESTAMP('2019-09-05 18:11:51','YYYY-MM-DD HH24:MI:SS'),100,'50')
;

-- 2019-09-05T16:11:51.106Z
-- URL zum Konzept
INSERT INTO R_Status_Trl (AD_Language,R_Status_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_Status_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_Status t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.R_Status_ID=540035 AND NOT EXISTS (SELECT 1 FROM R_Status_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_Status_ID=t.R_Status_ID)
;

-- 2019-09-05T16:12:01.834Z
-- URL zum Konzept
UPDATE R_Status SET Name='Geplant',Updated=TO_TIMESTAMP('2019-09-05 18:12:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_Status_ID=540031
;

-- 2019-09-05T16:12:01.835Z
-- URL zum Konzept
UPDATE R_Status_Trl SET Description=NULL, Help=NULL, Name='Geplant', IsTranslated='Y' WHERE R_Status_ID=540031
;

-- 2019-09-05T16:12:05.620Z
-- URL zum Konzept
UPDATE R_Status SET Name='Gebucht',Updated=TO_TIMESTAMP('2019-09-05 18:12:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_Status_ID=540032
;

-- 2019-09-05T16:12:05.620Z
-- URL zum Konzept
UPDATE R_Status_Trl SET Description=NULL, Help=NULL, Name='Gebucht', IsTranslated='Y' WHERE R_Status_ID=540032
;

-- 2019-09-05T16:12:09.209Z
-- URL zum Konzept
UPDATE R_Status SET Name='Läuft...',Updated=TO_TIMESTAMP('2019-09-05 18:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_Status_ID=540033
;

-- 2019-09-05T16:12:09.210Z
-- URL zum Konzept
UPDATE R_Status_Trl SET Description=NULL, Help=NULL, Name='Läuft...', IsTranslated='Y' WHERE R_Status_ID=540033
;

