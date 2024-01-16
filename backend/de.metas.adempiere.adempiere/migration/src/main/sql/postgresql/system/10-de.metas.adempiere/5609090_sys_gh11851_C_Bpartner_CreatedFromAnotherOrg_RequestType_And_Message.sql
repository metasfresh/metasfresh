
-- 2021-10-13T08:54:50.747Z
-- URL zum Konzept
INSERT INTO R_RequestType (AD_Client_ID,AD_Org_ID,AutoDueDateDays,CCM_Default,ConfidentialType,Created,CreatedBy,DueDateTolerance,IsActive,IsAutoChangeRequest,IsConfidentialInfo,IsDefault,IsDefaultForEMail,IsDefaultForLetter,IsEMailWhenDue,IsEMailWhenOverdue,IsIndexed,IsInvoiced,IsSelfService,IsUseForPartnerRequestWindow,Name,R_RequestType_ID,R_StatusCategory_ID,Updated,UpdatedBy) VALUES (1000000,1000000,0,'N','C',TO_TIMESTAMP('2021-10-13 10:54:50','YYYY-MM-DD HH24:MI:SS'),100,7,'Y','N','N','N','N','N','N','N','N','N','Y','N','Fremdaufnahme',540108,1000001,TO_TIMESTAMP('2021-10-13 10:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-13T08:54:50.759Z
-- URL zum Konzept
INSERT INTO R_RequestType_Trl (AD_Language,R_RequestType_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.R_RequestType_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, R_RequestType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.R_RequestType_ID=540108 AND NOT EXISTS (SELECT 1 FROM R_RequestType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_RequestType_ID=t.R_RequestType_ID)
;

-- 2021-10-13T08:54:59.181Z
-- URL zum Konzept
UPDATE R_RequestType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-10-13 10:54:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_RequestType_ID=540108
;

-- 2021-10-13T08:57:01.442Z
-- URL zum Konzept
UPDATE R_RequestType SET InternalName='C_Bpartner_CreatedFromAnotherOrg',Updated=TO_TIMESTAMP('2021-10-13 10:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_RequestType_ID=540108
;








-- 2021-10-13T09:02:17.577Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545064,0,TO_TIMESTAMP('2021-10-13 12:02:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The user {0}, logged in with organization {1}, created the partner {2} for the organization {3}.','I',TO_TIMESTAMP('2021-10-13 12:02:16','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartnerCreatedFromAnotherOrg')
;

-- 2021-10-13T09:02:17.655Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545064 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;









