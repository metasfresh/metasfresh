-- One-line description of the text-lines launcher button, shown next to the action in the WebUI
-- (the client carries the related process's description to the client alongside its caption).
--
-- AD_Process owns its Name/Description/Help itself -- there is no AD_Element behind them and no
-- propagation function -- so the base row and the AD_Process_Trl rows are set here directly. The
-- AD_Process row and its per-language skeleton rows were created by
-- 5824500_sys_WEBUI_Order_DocTextLines_Launcher.sql with no description at all; this adds it.
--
-- Base text is German; every language gets it, then en_US is overridden with the English text.

UPDATE AD_Process
SET Description='Öffnet den Editor für die Freitextzeilen dieses Auftrags: Textzeilen zwischen den Auftragspositionen anlegen, ändern, verschieben und löschen.',
    Updated=TO_TIMESTAMP('2026-09-17 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585678
;

UPDATE AD_Process_Trl
SET Description='Öffnet den Editor für die Freitextzeilen dieses Auftrags: Textzeilen zwischen den Auftragspositionen anlegen, ändern, verschieben und löschen.',
    Updated=TO_TIMESTAMP('2026-09-17 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585678
;

UPDATE AD_Process_Trl
SET Description='Opens the editor for this order''s text lines: create, change, move and delete text lines between the order''s article lines.',
    Updated=TO_TIMESTAMP('2026-09-17 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585678 AND AD_Language='en_US'
;
