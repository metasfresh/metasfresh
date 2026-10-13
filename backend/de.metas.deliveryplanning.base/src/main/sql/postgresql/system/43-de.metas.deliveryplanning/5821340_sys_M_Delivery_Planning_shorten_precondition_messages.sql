-- Shorten the nine delivery-planning precondition messages. They render in the action's
-- disabled-reason tooltip, where the available width truncates anything long -- a truncated reason
-- reads as no reason at all, and the planner cannot tell which rule refused the action.
-- Only MsgText changes; the AD_Message keys and ErrorCodes are untouched, so the cucumber
-- assertions (which match on the key, never on the text) are unaffected.
--
-- IDs allocated from idserver.metas.de on 2026-08-31:
--   AD_MigrationScript 5821340 (this file)

-- ===========================================================================================
-- 1) German base text (AD_Message.MsgText, base language de_DE)
-- ===========================================================================================
UPDATE AD_Message SET MsgText='Nicht eindeutig: {0}.',                            Updated=TO_TIMESTAMP('2026-08-31 20:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545796;
UPDATE AD_Message SET MsgText='Geschlossen: {0}.',                                Updated=TO_TIMESTAMP('2026-08-31 20:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545797;
UPDATE AD_Message SET MsgText='Bereits zugeordnet: {0}.',                         Updated=TO_TIMESTAMP('2026-08-31 20:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545798;
UPDATE AD_Message SET MsgText='Lieferanweisung fertiggestellt: {0}.',             Updated=TO_TIMESTAMP('2026-08-31 20:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545807;
UPDATE AD_Message SET MsgText='Lieferanweisung ist kein Entwurf.',                Updated=TO_TIMESTAMP('2026-08-31 20:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545808;
UPDATE AD_Message SET MsgText='Nicht zugeordnet: {0}.',                           Updated=TO_TIMESTAMP('2026-08-31 20:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545809;
UPDATE AD_Message SET MsgText='Geschlossene Lieferplanung zugeordnet: {0}.',      Updated=TO_TIMESTAMP('2026-08-31 20:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545810;
UPDATE AD_Message SET MsgText='Keine Lieferplanung zugeordnet.',                  Updated=TO_TIMESTAMP('2026-08-31 20:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545811;
UPDATE AD_Message SET MsgText='Bereits zugeordnet, bitte verschieben: {0}.',      Updated=TO_TIMESTAMP('2026-08-31 20:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545815;

-- ===========================================================================================
-- 2) German translation rows.
-- ===========================================================================================
UPDATE AD_Message_Trl trl
   SET MsgText   = m.MsgText,
       Updated   = TO_TIMESTAMP('2026-08-31 20:00:10','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
  FROM AD_Message m
 WHERE m.AD_Message_ID = trl.AD_Message_ID
   AND trl.AD_Language IN ('de_DE', 'de_CH')
   AND trl.AD_Message_ID IN (545796, 545797, 545798, 545807, 545808, 545809, 545810, 545811, 545815)
;

-- ===========================================================================================
-- 3) English translation rows
-- ===========================================================================================
UPDATE AD_Message_Trl SET MsgText='Not unique: {0}.',                             Updated=TO_TIMESTAMP('2026-08-31 20:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545796;
UPDATE AD_Message_Trl SET MsgText='Closed: {0}.',                                 Updated=TO_TIMESTAMP('2026-08-31 20:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545797;
UPDATE AD_Message_Trl SET MsgText='Already allocated: {0}.',                      Updated=TO_TIMESTAMP('2026-08-31 20:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545798;
UPDATE AD_Message_Trl SET MsgText='Delivery instruction completed: {0}.',         Updated=TO_TIMESTAMP('2026-08-31 20:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545807;
UPDATE AD_Message_Trl SET MsgText='Delivery instruction is not a draft.',         Updated=TO_TIMESTAMP('2026-08-31 20:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545808;
UPDATE AD_Message_Trl SET MsgText='Not allocated: {0}.',                          Updated=TO_TIMESTAMP('2026-08-31 20:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545809;
UPDATE AD_Message_Trl SET MsgText='Closed delivery planning allocated: {0}.',     Updated=TO_TIMESTAMP('2026-08-31 20:00:17','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545810;
UPDATE AD_Message_Trl SET MsgText='No delivery planning allocated.',              Updated=TO_TIMESTAMP('2026-08-31 20:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545811;
UPDATE AD_Message_Trl SET MsgText='Already allocated, please move: {0}.',         Updated=TO_TIMESTAMP('2026-08-31 20:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545815;

-- ===========================================================================================
-- 4) fr_CH per the convention stated once in
--    5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql: the en_US text,
--    IsTranslated='N'. Runs after section 3, so it copies the shortened English text.
-- ===========================================================================================
UPDATE AD_Message_Trl trl
   SET MsgText      = en.MsgText,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-08-31 20:00:21','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Message_Trl en
 WHERE en.AD_Message_ID = trl.AD_Message_ID
   AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH'
   AND trl.AD_Message_ID IN (545796, 545797, 545798, 545807, 545808, 545809, 545810, 545811, 545815)
;
