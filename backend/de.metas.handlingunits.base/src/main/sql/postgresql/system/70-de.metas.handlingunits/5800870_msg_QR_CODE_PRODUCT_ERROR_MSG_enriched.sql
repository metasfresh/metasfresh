-- me03#29347 follow-up (a) — enrich existing AD_Message
-- de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG (AD_Message_ID=545477)
-- with a {0} placeholder for the expected product name, so the picker sees what was supposed
-- to be scanned instead of the bare "Produkt passt nicht".

-- Update base text (German)
-- 2026-05-05 13:00
UPDATE AD_Message
SET MsgText='Produkt passt nicht. Erwartet: {0}',
    Updated=TO_TIMESTAMP('2026-05-05 13:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=0
WHERE AD_Message_ID=545477
;

-- Update de_DE / de_CH translations (same German text as base)
-- 2026-05-05 13:00
UPDATE AD_Message_Trl
SET MsgText='Produkt passt nicht. Erwartet: {0}',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-05 13:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=0
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545477
;

-- Update en_US translation
-- 2026-05-05 13:00
UPDATE AD_Message_Trl
SET MsgText='Product not matching. Expected: {0}',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-05 13:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=0
WHERE AD_Language='en_US' AND AD_Message_ID=545477
;

-- Defensive: catch any other AD_Message_Trl row still on the old base text (e.g. fr_CH and
-- any other language fanned out from the original 5737500 script that no one ever localised).
-- They should fall back to the new German base text rather than render "{0}"-less.
-- Leave IsTranslated='N' so the row is still flagged as not-actually-translated.
-- 2026-05-05 13:00
UPDATE AD_Message_Trl
SET MsgText='Produkt passt nicht. Erwartet: {0}',
    Updated=TO_TIMESTAMP('2026-05-05 13:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=0
WHERE AD_Message_ID=545477 AND MsgText='Produkt passt nicht'
;
