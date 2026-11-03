-- CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported: kept with a neutral text (human decision). Thrown only by
-- CostingMethodHandler#recalculateCostDetailAmountAndUpdateCurrentCost, which has no caller since the cost revaluation no longer
-- replays cost details.
UPDATE AD_Message SET MsgText='Eine Kostenneubewertung kann nicht neu berechnet werden. Bitte die Neuberechnung nach dem Datum der Neubewertung starten.', Updated=TO_TIMESTAMP('2026-10-01 18:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545879;
UPDATE AD_Message_Trl SET MsgText='Eine Kostenneubewertung kann nicht neu berechnet werden. Bitte die Neuberechnung nach dem Datum der Neubewertung starten.', Updated=TO_TIMESTAMP('2026-10-01 18:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545879 AND AD_Language<>'en_US';
UPDATE AD_Message_Trl SET MsgText='A cost revaluation cannot be recalculated. Restart the recompute after the revaluation date.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 18:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545879 AND AD_Language='en_US';
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 18:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545879 AND AD_Language IN ('de_DE','de_CH');
