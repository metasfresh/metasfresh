-- Languages without their own translation of the too-early release refusal show the base text, which now names the wait in seconds ({0}).

UPDATE AD_Message_Trl SET MsgText='Die Kartenzahlung kann erst nach {0} Sekunden freigegeben werden.', Updated=TO_TIMESTAMP('2026-11-11 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=546007 AND IsTranslated='N'
;
