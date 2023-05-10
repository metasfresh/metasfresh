-- 2023-04-03T08:00:10.528Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545257,0,TO_TIMESTAMP('2023-04-03 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Step cannot be locked because it contains different simulated dates than the actual data of the simulation {0}.','E',TO_TIMESTAMP('2023-04-03 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.project.workorder.step.StepCannotBeLockedSimulationFound')
;

-- 2023-04-03T08:00:10.539Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545257 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-04-03T08:00:25.092Z
UPDATE AD_Message_Trl SET MsgText='Schritt kann nicht gesperrt werden, da er andere simulierte Datumsangaben enthält als die tatsächlichen Daten der Simulation {0}.',Updated=TO_TIMESTAMP('2023-04-03 11:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545257
;

-- 2023-04-03T08:00:28.849Z
UPDATE AD_Message_Trl SET MsgText='Schritt kann nicht gesperrt werden, da er andere simulierte Datumsangaben enthält als die tatsächlichen Daten der Simulation {0}.',Updated=TO_TIMESTAMP('2023-04-03 11:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545257
;

-- 2023-04-03T08:00:33.430Z
UPDATE AD_Message_Trl SET MsgText='Schritt kann nicht gesperrt werden, da er andere simulierte Datumsangaben enthält als die tatsächlichen Daten der Simulation {0}.',Updated=TO_TIMESTAMP('2023-04-03 11:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545257
;


-- 2023-04-12T09:08:56.348Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545260,0,TO_TIMESTAMP('2023-04-12 12:08:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Test step cannot be locked manually! Please specify a start date and an end date.','E',TO_TIMESTAMP('2023-04-12 12:08:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.project.workorder.interceptor.WOStepNoStartDateEndDateSpecified')
;

-- 2023-04-12T09:08:56.357Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545260 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-04-12T09:09:09.663Z
UPDATE AD_Message_Trl SET MsgText='Prüfschritt kann nicht manuell gesperrt werden! Bitte Startdatum und Enddatum angeben.',Updated=TO_TIMESTAMP('2023-04-12 12:09:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545260
;

-- 2023-04-12T09:09:12.433Z
UPDATE AD_Message_Trl SET MsgText='Prüfschritt kann nicht manuell gesperrt werden! Bitte Startdatum und Enddatum angeben.',Updated=TO_TIMESTAMP('2023-04-12 12:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545260
;

-- 2023-04-12T09:09:16.332Z
UPDATE AD_Message_Trl SET MsgText='Prüfschritt kann nicht manuell gesperrt werden! Bitte Startdatum und Enddatum angeben.',Updated=TO_TIMESTAMP('2023-04-12 12:09:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545260
;



-- 2023-04-12T09:15:35.001Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545261,0,TO_TIMESTAMP('2023-04-12 12:15:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Failed to update simulated test step. Test step {0} is manually locked and it cannot be shifted accordingly!','E',TO_TIMESTAMP('2023-04-12 12:15:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.project.workorder.calendar.WoStepCannotBeShifted')
;

-- 2023-04-12T09:15:35.003Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545261 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-04-12T09:16:12.678Z
UPDATE AD_Message_Trl SET MsgText='Der simulierte Prüfschritt konnte nicht aktualisiert werden. Prüfschritt {0} ist manuell gesperrt und kann nicht entsprechend verschoben werden!',Updated=TO_TIMESTAMP('2023-04-12 12:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545261
;

-- 2023-04-12T09:16:15.796Z
UPDATE AD_Message_Trl SET MsgText='Der simulierte Prüfschritt konnte nicht aktualisiert werden. Prüfschritt {0} ist manuell gesperrt und kann nicht entsprechend verschoben werden!',Updated=TO_TIMESTAMP('2023-04-12 12:16:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545261
;

-- 2023-04-12T09:16:18.358Z
UPDATE AD_Message_Trl SET MsgText='Der simulierte Prüfschritt konnte nicht aktualisiert werden. Prüfschritt {0} ist manuell gesperrt und kann nicht entsprechend verschoben werden!',Updated=TO_TIMESTAMP('2023-04-12 12:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545261
;


-- 2023-04-12T11:51:00.759Z
UPDATE AD_Message SET MsgText='Prüfschritt kann nicht manuell gesperrt werden! Bitte Startdatum und Enddatum angeben.',Updated=TO_TIMESTAMP('2023-04-12 14:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545260
;

-- 2023-04-12T11:51:49.790Z
UPDATE AD_Message SET MsgText='Der simulierte Prüfschritt konnte nicht aktualisiert werden. Prüfschritt {0} ist manuell gesperrt und kann nicht entsprechend verschoben werden!',Updated=TO_TIMESTAMP('2023-04-12 14:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545261
;

-- 2023-04-12T11:52:47.623Z
UPDATE AD_Message SET MsgText='Schritt kann nicht gesperrt werden, da er andere simulierte Datumsangaben enthält als die tatsächlichen Daten der Simulation {0}.',Updated=TO_TIMESTAMP('2023-04-12 14:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545257
;


-- 2023-04-19T14:04:32.032Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545262,0,TO_TIMESTAMP('2023-04-19 17:04:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Der Schritt {0} kann nicht hinzugefügt werden. Es gibt keinen Zeitraum mehr, der dafür vorgesehen ist. Schrittdauer {1}, verfügbare Dauer {2}.','E',TO_TIMESTAMP('2023-04-19 17:04:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.project.workorder.calendar.NoAvailableDuration')
;

-- 2023-04-19T14:04:32.044Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545262 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-04-19T14:04:48.130Z
UPDATE AD_Message_Trl SET MsgText='Fail to add step {0}. There is no period left to be allocated for it. Step duration {1}, available duration {2}.',Updated=TO_TIMESTAMP('2023-04-19 17:04:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545262
;

