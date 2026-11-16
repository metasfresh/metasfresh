-- AD_Message base text is in German (DE).
-- Rationale: most users are German-speakers. If a translation is missing or broken, the
-- fallback (AD_Message.MsgText) should show German to German users — better to show DE
-- to EN users (mostly developers) than EN to DE users.
-- en_US translation is provided via the AD_Message_Trl override below.

-- 2026-05-15T10:01:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545686 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-15 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dieser Wagen ist bereits {0} zugewiesen. Bitte den Nutzer um Freigabe.','E',TO_TIMESTAMP('2026-05-15 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'WFRestAPI_TrolleyAlreadyAssignedTo')
;

-- 2026-05-15T10:01:01.000Z
UPDATE AD_Message SET ErrorCode='TrolleyAlreadyAssignedTo', Updated=TO_TIMESTAMP('2026-05-15 10:01:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545686 /*From ID Server*/
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-05-15T10:01:02.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545686 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-05-15T10:01:03.000Z
UPDATE AD_Message_Trl SET MsgText='This trolley is already assigned to {0}. Please ask them to release it first.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-15 10:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545686 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
-- 2026-05-15T10:01:04.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-15 10:01:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545686 /*From ID Server*/
;

-- 2026-05-15T10:01:05.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-15 10:01:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545686 /*From ID Server*/
;
