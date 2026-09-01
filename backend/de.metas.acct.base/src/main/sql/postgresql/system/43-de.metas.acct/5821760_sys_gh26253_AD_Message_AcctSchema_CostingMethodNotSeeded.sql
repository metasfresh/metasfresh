-- gh26253: message for the C_AcctSchema guard that refuses switching the costing method to a
-- method whose material cost element has no cost details at all in that accounting schema.
-- {0} = the target costing method code.

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545819 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-01 09:10:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Kostenrechnungsmethode kann nicht auf {0} umgestellt werden: Für diese Methode gibt es in diesem Buchführungsschema noch keine Kosten-Details. Bitte zuerst über einen Beleg "Kosten Neubewertung" mit Neubewertungsquelle "Übernahme aus Kostenart" die Kosten aufbauen und erst danach die Kostenrechnungsmethode umstellen.','E',TO_TIMESTAMP('2026-09-01 09:10:01','YYYY-MM-DD HH24:MI:SS'),100,'ERR_ACCTSCHEMA_COSTING_METHOD_NOT_SEEDED')
;

UPDATE AD_Message
SET ErrorCode='ACCTSCHEMA_COSTING_METHOD_NOT_SEEDED', Updated=TO_TIMESTAMP('2026-09-01 09:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545819
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545819
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='The costing method cannot be switched to {0}: there are no cost details for that method in this accounting schema yet. Please first build up the costs with a "Cost Revaluation" document using revaluation source "Copy from cost element", and only then switch the costing method.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 09:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545819
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 09:10:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545819
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-01 09:10:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545819
;
