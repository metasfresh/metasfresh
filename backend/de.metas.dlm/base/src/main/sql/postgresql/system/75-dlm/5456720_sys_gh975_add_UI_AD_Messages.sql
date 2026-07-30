-- 16.02.2017 15:03:15
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544267,0,TO_TIMESTAMP('2017-02-16 15:03:14','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.dlm','Y','de.metas.dlm.DLM_Level','I',TO_TIMESTAMP('2017-02-16 15:03:14','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.dlm.DLM_Level')
;

-- 16.02.2017 15:03:15
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544267 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


-- 16.02.2017 15:07
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Data Lifecycle Management Sichtbarkeit',Updated=TO_TIMESTAMP('2017-02-16 15:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544267
;

-- 16.02.2017 15:07
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544267
;

-- 16.02.2017 15:09
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544269,0,TO_TIMESTAMP('2017-02-16 15:09:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','nur Produktivdaten','I',TO_TIMESTAMP('2017-02-16 15:09:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm.DLM_Level_LIVE')
;

-- 16.02.2017 15:09
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544269 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 16.02.2017 15:10
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544270,0,TO_TIMESTAMP('2017-02-16 15:10:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Produktivdaten und archivierte Daten','I',TO_TIMESTAMP('2017-02-16 15:10:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm.DLM_Level_ARCHIVE')
;

-- 16.02.2017 15:10
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544270 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

