-- 2020-11-10T15:51:15.916Z
-- URL zum Konzept
UPDATE R_StatusCategory SET Name='Teststellung',Updated=TO_TIMESTAMP('2020-11-10 17:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_StatusCategory_ID=1000001
;

-- 2020-11-10T15:51:39.986Z
-- URL zum Konzept
UPDATE R_StatusCategory SET Name='Aufgabe',Updated=TO_TIMESTAMP('2020-11-10 17:51:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_StatusCategory_ID=1000001
;

-- 2020-11-10T15:51:53.595Z
-- URL zum Konzept
INSERT INTO R_StatusCategory (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsDefault,Name,R_StatusCategory_ID,Updated,UpdatedBy) VALUES (1000000,0,TO_TIMESTAMP('2020-11-10 17:51:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Teststellung',540015,TO_TIMESTAMP('2020-11-10 17:51:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-10T15:53:05.806Z
-- URL zum Konzept
UPDATE R_StatusCategory SET IsActive='N',Updated=TO_TIMESTAMP('2020-11-10 17:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_StatusCategory_ID=540015
;

-- 2020-11-10T15:56:57.012Z
-- URL zum Konzept
INSERT INTO R_RequestType (AD_Client_ID,AD_Org_ID,AutoDueDateDays,CCM_Default,ConfidentialType,Created,CreatedBy,DueDateTolerance,InternalName,IsActive,IsAutoChangeRequest,IsConfidentialInfo,IsDefault,IsDefaultForEMail,IsDefaultForLetter,IsEMailWhenDue,IsEMailWhenOverdue,IsIndexed,IsInvoiced,IsSelfService,IsUseForPartnerRequestWindow,Name,R_RequestType_ID,R_StatusCategory_ID,Updated,UpdatedBy) VALUES (1000000,0,0,'N','C',TO_TIMESTAMP('2020-11-10 17:56:55','YYYY-MM-DD HH24:MI:SS'),100,7,'TestAppliance','Y','N','N','N','N','N','N','N','N','N','Y','Y','Testellung',540100,1000001,TO_TIMESTAMP('2020-11-10 17:56:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-10T15:56:57.080Z
-- URL zum Konzept
INSERT INTO R_RequestType_Trl (AD_Language,R_RequestType_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_RequestType_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_RequestType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.R_RequestType_ID=540100 AND NOT EXISTS (SELECT 1 FROM R_RequestType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_RequestType_ID=t.R_RequestType_ID)
;

-- 2020-11-10T15:57:40.428Z
-- URL zum Konzept
UPDATE R_RequestType SET InternalName='T_TestAppliance',Updated=TO_TIMESTAMP('2020-11-10 17:57:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_RequestType_ID=540100
;

