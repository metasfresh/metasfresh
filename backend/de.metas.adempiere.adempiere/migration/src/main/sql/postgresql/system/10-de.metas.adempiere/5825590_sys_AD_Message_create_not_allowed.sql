-- Refusal message shown when a role is not allowed to create new records in a table.
-- {0} is the role name.
-- IDs allocated from idserver.metas.de on 2026-09-22:
--   AD_MigrationScript 5825590
--   AD_Message         545850 (ERR_Role_CreateNewRecordsNotAllowed)

-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545850 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-22 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Rolle „{0}“ darf hier keine neuen Datensätze anlegen.','E',TO_TIMESTAMP('2026-09-22 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ERR_Role_CreateNewRecordsNotAllowed');

-- 2. short ErrorCode
UPDATE AD_Message SET ErrorCode='Role_CreateNewRecordsNotAllowed', Updated=TO_TIMESTAMP('2026-09-22 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545850;

-- 3. seed AD_Message_Trl for all active system languages with the base (DE) text
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545850
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

-- 4. en_US override
UPDATE AD_Message_Trl SET MsgText='Role "{0}" is not allowed to create new records here.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-22 09:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545850;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the German base)
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-22 09:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545850;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-22 09:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545850;
