-- The refusal of a too-early release of a till card payment names the configured wait in seconds ({0}), as the card settlement's refusal does.

UPDATE AD_Message SET MsgText='Die Kartenzahlung kann erst nach {0} Sekunden freigegeben werden.', Updated=TO_TIMESTAMP('2026-11-11 08:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=546007
;

UPDATE AD_Message_Trl SET MsgText='Die Kartenzahlung kann erst nach {0} Sekunden freigegeben werden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-11 08:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=546007 AND AD_Language='de_DE'
;

UPDATE AD_Message_Trl SET MsgText='Die Kartenzahlung kann erst nach {0} Sekunden freigegeben werden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-11 08:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=546007 AND AD_Language='de_CH'
;

UPDATE AD_Message_Trl SET MsgText='The card payment can be released only after {0} seconds.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-11 08:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=546007 AND AD_Language='en_US'
;
