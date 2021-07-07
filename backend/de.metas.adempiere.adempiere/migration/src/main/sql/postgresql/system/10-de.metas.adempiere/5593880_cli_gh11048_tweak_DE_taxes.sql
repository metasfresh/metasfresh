-- 2021-06-21T10:54:28.061Z
-- URL zum Konzept
UPDATE C_Tax SET Name='Normaler Steuersatz 19% (DE) 2021',Updated=TO_TIMESTAMP('2021-06-21 12:54:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000042
;

-- 2021-06-21T10:54:28.070Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Normaler Steuersatz 19% (DE) 2021', TaxIndicator=NULL  WHERE C_Tax_ID=1000042 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:54:48.270Z
-- URL zum Konzept
UPDATE C_Tax SET Description='Normaler DE-Steuersatz nach dem Auslaufen der Corona-Hilfen',Updated=TO_TIMESTAMP('2021-06-21 12:54:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000042
;

-- 2021-06-21T10:54:48.277Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Normaler DE-Steuersatz nach dem Auslaufen der Corona-Hilfen', Name='Normaler Steuersatz 19% (DE) 2021', TaxIndicator=NULL  WHERE C_Tax_ID=1000042 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:54:56.938Z
-- URL zum Konzept
UPDATE C_Tax SET Name='Normaler Steuersatz 19% (DE) ab 2021',Updated=TO_TIMESTAMP('2021-06-21 12:54:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000042
;

-- 2021-06-21T10:54:56.945Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Normaler DE-Steuersatz nach dem Auslaufen der Corona-Hilfen', Name='Normaler Steuersatz 19% (DE) ab 2021', TaxIndicator=NULL  WHERE C_Tax_ID=1000042 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:55:31.076Z
-- URL zum Konzept
UPDATE C_Tax SET Name='Normaler Steuersatz 16% (DE) 2020',Updated=TO_TIMESTAMP('2021-06-21 12:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000039
;

-- 2021-06-21T10:55:31.086Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Normaler Steuersatz 16% (DE) 2020', TaxIndicator=NULL  WHERE C_Tax_ID=1000039 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:55:46.440Z
-- URL zum Konzept
UPDATE C_Tax SET Description='Normaler DE-Steuersatz während den Corona-Hilfen',Updated=TO_TIMESTAMP('2021-06-21 12:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000039
;

-- 2021-06-21T10:55:46.441Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Normaler DE-Steuersatz während den Corona-Hilfen', Name='Normaler Steuersatz 16% (DE) 2020', TaxIndicator=NULL  WHERE C_Tax_ID=1000039 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:56:27.185Z
-- URL zum Konzept
UPDATE C_Tax SET Description='Normaler DE-Steuersatz bis zu den Corona-Hilfen',Updated=TO_TIMESTAMP('2021-06-21 12:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000022
;

-- 2021-06-21T10:56:27.187Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Normaler DE-Steuersatz bis zu den Corona-Hilfen', Name='Normale MWSt 19% (DE)', TaxIndicator=NULL  WHERE C_Tax_ID=1000022 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:56:37.449Z
-- URL zum Konzept
UPDATE C_Tax SET Name='Normale MWSt 19% bis 2020',Updated=TO_TIMESTAMP('2021-06-21 12:56:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000022
;

-- 2021-06-21T10:56:37.454Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Normaler DE-Steuersatz bis zu den Corona-Hilfen', Name='Normale MWSt 19% bis 2020', TaxIndicator=NULL  WHERE C_Tax_ID=1000022 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:57:10.136Z
-- URL zum Konzept
UPDATE C_Tax SET Name='Normaler Steuersatz 19% (DE) bis 2020',Updated=TO_TIMESTAMP('2021-06-21 12:57:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000022
;

-- 2021-06-21T10:57:10.141Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Normaler DE-Steuersatz bis zu den Corona-Hilfen', Name='Normaler Steuersatz 19% (DE) bis 2020', TaxIndicator=NULL  WHERE C_Tax_ID=1000022 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:57:31.136Z
-- URL zum Konzept
UPDATE C_Tax SET Name='Reduzierter Steuersatz 5% (DE) 2020',Updated=TO_TIMESTAMP('2021-06-21 12:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000037
;

-- 2021-06-21T10:57:31.137Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierter Steuersatz 5% (DE) 2020', TaxIndicator=NULL  WHERE C_Tax_ID=1000037 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:57:49.935Z
-- URL zum Konzept
UPDATE C_Tax SET Description='Reduzierter DE-Steuersatz während den Corona-Hilfen',Updated=TO_TIMESTAMP('2021-06-21 12:57:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000037
;

-- 2021-06-21T10:57:49.940Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Reduzierter DE-Steuersatz während den Corona-Hilfen', Name='Reduzierter Steuersatz 5% (DE) 2020', TaxIndicator=NULL  WHERE C_Tax_ID=1000037 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:58:17.789Z
-- URL zum Konzept
UPDATE C_Tax SET Description='Reduzierter DE-Steuersatz bis zu den Corona-Hilfen',Updated=TO_TIMESTAMP('2021-06-21 12:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000023
;

-- 2021-06-21T10:58:17.795Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Reduzierter DE-Steuersatz bis zu den Corona-Hilfen', Name='Reduzierter Steuersatz 7% (Deutschland)', TaxIndicator=NULL  WHERE C_Tax_ID=1000023 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:58:27.316Z
-- URL zum Konzept
UPDATE C_Tax SET Name='Reduzierter Steuersatz 7% (DE) bis 2020',Updated=TO_TIMESTAMP('2021-06-21 12:58:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000023
;

-- 2021-06-21T10:58:27.321Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Reduzierter DE-Steuersatz bis zu den Corona-Hilfen', Name='Reduzierter Steuersatz 7% (DE) bis 2020', TaxIndicator=NULL  WHERE C_Tax_ID=1000023 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:58:48.588Z
-- URL zum Konzept
UPDATE C_Tax SET Name='Reduzierter Steuersatz 7% (DE) ab 2021',Updated=TO_TIMESTAMP('2021-06-21 12:58:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000041
;

-- 2021-06-21T10:58:48.597Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description=NULL, Name='Reduzierter Steuersatz 7% (DE) ab 2021', TaxIndicator=NULL  WHERE C_Tax_ID=1000041 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-21T10:58:56.305Z
-- URL zum Konzept
UPDATE C_Tax SET Description='Reduzierter DE-Steuersatz nach dem Auslaufen der Corona-Hilfen',Updated=TO_TIMESTAMP('2021-06-21 12:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Tax_ID=1000041
;

-- 2021-06-21T10:58:56.316Z
-- URL zum Konzept
UPDATE C_Tax_Trl trl SET Description='Reduzierter DE-Steuersatz nach dem Auslaufen der Corona-Hilfen', Name='Reduzierter Steuersatz 7% (DE) ab 2021', TaxIndicator=NULL  WHERE C_Tax_ID=1000041 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;
