-- Boni: the untranslated languages (IsTranslated='N', e.g. fr_CH) of the two messages for a payment bonus that a payment cannot book get the new base text of 5829190
-- AD_MigrationScript ID allocated from the central ID server on 2026-10-12

UPDATE AD_Message_Trl trl
SET MsgText=m.MsgText,
    Updated=TO_TIMESTAMP('2026-10-12 16:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM AD_Message m
WHERE m.AD_Message_ID = trl.AD_Message_ID
  AND trl.AD_Message_ID IN (545927, 545928)
  AND trl.IsTranslated = 'N'
;
