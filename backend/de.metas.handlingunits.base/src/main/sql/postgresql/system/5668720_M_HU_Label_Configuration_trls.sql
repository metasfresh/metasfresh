-- Element: M_HU_Label_Config_ID
-- 2022-12-15T11:48:50.013Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='HU-Labels Konfiguration', PrintName='HU-Labels Konfiguration',Updated=TO_TIMESTAMP('2022-12-15 13:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581844 AND AD_Language='de_CH'
;

-- 2022-12-15T11:48:50.056Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581844,'de_CH') 
;

-- Element: M_HU_Label_Config_ID
-- 2022-12-15T11:48:55.613Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='HU-Labels Konfiguration', PrintName='HU-Labels Konfiguration',Updated=TO_TIMESTAMP('2022-12-15 13:48:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581844 AND AD_Language='de_DE'
;

-- 2022-12-15T11:48:55.615Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581844,'de_DE') 
;

-- 2022-12-15T11:48:55.621Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581844,'de_DE') 
;

-- Element: M_HU_Label_Config_ID
-- 2022-12-15T11:48:57.873Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-15 13:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581844 AND AD_Language='en_US'
;

-- 2022-12-15T11:48:57.877Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581844,'en_US') 
;

-- Element: HU_SourceDocType
-- 2022-12-15T11:49:22.798Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Quellbelegart', PrintName='Quellbelegart',Updated=TO_TIMESTAMP('2022-12-15 13:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581846 AND AD_Language='de_CH'
;

-- 2022-12-15T11:49:22.801Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581846,'de_CH') 
;

-- Element: HU_SourceDocType
-- 2022-12-15T11:49:25.930Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Quellbelegart', PrintName='Quellbelegart',Updated=TO_TIMESTAMP('2022-12-15 13:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581846 AND AD_Language='de_DE'
;

-- 2022-12-15T11:49:25.932Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581846,'de_DE') 
;

-- 2022-12-15T11:49:25.950Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581846,'de_DE') 
;

-- Element: HU_SourceDocType
-- 2022-12-15T11:49:28.088Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-15 13:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581846 AND AD_Language='en_US'
;

-- 2022-12-15T11:49:28.090Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581846,'en_US') 
;

-- Reference: HU_SourceDocType (M_HU_Label_Config)
-- Value: MR
-- ValueName: MaterialReceipt
-- 2022-12-15T11:49:59.397Z
UPDATE AD_Ref_List SET Name='Wareneingang',Updated=TO_TIMESTAMP('2022-12-15 13:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543359
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> MR_MaterialReceipt
-- 2022-12-15T11:50:03.979Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Wareneingang',Updated=TO_TIMESTAMP('2022-12-15 13:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543359
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> MR_MaterialReceipt
-- 2022-12-15T11:50:05.958Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Wareneingang',Updated=TO_TIMESTAMP('2022-12-15 13:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543359
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> MR_MaterialReceipt
-- 2022-12-15T11:50:07.067Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-15 13:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543359
;

-- Reference: HU_SourceDocType (M_HU_Label_Config)
-- Value: MO
-- ValueName: Manufacturing
-- 2022-12-15T11:50:18.841Z
UPDATE AD_Ref_List SET Name='Produktion',Updated=TO_TIMESTAMP('2022-12-15 13:50:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543358
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> MO_Manufacturing
-- 2022-12-15T11:50:22.632Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Produktion',Updated=TO_TIMESTAMP('2022-12-15 13:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543358
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> MO_Manufacturing
-- 2022-12-15T11:50:31.835Z
UPDATE AD_Ref_List_Trl SET Name='Manufacturing',Updated=TO_TIMESTAMP('2022-12-15 13:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543358
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> MO_Manufacturing
-- 2022-12-15T11:50:36.435Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Produktion',Updated=TO_TIMESTAMP('2022-12-15 13:50:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543358
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> MO_Manufacturing
-- 2022-12-15T11:50:39.676Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Produktion',Updated=TO_TIMESTAMP('2022-12-15 13:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543358
;

-- Reference: HU_SourceDocType (M_HU_Label_Config)
-- Value: PI
-- ValueName: Picking
-- 2022-12-15T11:50:52.729Z
UPDATE AD_Ref_List SET Name='Kommissionierung',Updated=TO_TIMESTAMP('2022-12-15 13:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543360
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> PI_Picking
-- 2022-12-15T11:50:58.052Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Kommissionierung',Updated=TO_TIMESTAMP('2022-12-15 13:50:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543360
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> PI_Picking
-- 2022-12-15T11:51:00.167Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Kommissionierung',Updated=TO_TIMESTAMP('2022-12-15 13:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543360
;

-- Reference Item: HU_SourceDocType (M_HU_Label_Config) -> PI_Picking
-- 2022-12-15T11:51:01.263Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-15 13:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543360
;

-- Element: LabelReport_Process_ID
-- 2022-12-15T11:51:41.885Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Label Druckformat', PrintName='Label Druckformat',Updated=TO_TIMESTAMP('2022-12-15 13:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581845 AND AD_Language='de_CH'
;

-- 2022-12-15T11:51:41.888Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581845,'de_CH') 
;

-- Element: LabelReport_Process_ID
-- 2022-12-15T11:51:44.431Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Label Druckformat', PrintName='Label Druckformat',Updated=TO_TIMESTAMP('2022-12-15 13:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581845 AND AD_Language='de_DE'
;

-- 2022-12-15T11:51:44.434Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581845,'de_DE') 
;

-- 2022-12-15T11:51:44.437Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581845,'de_DE') 
;

-- Element: LabelReport_Process_ID
-- 2022-12-15T11:51:50.812Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-15 13:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581845 AND AD_Language='en_US'
;

-- 2022-12-15T11:51:50.815Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581845,'en_US') 
;

-- Element: IsAutoPrint
-- 2022-12-15T11:52:32.014Z
UPDATE AD_Element_Trl SET Description='Print immediately when the HU becomes Active', IsTranslated='Y', Name='Print immediately', PrintName='Print immediately',Updated=TO_TIMESTAMP('2022-12-15 13:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581849 AND AD_Language='en_US'
;

-- 2022-12-15T11:52:32.017Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581849,'en_US') 
;

-- Element: IsAutoPrint
-- 2022-12-15T11:52:43.474Z
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Sofort drucken', PrintName='Sofort drucken',Updated=TO_TIMESTAMP('2022-12-15 13:52:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581849 AND AD_Language='de_CH'
;

-- 2022-12-15T11:52:43.476Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581849,'de_CH') 
;

-- Element: IsAutoPrint
-- 2022-12-15T11:52:47.336Z
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Sofort drucken', PrintName='Sofort drucken',Updated=TO_TIMESTAMP('2022-12-15 13:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581849 AND AD_Language='de_DE'
;

-- 2022-12-15T11:52:47.338Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581849,'de_DE') 
;

-- 2022-12-15T11:52:47.340Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581849,'de_DE') 
;

-- Element: AutoPrintCopies
-- 2022-12-15T11:53:26.087Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Copies to print immediately', PrintName='Copies to print immediately',Updated=TO_TIMESTAMP('2022-12-15 13:53:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581850 AND AD_Language='en_US'
;

-- 2022-12-15T11:53:26.091Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581850,'en_US') 
;

-- Element: AutoPrintCopies
-- 2022-12-15T11:53:38.183Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Exemplare zum Sofortdruck', PrintName='Exemplare zum Sofortdruck',Updated=TO_TIMESTAMP('2022-12-15 13:53:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581850 AND AD_Language='de_CH'
;

-- 2022-12-15T11:53:38.185Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581850,'de_CH') 
;

-- Element: AutoPrintCopies
-- 2022-12-15T11:53:41.520Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Exemplare zum Sofortdruck', PrintName='Exemplare zum Sofortdruck',Updated=TO_TIMESTAMP('2022-12-15 13:53:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581850 AND AD_Language='de_DE'
;

-- 2022-12-15T11:53:41.522Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581850,'de_DE') 
;

-- 2022-12-15T11:53:41.525Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581850,'de_DE') 
;

