-- English (en_US) texts of the contract period check messages; en_US still carried the German text.
--   540264 Term_Error_Range_Without_Periods
--   540306 Term_Error_PeriodStartDate_After_TermStartDate
--   540307 Term_Error_PeriodEndDate_Before_TermEndDate

UPDATE AD_Message_Trl
SET MsgText      = 'The contract period {0,date} to {1,date} has no periods.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 18:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 540264
  AND AD_Language = 'en_US'
;

UPDATE AD_Message_Trl
SET MsgText      = 'Start date {0,date} of the first period of calendar {1} is after the start date of the contract period.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 18:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 540306
  AND AD_Language = 'en_US'
;

UPDATE AD_Message_Trl
SET MsgText      = 'End date {0,date} of the last period of calendar {1} is before the end date of the contract period.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 18:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 540307
  AND AD_Language = 'en_US'
;
