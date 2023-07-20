-- 2022-11-16T19:07:29.679Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktionsstückliste Position', PrintName='Produktionsstückliste Position',Updated=TO_TIMESTAMP('2022-11-16 21:07:29.573','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53275 AND AD_Language='de_CH'
;

-- 2022-11-16T19:07:29.827Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53275,'de_CH') 
;

-- 2022-11-16T19:07:39.878Z
UPDATE AD_Element_Trl SET Name='Produktionsstückliste Position', PrintName='Produktionsstückliste Position',Updated=TO_TIMESTAMP('2022-11-16 21:07:39.774','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53275 AND AD_Language='de_DE'
;

-- 2022-11-16T19:07:39.920Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53275,'de_DE') 
;

-- 2022-11-16T19:07:40Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53275,'de_DE') 
;

-- 2022-11-16T19:07:40.038Z
UPDATE AD_Column SET ColumnName='PP_Order_BOMLine_ID', Name='Produktionsstückliste Position', Description=NULL, Help=NULL WHERE AD_Element_ID=53275
;

-- 2022-11-16T19:07:40.079Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_BOMLine_ID', Name='Produktionsstückliste Position', Description=NULL, Help=NULL, AD_Element_ID=53275 WHERE UPPER(ColumnName)='PP_ORDER_BOMLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-16T19:07:40.132Z
UPDATE AD_Process_Para SET ColumnName='PP_Order_BOMLine_ID', Name='Produktionsstückliste Position', Description=NULL, Help=NULL WHERE AD_Element_ID=53275 AND IsCentrallyMaintained='Y'
;

-- 2022-11-16T19:07:40.169Z
UPDATE AD_Field SET Name='Produktionsstückliste Position', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53275) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53275)
;

-- 2022-11-16T19:07:40.251Z
UPDATE AD_PrintFormatItem pi SET PrintName='Produktionsstückliste Position', Name='Produktionsstückliste Position' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53275)
;

-- 2022-11-16T19:07:40.290Z
UPDATE AD_Tab SET Name='Produktionsstückliste Position', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 53275
;

-- 2022-11-16T19:07:40.328Z
UPDATE AD_WINDOW SET Name='Produktionsstückliste Position', Description=NULL, Help=NULL WHERE AD_Element_ID = 53275
;

-- 2022-11-16T19:07:40.365Z
UPDATE AD_Menu SET   Name = 'Produktionsstückliste Position', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 53275
;

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

-- 2022-11-16T19:11:30.514Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Produktionsstückliste Position',Updated=TO_TIMESTAMP('2022-11-16 21:11:30.408','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=53025
;

-- 2022-11-16T19:11:41.303Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Produktionsstückliste Position',Updated=TO_TIMESTAMP('2022-11-16 21:11:41.198','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=53025
;

