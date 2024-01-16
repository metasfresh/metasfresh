-- 2022-06-17T10:12:27.994Z
UPDATE WEBUI_KPI SET Name='Gesamtsumme der Aufträge für heute.',Updated=TO_TIMESTAMP('2022-06-17 13:12:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540036
;

-- 2022-06-17T10:12:32.926Z
UPDATE WEBUI_KPI SET Name='Gesamtsumme der Aufträge für diese Woche.',Updated=TO_TIMESTAMP('2022-06-17 13:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540037
;

-- 2022-06-17T10:12:38.601Z
UPDATE WEBUI_KPI SET Name='Gesamtsumme der Bestellungen für heute.',Updated=TO_TIMESTAMP('2022-06-17 13:12:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540038
;

-- 2022-06-17T10:12:40.944Z
UPDATE WEBUI_KPI SET Name='Gesamtsumme der Bestellungen für diese Woche.',Updated=TO_TIMESTAMP('2022-06-17 13:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540040
;

-- 2022-06-17T10:21:54.922Z
UPDATE WEBUI_DashboardItem SET Name='Gesamtsumme der Aufträge für heute.',Updated=TO_TIMESTAMP('2022-06-17 13:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_DashboardItem_ID=540034
;

-- 2022-06-17T10:21:54.928Z
UPDATE WEBUI_DashboardItem_Trl trl SET Name='Gesamtsumme der Aufträge für heute.'  WHERE WEBUI_DashboardItem_ID=540034 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-06-17T10:22:03.934Z
UPDATE WEBUI_DashboardItem SET Name='Gesamtsumme der Aufträge für diese Woche.',Updated=TO_TIMESTAMP('2022-06-17 13:22:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_DashboardItem_ID=540035
;

-- 2022-06-17T10:22:03.936Z
UPDATE WEBUI_DashboardItem_Trl trl SET Name='Gesamtsumme der Aufträge für diese Woche.'  WHERE WEBUI_DashboardItem_ID=540035 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-06-17T10:22:14.229Z
UPDATE WEBUI_DashboardItem SET Name='Gesamtsumme der Bestellungen für heute.',Updated=TO_TIMESTAMP('2022-06-17 13:22:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_DashboardItem_ID=540032
;

-- 2022-06-17T10:22:14.231Z
UPDATE WEBUI_DashboardItem_Trl trl SET Name='Gesamtsumme der Bestellungen für heute.'  WHERE WEBUI_DashboardItem_ID=540032 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-06-17T10:22:16.628Z
UPDATE WEBUI_DashboardItem SET Name='Gesamtsumme der Bestellungen für diese Woche.',Updated=TO_TIMESTAMP('2022-06-17 13:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_DashboardItem_ID=540033
;

-- 2022-06-17T10:22:16.630Z
UPDATE WEBUI_DashboardItem_Trl trl SET Name='Gesamtsumme der Bestellungen für diese Woche.'  WHERE WEBUI_DashboardItem_ID=540033 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-06-17T10:22:36.835Z
UPDATE WEBUI_DashboardItem_Trl SET Name='Grand total of sales orders for today.',Updated=TO_TIMESTAMP('2022-06-17 13:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND WEBUI_DashboardItem_ID=540034
;

-- 2022-06-17T10:22:48.560Z
UPDATE WEBUI_DashboardItem_Trl SET Name='Grand total of sales orders for this week.',Updated=TO_TIMESTAMP('2022-06-17 13:22:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND WEBUI_DashboardItem_ID=540035
;

-- 2022-06-17T10:22:57.962Z
UPDATE WEBUI_DashboardItem_Trl SET Name='Gesamtsumme der Bestellungen für heute.',Updated=TO_TIMESTAMP('2022-06-17 13:22:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND WEBUI_DashboardItem_ID=540032
;

-- 2022-06-17T10:23:08.974Z
UPDATE WEBUI_DashboardItem_Trl SET Name='Grand total of purchase orders for this week.',Updated=TO_TIMESTAMP('2022-06-17 13:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND WEBUI_DashboardItem_ID=540033
;
