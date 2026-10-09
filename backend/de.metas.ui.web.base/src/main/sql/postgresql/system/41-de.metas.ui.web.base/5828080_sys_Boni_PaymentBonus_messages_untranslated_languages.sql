-- Boni: the untranslated languages (e.g. fr_CH) of the payment bonus messages still had the long German text of 5828030;
-- the message translation is used whatever its IsTranslated flag, so they get the current base text
-- 2026-10-06
UPDATE AD_Message_Trl trl
SET MsgText   = m.MsgText,
    Updated   = TO_TIMESTAMP('2026-10-06 17:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
FROM AD_Message m
WHERE m.AD_Message_ID = trl.AD_Message_ID
  AND trl.AD_Message_ID IN (545901, 545902, 545903, 545904, 545905)
  AND trl.IsTranslated = 'N'
;
