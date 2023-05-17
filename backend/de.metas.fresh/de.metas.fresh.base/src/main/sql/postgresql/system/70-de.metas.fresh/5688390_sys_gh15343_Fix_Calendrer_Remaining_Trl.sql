-- Value: webui.calendar.resource.area.header
-- 2023-05-17T20:26:34.212740Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545267,0,TO_TIMESTAMP('2023-05-17 21:26:33.896','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y','Ressource','I',TO_TIMESTAMP('2023-05-17 21:26:33.896','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.calendar.resource.area.header')
;

-- 2023-05-17T20:26:34.217046800Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545267 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.calendar.resource.area.header
-- 2023-05-17T20:26:58.921603500Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Resource',Updated=TO_TIMESTAMP('2023-05-17 21:26:58.921','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545267
;

-- Value: webui.key.actual.data
-- 2023-05-17T20:31:17.495619Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545268,0,TO_TIMESTAMP('2023-05-17 21:31:16.306','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y','aktuelle Daten','I',TO_TIMESTAMP('2023-05-17 21:31:16.306','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.key.actual.data')
;

-- 2023-05-17T20:31:17.497187900Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545268 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.key.actual.data
-- 2023-05-17T20:31:44.908842Z
UPDATE AD_Message SET MsgText='Aktuelle daten',Updated=TO_TIMESTAMP('2023-05-17 21:31:44.906','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545268
;

-- 2023-05-17T20:31:44.912051200Z
UPDATE AD_Message_Trl trl SET MsgText='Aktuelle daten' WHERE AD_Message_ID=545268 AND AD_Language='de_DE'
;

-- Value: webui.key.actual.data
-- 2023-05-17T20:32:16.588564Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Actual data',Updated=TO_TIMESTAMP('2023-05-17 21:32:16.588','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545268
;
-- Value: webui.option.actual.data
-- 2023-05-17T20:58:28.549201Z
UPDATE AD_Message SET Value='webui.option.actual.data',Updated=TO_TIMESTAMP('2023-05-17 21:58:28.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545268
;

-- Value: webui.option.new.simulation
-- 2023-05-17T21:00:16.219740400Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545269,0,TO_TIMESTAMP('2023-05-17 22:00:15.972','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y','Neue Simulation','I',TO_TIMESTAMP('2023-05-17 22:00:15.972','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.option.new.simulation')
;

-- 2023-05-17T21:00:16.221848500Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545269 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.option.new.simulation
-- 2023-05-17T21:00:33.243267500Z
UPDATE AD_Message_Trl SET MsgText='New simulation',Updated=TO_TIMESTAMP('2023-05-17 22:00:33.243','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545269
;

-- Value: webui.button.start.optimization
-- 2023-05-17T21:03:33.643367900Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545270,0,TO_TIMESTAMP('2023-05-17 22:03:33.144','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y','Starte Optimierung','I',TO_TIMESTAMP('2023-05-17 22:03:33.144','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.button.start.optimization')
;

-- 2023-05-17T21:03:33.645482600Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545270 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.button.start.optimization
-- 2023-05-17T21:03:48.618470100Z
UPDATE AD_Message_Trl SET MsgText='Start optimization',Updated=TO_TIMESTAMP('2023-05-17 22:03:48.618','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545270
;

-- Value: webui.conflicts.summary
-- 2023-05-17T21:20:00.078976900Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545271,0,TO_TIMESTAMP('2023-05-17 22:19:59.745','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y','Konflikte','I',TO_TIMESTAMP('2023-05-17 22:19:59.745','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.conflicts.summary')
;

-- 2023-05-17T21:20:00.080535700Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545271 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.conflicts.summary
-- 2023-05-17T21:20:25.572179Z
UPDATE AD_Message_Trl SET MsgText='conflicts',Updated=TO_TIMESTAMP('2023-05-17 22:20:25.572','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545271
;

-- Value: webui.simulation.processed
-- 2023-05-17T21:29:54.172255400Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545272,0,TO_TIMESTAMP('2023-05-17 22:29:53.786','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y','Verarbeitet: ','I',TO_TIMESTAMP('2023-05-17 22:29:53.786','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.simulation.processed')
;

-- 2023-05-17T21:29:54.173844Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545272 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.simulation.processed
-- 2023-05-17T21:30:04.787830600Z
UPDATE AD_Message_Trl SET MsgText='Processed: ',Updated=TO_TIMESTAMP('2023-05-17 22:30:04.787','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545272
;

