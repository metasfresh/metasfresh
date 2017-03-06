-- 28.02.2017 11:47
-- URL zum Konzept
INSERT INTO R_StatusCategory (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsDefault,Name,R_StatusCategory_ID,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2017-02-28 11:47:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Opportunity',540004,TO_TIMESTAMP('2017-02-28 11:47:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 28.02.2017 11:47
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2017-02-28 11:47:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','N','Neu',540004,540009,0,0,TO_TIMESTAMP('2017-02-28 11:47:52','YYYY-MM-DD HH24:MI:SS'),100,'0')
;

-- 28.02.2017 11:48
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2017-02-28 11:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','25%',540004,540010,25,0,TO_TIMESTAMP('2017-02-28 11:48:23','YYYY-MM-DD HH24:MI:SS'),100,'25')
;

-- 28.02.2017 11:48
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2017-02-28 11:48:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','50%',540004,540011,50,0,TO_TIMESTAMP('2017-02-28 11:48:36','YYYY-MM-DD HH24:MI:SS'),100,'50')
;

-- 28.02.2017 11:48
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2017-02-28 11:48:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','75%',540004,540012,75,0,TO_TIMESTAMP('2017-02-28 11:48:45','YYYY-MM-DD HH24:MI:SS'),100,'75')
;

-- 28.02.2017 11:49
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2017-02-28 11:49:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','N','N','100%',540004,540013,100,0,TO_TIMESTAMP('2017-02-28 11:49:15','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 28.02.2017 11:51
-- URL zum Konzept
UPDATE R_Status SET Name='10%', SeqNo=10, Value='10',Updated=TO_TIMESTAMP('2017-02-28 11:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_Status_ID=540009
;

-- 28.02.2017 11:51
-- URL zum Konzept
INSERT INTO R_Status (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsClosed,IsDefault,IsFinalClose,IsOpen,IsWebCanUpdate,Name,R_StatusCategory_ID,R_Status_ID,SeqNo,TimeoutDays,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2017-02-28 11:51:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','N','N','0%',540004,540014,0,0,TO_TIMESTAMP('2017-02-28 11:51:50','YYYY-MM-DD HH24:MI:SS'),100,'0')
;


-- 28.02.2017 11:55
-- URL zum Konzept
INSERT INTO R_RequestType (AD_Client_ID,AD_Org_ID,AutoDueDateDays,CCM_Default,ConfidentialType,Created,CreatedBy,DueDateTolerance,InternalName,IsActive,IsAutoChangeRequest,IsConfidentialInfo,IsDefault,IsDefaultForEMail,IsDefaultForLetter,IsEMailWhenDue,IsEMailWhenOverdue,IsIndexed,IsInvoiced,IsSelfService,IsUseForPartnerRequestWindow,Name,R_RequestType_ID,R_StatusCategory_ID,Updated,UpdatedBy) VALUES (0,0,0,'N','C',TO_TIMESTAMP('2017-02-28 11:55:50','YYYY-MM-DD HH24:MI:SS'),100,7,'O_Opportunity','Y','N','N','N','N','N','N','N','N','N','Y','Y','Opportunity',540008,1000001,TO_TIMESTAMP('2017-02-28 11:55:50','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 28.02.2017 11:58
-- URL zum Konzept
UPDATE R_RequestType SET R_StatusCategory_ID=540004,Updated=TO_TIMESTAMP('2017-02-28 11:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE R_RequestType_ID=540008
;
