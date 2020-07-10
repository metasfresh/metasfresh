-- 2017-04-04T18:30:24.634
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544288,0,TO_TIMESTAMP('2017-04-04 18:30:24','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Kundenrücklieferung {0} für Partner {1} {2} wurde erstellt.','I',TO_TIMESTAMP('2017-04-04 18:30:24','YYYY-MM-DD HH24:MI:SS'),100,'Event_CustomerReturn_Generated')
;

-- 2017-04-04T18:30:24.642
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544288 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-04-04T18:31:09.117
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544289,0,TO_TIMESTAMP('2017-04-04 18:31:09','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Lieferantenrückgabe {0} für Partner {1} {2} wurde erstellt.','I',TO_TIMESTAMP('2017-04-04 18:31:09','YYYY-MM-DD HH24:MI:SS'),100,'Event_ReturnToVendor_Generated')
;

-- 2017-04-04T18:31:09.119
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544289 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-04-04T18:32:51.885
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Lieferantenrücklieferung {0} für Partner {1} {2} wurde erstellt.',Updated=TO_TIMESTAMP('2017-04-04 18:32:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544289
;

-- 2017-04-04T18:32:51.887
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544289
;

-- 2017-04-04T18:33:42.814
-- URL zum Konzept
UPDATE AD_Message SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2017-04-04 18:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544289
;

-- 2017-04-04T18:33:54.028
-- URL zum Konzept
UPDATE AD_Message SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2017-04-04 18:33:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544288
;