-- 2021-09-28T07:02:40.970Z
-- URL zum Konzept
UPDATE C_DocType SET IsDocNoControlled='Y',Updated=TO_TIMESTAMP('2021-09-28 09:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000040
;

-- 2021-09-28T07:05:34.595Z
-- URL zum Konzept
UPDATE C_DocType SET DocNoSequence_ID=545400,Updated=TO_TIMESTAMP('2021-09-28 09:05:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000040
;

-- 2021-09-29T08:08:07.533Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET Name='Distributionsauftrag',Updated=TO_TIMESTAMP('2021-09-29 10:08:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:08:08.697Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Distributionsauftrag',Updated=TO_TIMESTAMP('2021-09-29 10:08:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:08:11.825Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 10:08:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:08:18.160Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET Name='Distributionsauftrag',Updated=TO_TIMESTAMP('2021-09-29 10:08:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:08:20.383Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Distributionsauftrag',Updated=TO_TIMESTAMP('2021-09-29 10:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:08:20.477Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 10:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:08:29.702Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET Name='Distributionsauftrag',Updated=TO_TIMESTAMP('2021-09-29 10:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:08:31.339Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Distributionsauftrag',Updated=TO_TIMESTAMP('2021-09-29 10:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:08:31.436Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 10:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:11:14.947Z
-- URL zum Konzept
UPDATE C_DocType SET Name='Distributionsauftrag',Updated=TO_TIMESTAMP('2021-09-29 10:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000040
;

-- 2021-09-29T08:11:15.023Z
-- URL zum Konzept
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Distributionsauftrag', PrintName='Distribution Order'  WHERE C_DocType_ID=1000040 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-09-29T08:11:20.133Z
-- URL zum Konzept
UPDATE C_DocType SET PrintName='Distributionsauftrag',Updated=TO_TIMESTAMP('2021-09-29 10:11:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000040
;

-- 2021-09-29T08:11:20.152Z
-- URL zum Konzept
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Distributionsauftrag', PrintName='Distributionsauftrag'  WHERE C_DocType_ID=1000040 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-09-29T08:11:51.756Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET Name='Distribution Order',Updated=TO_TIMESTAMP('2021-09-29 10:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:11:53.258Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Distribution Order',Updated=TO_TIMESTAMP('2021-09-29 10:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:11:53.358Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 10:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:12:03.211Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET Name='Distribution Order',Updated=TO_TIMESTAMP('2021-09-29 10:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:12:04.397Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Distribution Order',Updated=TO_TIMESTAMP('2021-09-29 10:12:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=1000040
;

-- 2021-09-29T08:12:05.842Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 10:12:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=1000040
;


-- disable unsued action
-- 2021-10-04T08:49:32.888Z
-- URL zum Konzept
UPDATE AD_Table_Process SET IsActive='N',Updated=TO_TIMESTAMP('2021-10-04 10:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540452
;

