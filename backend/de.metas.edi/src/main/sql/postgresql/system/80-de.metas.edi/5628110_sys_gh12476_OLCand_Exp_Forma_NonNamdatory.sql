-- make DeliveryRule and DeliveryViaRule non-mandatory on the export-format

-- 2022-02-28T13:23:59.316Z
-- URL zum Konzept
UPDATE EXP_FormatLine SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-02-28 14:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=549064
;

-- 2022-02-28T13:29:47.574Z
-- URL zum Konzept
UPDATE EXP_FormatLine SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-02-28 14:29:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=549063
;

