-- CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported: its text described the refusal of a revaluation dated before another one, which no longer exists.
DELETE FROM AD_Message_Trl WHERE AD_Message_ID = 545879;
DELETE FROM AD_Message WHERE AD_Message_ID = 545879;
