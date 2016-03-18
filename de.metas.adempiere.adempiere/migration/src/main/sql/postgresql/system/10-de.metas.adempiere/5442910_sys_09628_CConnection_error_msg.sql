
-- 18.03.2016 08:10
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543828,0,TO_TIMESTAMP('2016-03-18 08:10:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Verbindungsproblem mit Anwendungsserver','E',TO_TIMESTAMP('2016-03-18 08:10:30','YYYY-MM-DD HH24:MI:SS'),100,'CConnection.AppserverConnectionProblem')
;

-- 18.03.2016 08:10
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543828 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 18.03.2016 08:11
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Problem beim Kontaktieren des metasfresh Servers',Updated=TO_TIMESTAMP('2016-03-18 08:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543828
;

-- 18.03.2016 08:11
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543828
;

