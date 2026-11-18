-- Mobile MFG raw-materials issue — refusal message when the issued qty exceeds the BOM line's
-- remaining open demand (the over-issue cap). User-validation error shown to the operator.
-- IDs allocated from idserver.metas.de on 2026-11-18:
--   AD_Message_ID 546014

-- ==========================================================================
-- AD_Message: Cannot Issue More Than Remaining Demand
-- ==========================================================================

-- Base language (German)
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, Value, MsgType, MsgText, ErrorCode, EntityType, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES (546014 /*From ID Server*/, 0, 0, 'de.metas.manufacturing.job.service.CannotIssueMoreThanRemainingDemand', 'E', 'Es kann nicht mehr als die offene Restmenge der Position ausgegeben werden', 'ERR_CANNOT_ISSUE_MORE_THAN_REMAINING', 'D', 'Y', TO_TIMESTAMP('2026-11-18 13:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-11-18 13:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Seed translations for all active system languages (copy base text, mark as untranslated)
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, IsTranslated, IsActive, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, 'N', t.IsActive, t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546014 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

-- German translations (de_DE and de_CH) — same as base language, mark as translated
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 13:30:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=546014 /*From ID Server*/;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 13:30:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=546014 /*From ID Server*/;

-- English translation (en_US)
UPDATE AD_Message_Trl SET MsgText='Cannot issue more than the line''s remaining open demand', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 13:30:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546014 /*From ID Server*/;
