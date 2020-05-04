

---------!!!!!!!!!!!!!!!!!!!!!! PLACEHOLDERS !!!!!!!!!!!!!!!!!!!!!!!!-----------------------------




-- 09.09.2016 14:35
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543962,0,TO_TIMESTAMP('2016-09-09 14:35:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','R_Request_From_InOut_Summary placeholder','I',TO_TIMESTAMP('2016-09-09 14:35:04','YYYY-MM-DD HH24:MI:SS'),100,'R_Request_From_InOut_Summary')
;

-- 09.09.2016 14:35
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543962 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 09.09.2016 14:35
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543963,0,TO_TIMESTAMP('2016-09-09 14:35:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','R_Request_From_InOut_Result placeholder','I',TO_TIMESTAMP('2016-09-09 14:35:27','YYYY-MM-DD HH24:MI:SS'),100,'R_Request_From_InOut_Result')
;

-- 09.09.2016 14:35
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543963 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

