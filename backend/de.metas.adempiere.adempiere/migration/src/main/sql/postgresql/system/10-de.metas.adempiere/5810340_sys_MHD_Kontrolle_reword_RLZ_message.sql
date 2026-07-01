-- Reword the shelf-life picking warning (AD_Message RLZ_TooShort, AD_Message_ID=545767).
-- Old text "RLZ zu kurz!" was a bare statement: "RLZ" is a customer-internal acronym (not
-- established metasfresh vocabulary) and it gave no hint what the dialog's Ja/Nein buttons do.
-- New text spells out "Restlaufzeit" and is phrased as a question, so the green Ja (pick anyway)
-- and red Nein (cancel) buttons become self-explanatory.
-- Value and ErrorCode stay 'RLZ_TooShort' (the mobile frontend matches on the error code) and
-- MsgType stays 'E' — only the displayed text changes.
-- German in the base column + de_DE/de_CH/fr_CH _Trl; English override on en_US.

-- 2026-07-01T10:10:00.000Z
UPDATE AD_Message
SET MsgText='Restlaufzeit unterschreitet die Vorgabe. Trotzdem kommissionieren?',
    Updated=TO_TIMESTAMP('2026-07-01 10:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545767
;

-- 2026-07-01T10:10:01.000Z
UPDATE AD_Message_Trl
SET MsgText='Restlaufzeit unterschreitet die Vorgabe. Trotzdem kommissionieren?', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-01 10:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545767 AND AD_Language='de_DE'
;

-- 2026-07-01T10:10:02.000Z
UPDATE AD_Message_Trl
SET MsgText='Restlaufzeit unterschreitet die Vorgabe. Trotzdem kommissionieren?', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-01 10:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545767 AND AD_Language='de_CH'
;

-- 2026-07-01T10:10:03.000Z
-- fr_CH has no French translation (IsTranslated='N'); mirror the German base as the fallback text.
UPDATE AD_Message_Trl
SET MsgText='Restlaufzeit unterschreitet die Vorgabe. Trotzdem kommissionieren?', IsTranslated='N',
    Updated=TO_TIMESTAMP('2026-07-01 10:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545767 AND AD_Language='fr_CH'
;

-- 2026-07-01T10:10:04.000Z
UPDATE AD_Message_Trl
SET MsgText='Remaining shelf life is below the requirement. Pick anyway?', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-01 10:10:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545767 AND AD_Language='en_US'
;
