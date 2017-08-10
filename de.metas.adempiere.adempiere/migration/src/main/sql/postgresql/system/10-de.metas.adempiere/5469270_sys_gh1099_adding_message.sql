-- 2017-08-10T09:08:14.543
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544472,0,TO_TIMESTAMP('2017-08-10 09:08:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','BROWSE WHOLE TREE','I',TO_TIMESTAMP('2017-08-10 09:08:14','YYYY-MM-DD HH24:MI:SS'),100,'window.browseTree.caption')
;

-- 2017-08-10T09:08:14.553
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544472 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-08-10T09:08:37.163
-- URL zum Konzept
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-10 09:08:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544472 AND AD_Language='en_US'
;

-- 2017-08-10T09:08:46.292
-- URL zum Konzept
UPDATE AD_Message SET MsgText='GESAMTER BAUM',Updated=TO_TIMESTAMP('2017-08-10 09:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544472
;

