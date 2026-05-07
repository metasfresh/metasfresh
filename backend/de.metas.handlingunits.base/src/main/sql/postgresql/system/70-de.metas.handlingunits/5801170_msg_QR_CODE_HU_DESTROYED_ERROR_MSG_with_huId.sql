-- me03#29347 follow-up — include the M_HU_ID (also the user-facing search key) in the
-- destroyed-HU error message so the picker can hand the number to a supervisor / look it up
-- in the WebUI HU window without further detective work.

-- Update base text (German)
-- 2026-05-06 14:00
UPDATE AD_Message
SET MsgText='HU {0} wurde bereits zerstört.',
    Updated=TO_TIMESTAMP('2026-05-06 14:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=0
WHERE AD_Message_ID=545677
;

-- Update de_DE / de_CH translations (same German text as base)
-- 2026-05-06 14:00
UPDATE AD_Message_Trl
SET MsgText='HU {0} wurde bereits zerstört.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-06 14:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=0
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545677
;

-- Update en_US translation
-- 2026-05-06 14:00
UPDATE AD_Message_Trl
SET MsgText='HU {0} was already destroyed.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-06 14:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=0
WHERE AD_Language='en_US' AND AD_Message_ID=545677
;

-- Defensive: catch any other AD_Message_Trl row still on the prior placeholder-less text
-- (e.g. fr_CH and any other language fanned out from 5800850 that was never localised).
-- Leave IsTranslated='N' so the row is still flagged as not-actually-translated.
-- 2026-05-06 14:00
UPDATE AD_Message_Trl
SET MsgText='HU {0} wurde bereits zerstört.',
    Updated=TO_TIMESTAMP('2026-05-06 14:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=0
WHERE AD_Message_ID=545677 AND MsgText='HU wurde bereits zerstört.'
;
