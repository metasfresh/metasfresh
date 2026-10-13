-- API_Audit_Config.NotifyUserInCharge: new option to notify when a call ends with an error or a partial error (HTTP 207).
-- Ref-list NotifyItems (AD_Reference 541315) + info message used for the notification.

-- Ref-list value
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544373 /*From ID Server*/,541315,TO_TIMESTAMP('2026-09-29 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Aufrufen mit Fehler oder teilweisem Fehler (207)',TO_TIMESTAMP('2026-09-29 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ERROR_OR_207','AufrufenMitFehlerOderTeilweisemFehler207')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Description,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Ref_List_ID,t.Description,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544373
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET Name='Calls with error or partial error (207)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544373
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Ref_List_ID=544373
;

-- Message
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545880 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','API-Aufruf {0} teilweise fehlgeschlagen (207)','I',TO_TIMESTAMP('2026-09-29 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.util.web.audit.invocation_partially_failed')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545880
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='API call {0} partially failed (207)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 10:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545880
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-29 10:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545880
;
