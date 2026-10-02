-- Kosten Neubewertung (cost revaluation), forward-only: a revaluation no longer refuses because of other revaluations.

-- M_CostRevaluation.EarlierRevaluationNotPosted: the "earlier revaluation not posted yet" refusal is gone, so its message is no longer used
DELETE FROM AD_Message_Trl WHERE AD_Message_ID = 545881;
DELETE FROM AD_Message WHERE AD_Message_ID = 545881;

-- M_CostRevaluation.DeleteLinesFirstError: {0} = the translated names of the header fields the user tried to change, comma-separated
UPDATE AD_Message SET MsgText='Folgende Kopfdaten können nur geändert werden, wenn die Kosten Neubewertung keine Zeilen hat: {0}. Bitte zuerst die Zeilen löschen.', Updated=TO_TIMESTAMP('2026-10-01 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545876;
UPDATE AD_Message_Trl SET MsgText='Folgende Kopfdaten können nur geändert werden, wenn die Kosten Neubewertung keine Zeilen hat: {0}. Bitte zuerst die Zeilen löschen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 12:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545876;
UPDATE AD_Message_Trl SET MsgText='Folgende Kopfdaten können nur geändert werden, wenn die Kosten Neubewertung keine Zeilen hat: {0}. Bitte zuerst die Zeilen löschen.', Updated=TO_TIMESTAMP('2026-10-01 12:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545876;
UPDATE AD_Message_Trl SET MsgText='The following header fields can only be changed while the cost revaluation has no lines: {0}. Please delete the lines first.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 12:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545876;
