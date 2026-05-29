-- 2022-11-16T19:08:49.699Z
UPDATE C_UOM_Trl SET IsTranslated='Y', Name='Gramm',Updated=TO_TIMESTAMP('2022-11-16 21:08:49.598','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_UOM_ID=540019
;

-- 2022-11-16T19:08:53.033Z
UPDATE C_UOM_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-16 21:08:52.927','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_UOM_ID=540019
;

-- 2022-11-16T19:09:00.440Z
UPDATE C_UOM_Trl SET IsTranslated='Y', Name='Gramm',Updated=TO_TIMESTAMP('2022-11-16 21:09:00.336','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_UOM_ID=540019
;

-- 2022-11-16T19:09:05.729Z
UPDATE C_UOM SET Name='Gramm',Updated=TO_TIMESTAMP('2022-11-16 21:09:05.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_UOM_ID=540019
;

-- 2022-11-16T19:09:05.772Z
UPDATE C_UOM_Trl trl SET Description=NULL, Name='Gramm', UOMSymbol='GRM'  WHERE C_UOM_ID=540019 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

