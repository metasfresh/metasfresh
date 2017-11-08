-- 2017-09-27T15:12:39.261
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544515,0,TO_TIMESTAMP('2017-09-27 15:12:38','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Alle Datensätze sind verarbeitet.','I',TO_TIMESTAMP('2017-09-27 15:12:38','YYYY-MM-DD HH24:MI:SS'),100,'ShipmentSchedulesAllNotProcessed')
;

-- 2017-09-27T15:12:39.277
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544515 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-09-27T15:14:33.374
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Alle Datensätze sind offen.', Value='M_ShipmentSchedule_OpenProcessed.ShipmentSchedulesAllNotProcessed',Updated=TO_TIMESTAMP('2017-09-27 15:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544515
;

-- 2017-09-27T15:14:46.621
-- URL zum Konzept
UPDATE AD_Message SET EntityType='de.metas.inoutcandidate',Updated=TO_TIMESTAMP('2017-09-27 15:14:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544515
;






-- 2017-09-27T15:34:30.075
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544516,0,TO_TIMESTAMP('2017-09-27 15:34:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Überspringe geöffneten Datensatz {0}.','I',TO_TIMESTAMP('2017-09-27 15:34:29','YYYY-MM-DD HH24:MI:SS'),100,'M_ShipmentSchedule_Close.SkipOpen_1P')
;

-- 2017-09-27T15:34:30.093
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544516 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-09-27T15:34:53.719
-- URL zum Konzept
UPDATE AD_Message SET Value='M_ShipmentSchedule_OpenProcessed.SkipOpen_1P',Updated=TO_TIMESTAMP('2017-09-27 15:34:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544516
;





