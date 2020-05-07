

-- 2017-04-04T18:52:57.001
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544290,0,TO_TIMESTAMP('2017-04-04 18:52:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Warenbewegung {0} ','I',TO_TIMESTAMP('2017-04-04 18:52:56','YYYY-MM-DD HH24:MI:SS'),100,'Event_MovementGenerated')
;

-- 2017-04-04T18:52:57.004
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544290 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-04-04T18:53:05.939
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Warenbewegung {0} wurde erstellt.',Updated=TO_TIMESTAMP('2017-04-04 18:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544290
;

-- 2017-04-04T18:53:05.941
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544290
;

