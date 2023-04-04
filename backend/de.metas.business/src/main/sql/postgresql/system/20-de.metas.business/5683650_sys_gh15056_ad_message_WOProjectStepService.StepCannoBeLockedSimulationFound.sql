

-- 2023-04-03T08:00:10.528Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545257,0,TO_TIMESTAMP('2023-04-03 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cannot be locked. Step has different simulated dates than actual data on simulation {0}.','E',TO_TIMESTAMP('2023-04-03 11:00:10','YYYY-MM-DD HH24:MI:SS'),100,'WOProjectStepService.StepCannotBeLockeSimulationFound')
;

-- 2023-04-03T08:00:10.539Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545257 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-04-03T08:00:25.092Z
UPDATE AD_Message_Trl SET MsgText='Kann nicht gesperrt werden. Schritt hat andere simulierte Daten als die tatsächlichen Daten der Simulation {0}.',Updated=TO_TIMESTAMP('2023-04-03 11:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545257
;

-- 2023-04-03T08:00:28.849Z
UPDATE AD_Message_Trl SET MsgText='Kann nicht gesperrt werden. Schritt hat andere simulierte Daten als die tatsächlichen Daten der Simulation {0}.',Updated=TO_TIMESTAMP('2023-04-03 11:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545257
;

-- 2023-04-03T08:00:33.430Z
UPDATE AD_Message_Trl SET MsgText='Kann nicht gesperrt werden. Schritt hat andere simulierte Daten als die tatsächlichen Daten der Simulation {0}.',Updated=TO_TIMESTAMP('2023-04-03 11:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545257
;

