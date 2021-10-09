-- 2021-01-09T12:38:10.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO R_RequestType (AD_Client_ID,AD_Org_ID,AutoDueDateDays,CCM_Default,ConfidentialType,Created,CreatedBy,DueDateTolerance,IsActive,IsAutoChangeRequest,IsConfidentialInfo,IsDefault,IsDefaultForEMail,IsDefaultForLetter,IsEMailWhenDue,IsEMailWhenOverdue,IsIndexed,IsInvoiced,IsSelfService,IsUseForPartnerRequestWindow,Name,R_RequestType_ID,R_StatusCategory_ID,Updated,UpdatedBy) VALUES (1000000,1000000,0,'N','C',TO_TIMESTAMP('2021-01-09 14:38:10','YYYY-MM-DD HH24:MI:SS'),2188225,7,'Y','N','N','N','N','N','N','N','N','N','Y','N','Service Annahme',540102,1000001,TO_TIMESTAMP('2021-01-09 14:38:10','YYYY-MM-DD HH24:MI:SS'),2188225)
;

-- 2021-01-09T12:38:10.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO R_RequestType_Trl (AD_Language,R_RequestType_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.R_RequestType_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, R_RequestType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.R_RequestType_ID=540102 AND NOT EXISTS (SELECT 1 FROM R_RequestType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.R_RequestType_ID=t.R_RequestType_ID)
;

-- 2021-01-09T12:39:28.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET R_RequestType_ID=540102,Updated=TO_TIMESTAMP('2021-01-09 14:39:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188225 WHERE C_DocType_ID=541005
;

