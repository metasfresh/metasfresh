-- Empty HU write-off — inventory description used when an emptied HU is booked to zero
-- IDs allocated from idserver.metas.de on 2026-09-07:
--   AD_Message_ID 545830

-- ==========================================================================
-- AD_Message: Emptied HU Inventory Description
-- ==========================================================================

-- Base language (German)
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, Value, MsgType, MsgText, ErrorCode, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES (545830 /*From ID Server*/, 0, 0, 'de.metas.handlingunits.pporder.EmptiedHUInventoryDescription', 'I', 'Bei Materialzuteilung zu {0} geleert', NULL, 'Y', TO_TIMESTAMP('2026-09-07 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-07 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Seed translations for all active system languages (copy base text, mark as untranslated)
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, IsTranslated, IsActive, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, 'N', t.IsActive, t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545830 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

-- German translations (de_DE and de_CH) — same as base language, mark as translated
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545830 /*From ID Server*/;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545830 /*From ID Server*/;

-- English translation (en_US)
UPDATE AD_Message_Trl SET MsgText='Emptied during production issue of {0}', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545830 /*From ID Server*/;
