-- 2022-08-18T21:52:13.644Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) 
VALUES (0,545147,0,TO_TIMESTAMP('2022-08-18 23:52:13','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',
'The Conditions {0} cannot be set because the resulting Contract would overlap in time with another Contract on the same Product.
','I',TO_TIMESTAMP('2022-08-18 23:52:13','YYYY-MM-DD HH24:MI:SS'),100,'C_OrderLine_OverlappingTerms')
;

-- 2022-08-18T21:52:13.646Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) 
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') 
AND t.AD_Message_ID=545147 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
