--
-- fixing the log from beeing filled with
-- 2016-01-20 10:42:20,306 ERROR [STDERR] 10:42:20.306 -----------> Msg.translate: NOT found: Fact_Acct_Log [96/MScheduler-Fact_Acct_Log_Process]
--

DELETE FROM AD_Message WHERE AD_Message_ID IN (543743, 543744);
DELETE FROM AD_Message_Trl WHERE AD_Message_ID IN (543743, 543744);

-- 20.01.2016 10:38
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543743,0,TO_TIMESTAMP('2016-01-20 10:38:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Fact_Acct_Log','I',TO_TIMESTAMP('2016-01-20 10:38:21','YYYY-MM-DD HH24:MI:SS'),100,'Fact_Acct_Log')
;

-- 20.01.2016 10:38
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543743 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 20.01.2016 10:39
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543744,0,TO_TIMESTAMP('2016-01-20 10:39:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Fact_Acct_Summary','I',TO_TIMESTAMP('2016-01-20 10:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Fact_Acct_Summary')
;

-- 20.01.2016 10:39
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543744 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

