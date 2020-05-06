-- 16.09.2016 17:39
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Qualitätsabzug Wareneingang',Updated=TO_TIMESTAMP('2016-09-16 17:39:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543962
;

-- 16.09.2016 17:39
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543962
;

-- 16.09.2016 17:40
-- URL zum Konzept
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=543963
;

-- 16.09.2016 17:40
-- URL zum Konzept
DELETE FROM AD_Message WHERE AD_Message_ID=543963
;

