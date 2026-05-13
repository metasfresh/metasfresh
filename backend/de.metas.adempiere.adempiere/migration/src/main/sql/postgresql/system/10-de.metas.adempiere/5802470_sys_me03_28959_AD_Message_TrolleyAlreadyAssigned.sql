-- 2026-05-13T10:00:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545685 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-13 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','This trolley is already assigned to another user. Please ask them to release it first.','E',TO_TIMESTAMP('2026-05-13 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'WFRestAPI_TrolleyAlreadyAssigned')
;

-- 2026-05-13T10:00:01.000Z
UPDATE AD_Message SET ErrorCode='TrolleyAlreadyAssigned', Updated=TO_TIMESTAMP('2026-05-13 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545685 /*From ID Server*/
;

-- 2026-05-13T10:00:02.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545685 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-05-13T10:00:03.000Z
UPDATE AD_Message_Trl SET MsgText='Dieser Wagen ist bereits einem anderen Nutzer zugewiesen. Bitte den Nutzer um Freigabe.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545685 /*From ID Server*/
;

-- 2026-05-13T10:00:04.000Z
UPDATE AD_Message_Trl SET MsgText='Dieser Wagen ist bereits einem anderen Nutzer zugewiesen. Bitte den Nutzer um Freigabe.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545685 /*From ID Server*/
;

-- 2026-05-13T10:01:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545686 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-13 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','This trolley is already assigned to {0}. Please ask them to release it first.','E',TO_TIMESTAMP('2026-05-13 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'WFRestAPI_TrolleyAlreadyAssignedTo')
;

-- 2026-05-13T10:01:01.000Z
UPDATE AD_Message SET ErrorCode='TrolleyAlreadyAssignedTo', Updated=TO_TIMESTAMP('2026-05-13 10:01:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545686 /*From ID Server*/
;

-- 2026-05-13T10:01:02.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545686 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-05-13T10:01:03.000Z
UPDATE AD_Message_Trl SET MsgText='Dieser Wagen ist bereits {0} zugewiesen. Bitte den Nutzer um Freigabe.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 10:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545686 /*From ID Server*/
;

-- 2026-05-13T10:01:04.000Z
UPDATE AD_Message_Trl SET MsgText='Dieser Wagen ist bereits {0} zugewiesen. Bitte den Nutzer um Freigabe.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 10:01:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545686 /*From ID Server*/
;
