-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:05:20.809Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zuweisung an FEC',Updated=TO_TIMESTAMP('2023-06-12 12:05:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585186
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:05:29.562Z
UPDATE AD_Process_Trl SET Name='Zuweisung an FEC',Updated=TO_TIMESTAMP('2023-06-12 12:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585186
;

-- 2023-06-12T09:05:29.563Z
UPDATE AD_Process SET Name='Zuweisung an FEC' WHERE AD_Process_ID=585186
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:05:36.868Z
UPDATE AD_Process_Trl SET Name='Zuweisung an FEC',Updated=TO_TIMESTAMP('2023-06-12 12:05:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585186
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:05:41.574Z
UPDATE AD_Process_Trl SET Name='Zuweisung an FEC',Updated=TO_TIMESTAMP('2023-06-12 12:05:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585186
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:05:53.048Z
UPDATE AD_Process_Trl SET Description='Dem Devisenvertrag zuweisen',Updated=TO_TIMESTAMP('2023-06-12 12:05:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585186
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:05:57.472Z
UPDATE AD_Process_Trl SET Description='Dem Devisenvertrag zuweisen',Updated=TO_TIMESTAMP('2023-06-12 12:05:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585186
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:06:02.413Z
UPDATE AD_Process_Trl SET Description='Dem Devisenvertrag zuweisen',Updated=TO_TIMESTAMP('2023-06-12 12:06:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585186
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:06:06.598Z
UPDATE AD_Process_Trl SET Description='Dem Devisenvertrag zuweisen',Updated=TO_TIMESTAMP('2023-06-12 12:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585186
;

-- 2023-06-12T09:06:06.599Z
UPDATE AD_Process SET Description='Dem Devisenvertrag zuweisen' WHERE AD_Process_ID=585186
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:06:11.347Z
UPDATE AD_Process_Trl SET Description='Dem Devisenvertrag zuweisen',Updated=TO_TIMESTAMP('2023-06-12 12:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585186
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- 2023-06-12T09:06:23.561Z
UPDATE AD_Process_Trl SET Description='Allocate to Foreign Exchange Contract',Updated=TO_TIMESTAMP('2023-06-12 12:06:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585186
;

-- Element: FEC_RemainingAmount
-- 2023-06-12T09:07:15.774Z
UPDATE AD_Element_Trl SET Name='Verbleibender FEC', PrintName='Verbleibender FEC',Updated=TO_TIMESTAMP('2023-06-12 12:07:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582401 AND AD_Language='de_CH'
;

-- 2023-06-12T09:07:15.798Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582401,'de_CH') 
;

-- Element: FEC_RemainingAmount
-- 2023-06-12T09:07:21.492Z
UPDATE AD_Element_Trl SET Name='Verbleibender FEC', PrintName='Verbleibender FEC',Updated=TO_TIMESTAMP('2023-06-12 12:07:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582401 AND AD_Language='de_DE'
;

-- 2023-06-12T09:07:21.493Z
UPDATE AD_Element SET Name='Verbleibender FEC', PrintName='Verbleibender FEC' WHERE AD_Element_ID=582401
;

-- 2023-06-12T09:07:21.951Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582401,'de_DE') 
;

-- 2023-06-12T09:07:21.952Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582401,'de_DE') 
;

-- Element: FEC_RemainingAmount
-- 2023-06-12T09:07:29.034Z
UPDATE AD_Element_Trl SET Name='Verbleibender FEC', PrintName='Verbleibender FEC',Updated=TO_TIMESTAMP('2023-06-12 12:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582401 AND AD_Language='fr_CH'
;

-- 2023-06-12T09:07:29.036Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582401,'fr_CH') 
;

-- Element: FEC_RemainingAmount
-- 2023-06-12T09:07:34.330Z
UPDATE AD_Element_Trl SET Name='Verbleibender FEC', PrintName='Verbleibender FEC',Updated=TO_TIMESTAMP('2023-06-12 12:07:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582401 AND AD_Language='nl_NL'
;

-- 2023-06-12T09:07:34.332Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582401,'nl_NL') 
;

-- Element: AllocatedAmt
-- 2023-06-12T09:08:14.211Z
UPDATE AD_Element_Trl SET Name='Zugewiesener Betrag', PrintName='Zugewiesener Betrag',Updated=TO_TIMESTAMP('2023-06-12 12:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2677 AND AD_Language='de_CH'
;

-- 2023-06-12T09:08:14.214Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2677,'de_CH') 
;

-- Element: AllocatedAmt
-- 2023-06-12T09:08:22.191Z
UPDATE AD_Element_Trl SET Name='Zugewiesener Betrag', PrintName='Zugewiesener Betrag',Updated=TO_TIMESTAMP('2023-06-12 12:08:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2677 AND AD_Language='nl_NL'
;

-- 2023-06-12T09:08:22.193Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2677,'nl_NL') 
;

-- Element: AllocatedAmt
-- 2023-06-12T09:08:27.585Z
UPDATE AD_Element_Trl SET Name='Zugewiesener Betrag', PrintName='Zugewiesener Betrag',Updated=TO_TIMESTAMP('2023-06-12 12:08:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2677 AND AD_Language='de_DE'
;

-- 2023-06-12T09:08:27.586Z
UPDATE AD_Element SET Name='Zugewiesener Betrag', PrintName='Zugewiesener Betrag' WHERE AD_Element_ID=2677
;

-- 2023-06-12T09:08:28.087Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2677,'de_DE') 
;

-- 2023-06-12T09:08:28.088Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2677,'de_DE') 
;

-- Element: C_ForeignExchangeContract_ID
-- 2023-06-12T09:08:48.969Z
UPDATE AD_Element_Trl SET Name='Devisenvertrag', PrintName='Devisenvertrag',Updated=TO_TIMESTAMP('2023-06-12 12:08:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581935 AND AD_Language='de_CH'
;

-- 2023-06-12T09:08:48.971Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581935,'de_CH') 
;

-- Element: C_ForeignExchangeContract_ID
-- 2023-06-12T09:08:54.452Z
UPDATE AD_Element_Trl SET Name='Devisenvertrag', PrintName='Devisenvertrag',Updated=TO_TIMESTAMP('2023-06-12 12:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581935 AND AD_Language='de_DE'
;

-- 2023-06-12T09:08:54.452Z
UPDATE AD_Element SET Name='Devisenvertrag', PrintName='Devisenvertrag' WHERE AD_Element_ID=581935
;

-- 2023-06-12T09:08:54.907Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581935,'de_DE') 
;

-- 2023-06-12T09:08:54.908Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581935,'de_DE') 
;

-- Element: C_ForeignExchangeContract_ID
-- 2023-06-12T09:09:02.450Z
UPDATE AD_Element_Trl SET Name='Devisenvertrag', PrintName='Devisenvertrag',Updated=TO_TIMESTAMP('2023-06-12 12:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581935 AND AD_Language='fr_CH'
;

-- 2023-06-12T09:09:02.451Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581935,'fr_CH') 
;

-- Element: C_ForeignExchangeContract_ID
-- 2023-06-12T09:09:07.161Z
UPDATE AD_Element_Trl SET Name='Devisenvertrag', PrintName='Devisenvertrag',Updated=TO_TIMESTAMP('2023-06-12 12:09:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581935 AND AD_Language='nl_NL'
;

-- 2023-06-12T09:09:07.163Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581935,'nl_NL') 
;

