-- 2023-04-04T12:16:41.056Z
UPDATE AD_Element SET ColumnName='BaseLineType',Updated=TO_TIMESTAMP('2023-04-04 14:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205
;

-- 2023-04-04T12:16:41.059Z
UPDATE AD_Column SET ColumnName='BaseLineType' WHERE AD_Element_ID=582205
;

-- 2023-04-04T12:16:41.060Z
UPDATE AD_Process_Para SET ColumnName='BaseLineType' WHERE AD_Element_ID=582205
;

-- 2023-04-04T12:16:41.081Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-04T12:17:54.619Z
UPDATE AD_Element_Trl SET Name='Base Line Type', PrintName='Base Line Type',Updated=TO_TIMESTAMP('2023-04-04 14:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_CH'
;

-- 2023-04-04T12:17:54.635Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_CH') 
;

-- Element: BaseLineType
-- 2023-04-04T12:18:54.517Z
UPDATE AD_Element_Trl SET Name='Special Baseline Date Determination', PrintName='Special Baseline Date Determination',Updated=TO_TIMESTAMP('2023-04-04 14:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='en_US'
;

-- 2023-04-04T12:18:54.518Z
UPDATE AD_Element SET Name='Special Baseline Date Determination', PrintName='Special Baseline Date Determination' WHERE AD_Element_ID=582205
;

-- 2023-04-04T12:18:55.027Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582205,'en_US') 
;

-- 2023-04-04T12:18:55.028Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Element: BaseLineType
-- 2023-04-04T12:19:08.016Z
UPDATE AD_Element_Trl SET Name='Special Baseline Date Determination', PrintName='Special Baseline Date Determination',Updated=TO_TIMESTAMP('2023-04-04 14:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='fr_CH'
;

-- 2023-04-04T12:19:08.017Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'fr_CH') 
;

-- Element: BaseLineType
-- 2023-04-04T12:19:14.616Z
UPDATE AD_Element_Trl SET Name='Special Baseline Date Determination', PrintName='Special Baseline Date Determination',Updated=TO_TIMESTAMP('2023-04-04 14:19:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='nl_NL'
;

-- 2023-04-04T12:19:14.618Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'nl_NL') 
;

-- Element: BaseLineType
-- 2023-04-04T12:19:24.990Z
UPDATE AD_Element_Trl SET Name='Special Baseline Date Determination', PrintName='Special Baseline Date Determination',Updated=TO_TIMESTAMP('2023-04-04 14:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_DE'
;

-- 2023-04-04T12:19:24.992Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_DE') 
;

-- Element: BaseLineType
-- 2023-04-04T12:20:55.340Z
UPDATE AD_Element_Trl SET Name='Besondere Bestimmung des Basisdatums', PrintName='Besondere Bestimmung des Basisdatums',Updated=TO_TIMESTAMP('2023-04-04 14:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_DE'
;

-- 2023-04-04T12:20:55.342Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_DE') 
;

-- Element: BaseLineType
-- 2023-04-04T12:21:00.809Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-04 14:21:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_DE'
;

-- 2023-04-04T12:21:00.810Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_DE') 
;

-- Element: BaseLineType
-- 2023-04-04T12:21:17.111Z
UPDATE AD_Element_Trl SET Name='Besondere Bestimmung des Basisdatums', PrintName='Besondere Bestimmung des Basisdatums',Updated=TO_TIMESTAMP('2023-04-04 14:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='nl_NL'
;

-- 2023-04-04T12:21:17.112Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'nl_NL') 
;

-- Reference Item: Base Line Types List -> AD_After Delivery
-- 2023-04-04T12:22:18.249Z
UPDATE AD_Ref_List_Trl SET Description='Nach der Lieferung', Name='Nach der Lieferung',Updated=TO_TIMESTAMP('2023-04-04 14:22:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543427
;

-- Reference Item: Base Line Types List -> AD_After Delivery
-- 2023-04-04T12:22:20.555Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-04 14:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543427
;

-- Reference Item: Base Line Types List -> AD_After Delivery
-- 2023-04-04T12:22:31.975Z
UPDATE AD_Ref_List_Trl SET Description='Nach der Lieferung', IsTranslated='Y', Name='Nach der Lieferung',Updated=TO_TIMESTAMP('2023-04-04 14:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543427
;

-- Reference Item: Base Line Types List -> AD_After Delivery
-- 2023-04-04T12:22:34.919Z
UPDATE AD_Ref_List_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2023-04-04 14:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543427
;

-- Reference Item: Base Line Types List -> AD_After Delivery
-- 2023-04-04T12:22:47.535Z
UPDATE AD_Ref_List_Trl SET Description='Nach der Lieferung', Name='Nach der Lieferung',Updated=TO_TIMESTAMP('2023-04-04 14:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543427
;

-- Reference Item: Base Line Types List -> ID_Invoice Date
-- 2023-04-04T12:23:08.535Z
UPDATE AD_Ref_List_Trl SET Description='Datum der Rechnung', Name='Datum der Rechnung',Updated=TO_TIMESTAMP('2023-04-04 14:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543429
;

-- Reference Item: Base Line Types List -> ID_Invoice Date
-- 2023-04-04T12:23:17.500Z
UPDATE AD_Ref_List_Trl SET Description='Datum der Rechnung', Name='Datum der Rechnung',Updated=TO_TIMESTAMP('2023-04-04 14:23:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543429
;

-- Reference Item: Base Line Types List -> ID_Invoice Date
-- 2023-04-04T12:23:22.499Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-04 14:23:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543429
;

-- Reference Item: Base Line Types List -> ID_Invoice Date
-- 2023-04-04T12:23:27.085Z
UPDATE AD_Ref_List_Trl SET Description='Datum der Rechnung', Name='Datum der Rechnung',Updated=TO_TIMESTAMP('2023-04-04 14:23:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543429
;

-- Reference Item: Base Line Types List -> ABL_After Bill of Landing
-- 2023-04-04T12:23:45.425Z
UPDATE AD_Ref_List_Trl SET Description='Nach der Anlandung (Bill of Landing)', Name='Nach der Anlandung (Bill of Landing)',Updated=TO_TIMESTAMP('2023-04-04 14:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543428
;

-- Reference Item: Base Line Types List -> ABL_After Bill of Landing
-- 2023-04-04T12:23:50.799Z
UPDATE AD_Ref_List_Trl SET Description='Nach der Anlandung (Bill of Landing)', IsTranslated='Y', Name='Nach der Anlandung (Bill of Landing)',Updated=TO_TIMESTAMP('2023-04-04 14:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543428
;

-- Reference Item: Base Line Types List -> ABL_After Bill of Landing
-- 2023-04-04T12:24:32.201Z
UPDATE AD_Ref_List_Trl SET Description='Das tatsächliche Ladedatum wird aus der Lieferplanung übernommen.',Updated=TO_TIMESTAMP('2023-04-04 14:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543428
;

-- Reference Item: Base Line Types List -> ABL_After Bill of Landing
-- 2023-04-04T12:24:40.078Z
UPDATE AD_Ref_List_Trl SET Description='Das tatsächliche Ladedatum wird aus der Lieferplanung übernommen.',Updated=TO_TIMESTAMP('2023-04-04 14:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543428
;

-- Reference Item: Base Line Types List -> ABL_After Bill of Landing
-- 2023-04-04T12:24:52.845Z
UPDATE AD_Ref_List_Trl SET Description='The the actual landing date is taken from the delivery planning',Updated=TO_TIMESTAMP('2023-04-04 14:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543428
;

-- Reference Item: Base Line Types List -> AD_After Delivery
-- 2023-04-04T12:25:44.897Z
UPDATE AD_Ref_List_Trl SET Description='wird das Warenausgangsdatum verwendet',Updated=TO_TIMESTAMP('2023-04-04 14:25:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543427
;

-- Reference Item: Base Line Types List -> AD_After Delivery
-- 2023-04-04T12:25:49.179Z
UPDATE AD_Ref_List_Trl SET Description='wird das Warenausgangsdatum verwendet',Updated=TO_TIMESTAMP('2023-04-04 14:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543427
;

-- Reference Item: Base Line Types List -> AD_After Delivery
-- 2023-04-04T12:25:55.634Z
UPDATE AD_Ref_List_Trl SET Description='wird das Warenausgangsdatum verwendet',Updated=TO_TIMESTAMP('2023-04-04 14:25:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543427
;

-- Reference Item: Calculation Method List -> BLDX_Base Line Date +X days
-- 2023-04-04T12:26:48.459Z
UPDATE AD_Ref_List_Trl SET Description='', Name='Datum der Basislinie +X Tage',Updated=TO_TIMESTAMP('2023-04-04 14:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543430
;

-- Reference Item: Calculation Method List -> BLDX_Base Line Date +X days
-- 2023-04-04T12:26:50.743Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:26:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543430
;

-- Reference Item: Calculation Method List -> BLDX_Base Line Date +X days
-- 2023-04-04T12:26:52.694Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543430
;

-- 2023-04-04T12:26:52.694Z
UPDATE AD_Ref_List SET Description='' WHERE AD_Ref_List_ID=543430
;

-- Reference Item: Calculation Method List -> BLDX_Base Line Date +X days
-- 2023-04-04T12:26:54.723Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:26:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543430
;

-- Reference Item: Calculation Method List -> BLDX_Base Line Date +X days
-- 2023-04-04T12:27:02.388Z
UPDATE AD_Ref_List_Trl SET Description='', Name='Datum der Basislinie +X Tage',Updated=TO_TIMESTAMP('2023-04-04 14:27:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543430
;

-- Reference Item: Calculation Method List -> BLDX_Base Line Date +X days
-- 2023-04-04T12:27:05.934Z
UPDATE AD_Ref_List_Trl SET Name='Datum der Basislinie +X Tage',Updated=TO_TIMESTAMP('2023-04-04 14:27:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543430
;

-- Reference Item: Calculation Method List -> BLDXE_Base Line Date +X days and then end of month
-- 2023-04-04T12:27:39.555Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:27:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543431
;

-- Reference Item: Calculation Method List -> BLDXE_Base Line Date +X days and then end of month
-- 2023-04-04T12:27:42.569Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:27:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543431
;

-- Reference Item: Calculation Method List -> BLDXE_Base Line Date +X days and then end of month
-- 2023-04-04T12:27:46.218Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:27:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543431
;

-- 2023-04-04T12:27:46.219Z
UPDATE AD_Ref_List SET Description='' WHERE AD_Ref_List_ID=543431
;

-- Reference Item: Calculation Method List -> BLDXE_Base Line Date +X days and then end of month
-- 2023-04-04T12:27:48.304Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543431
;

-- Reference Item: Calculation Method List -> BLDXE_Base Line Date +X days and then end of month
-- 2023-04-04T12:27:52.809Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:27:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543431
;

-- Reference Item: Calculation Method List -> BLDXE_Base Line Date +X days and then end of month
-- 2023-04-04T12:27:57.902Z
UPDATE AD_Ref_List_Trl SET Name='Datum der Basislinie +X Tage und dann Monatsende',Updated=TO_TIMESTAMP('2023-04-04 14:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543431
;

-- Reference Item: Calculation Method List -> BLDXE_Base Line Date +X days and then end of month
-- 2023-04-04T12:28:00.328Z
UPDATE AD_Ref_List_Trl SET Name='Datum der Basislinie +X Tage und dann Monatsende',Updated=TO_TIMESTAMP('2023-04-04 14:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543431
;

-- Reference Item: Calculation Method List -> BLDXE_Base Line Date +X days and then end of month
-- 2023-04-04T12:28:02.839Z
UPDATE AD_Ref_List_Trl SET Name='Datum der Basislinie +X Tage und dann Monatsende',Updated=TO_TIMESTAMP('2023-04-04 14:28:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543431
;

-- Reference Item: Calculation Method List -> EBLDX_End of the month of baseline date plus X days
-- 2023-04-04T12:28:38.514Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:28:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543432
;

-- Reference Item: Calculation Method List -> EBLDX_End of the month of baseline date plus X days
-- 2023-04-04T12:28:40.819Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:28:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543432
;

-- Reference Item: Calculation Method List -> EBLDX_End of the month of baseline date plus X days
-- 2023-04-04T12:28:43.177Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543432
;

-- 2023-04-04T12:28:43.178Z
UPDATE AD_Ref_List SET Description='' WHERE AD_Ref_List_ID=543432
;

-- Reference Item: Calculation Method List -> EBLDX_End of the month of baseline date plus X days
-- 2023-04-04T12:28:49.427Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543432
;

-- Reference Item: Calculation Method List -> EBLDX_End of the month of baseline date plus X days
-- 2023-04-04T12:28:53.098Z
UPDATE AD_Ref_List_Trl SET Description='',Updated=TO_TIMESTAMP('2023-04-04 14:28:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543432
;

-- Reference Item: Calculation Method List -> EBLDX_End of the month of baseline date plus X days
-- 2023-04-04T12:28:56.703Z
UPDATE AD_Ref_List_Trl SET Name='Ende des Monats des Basisdatums plus X Tage',Updated=TO_TIMESTAMP('2023-04-04 14:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543432
;

-- Reference Item: Calculation Method List -> EBLDX_End of the month of baseline date plus X days
-- 2023-04-04T12:29:00.513Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Ende des Monats des Basisdatums plus X Tage',Updated=TO_TIMESTAMP('2023-04-04 14:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543432
;

-- Reference Item: Calculation Method List -> EBLDX_End of the month of baseline date plus X days
-- 2023-04-04T12:29:05.964Z
UPDATE AD_Ref_List_Trl SET Name='Ende des Monats des Basisdatums plus X Tage',Updated=TO_TIMESTAMP('2023-04-04 14:29:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543432
;

-- Column: C_PaymentTerm.CalculationMethod
-- 2023-04-04T12:33:25.742Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-04-04 14:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586413
;

-- Column: C_PaymentTerm.BaseLineType
-- 2023-04-04T12:33:41.866Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-04-04 14:33:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586411
;

-- Element: BaseLineType
-- 2023-04-04T12:34:31.875Z
UPDATE AD_Element_Trl SET Name='Besondere Bestimmung des Basisdatums', PrintName='Besondere Bestimmung des Basisdatums',Updated=TO_TIMESTAMP('2023-04-04 14:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205 AND AD_Language='de_CH'
;

-- 2023-04-04T12:34:31.876Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'de_CH') 
;

-- Element: CalculationMethod
-- 2023-04-04T12:35:57.603Z
UPDATE AD_Element_Trl SET Name='Berechnungsmethode', PrintName='Berechnungsmethode',Updated=TO_TIMESTAMP('2023-04-04 14:35:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='de_CH'
;

-- 2023-04-04T12:35:57.605Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'de_CH') 
;

-- Element: CalculationMethod
-- 2023-04-04T12:36:04.440Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Berechnungsmethode', PrintName='Berechnungsmethode',Updated=TO_TIMESTAMP('2023-04-04 14:36:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='de_DE'
;

-- 2023-04-04T12:36:04.441Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'de_DE') 
;

-- Element: CalculationMethod
-- 2023-04-04T12:36:13.823Z
UPDATE AD_Element_Trl SET Name='Berechnungsmethode', PrintName='Berechnungsmethode',Updated=TO_TIMESTAMP('2023-04-04 14:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206 AND AD_Language='nl_NL'
;

-- 2023-04-04T12:36:13.824Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'nl_NL') 
;

-- 2023-04-04T12:38:22.536Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE public.C_PaymentTerm ADD COLUMN BaseLineType VARCHAR(3) DEFAULT ''ID'' NOT NULL')
;

/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE public.C_PaymentTerm DROP COLUMN BaseLineTypes ')
;


