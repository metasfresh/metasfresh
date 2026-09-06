-- Reason shown instead of the manufacturing post-calculation action when the order's total inbound cost is
-- zero, i.e. it received value with no component ever issued. What such an order displays as a cost
-- difference is its entire receipt, so discharging it would remove the whole manufactured value from stock
-- and close the order. Refused WITH this reason rather than silently hidden, because the order does show a
-- difference and the controller needs to be told why the action is unavailable.
--
-- MsgType 'E' (a refusal) WITH a short ErrorCode, per the AD_Message recipe: an 'E' message must carry one so
-- an API consumer can handle this specific refusal programmatically. (Many sibling 'E' messages in this module
-- omit it; that is pre-existing drift, not a precedent to copy.)

-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545829/*From ID Server*/,0,TO_TIMESTAMP('2026-09-04 21:30:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
        'Für diesen Fertigungsauftrag wurde keine Komponente zugeteilt. Die angezeigte Kostendifferenz ist der vollständige Wareneingang und keine Kostendifferenz – eine Nachberechnung würde den gesamten Herstellwert aus dem Bestand entfernen und den Auftrag schließen. Bitte zuerst die Komponentenzuteilung nachtragen.',
        'E',TO_TIMESTAMP('2026-09-04 21:30:00','YYYY-MM-DD HH24:MI:SS'),100,'org.eevolution.process.PP_Order_PostCalculation.NoComponentIssued');

-- 2. the short ErrorCode (the full key lives in Value)
UPDATE AD_Message SET ErrorCode='PPOrderNoComponentIssued',
       Updated=TO_TIMESTAMP('2026-09-04 21:30:00.500000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100
WHERE AD_Message_ID=545829;

-- 3. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545829
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

-- 4. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl SET MsgText='No component was issued to this manufacturing order. The difference it shows is its entire receipt, not a cost difference – posting it would remove the whole manufactured value from stock and close the order. Please record the component issue first.',
       IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-04 21:30:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545829;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-04 21:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545829;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-04 21:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545829;
