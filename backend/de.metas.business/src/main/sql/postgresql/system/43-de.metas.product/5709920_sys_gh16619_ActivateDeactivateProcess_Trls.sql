-- Process: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process(de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process)
-- 2023-11-08T09:59:45.871Z
UPDATE AD_Process_Trl SET Name='Produktpreise aktivieren / deaktivieren',Updated=TO_TIMESTAMP('2023-11-08 11:59:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584763
;

-- Value: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process
-- Classname: de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process
-- 2023-11-08T09:59:48.166Z
UPDATE AD_Process SET Description='Wenn das Produkt als "Auslaufprodukt" gekennzeichnet ist, werden alle Preise in den Preislistenversionen, die zum oder nach dem angegebenen "Auslaufdatum" gültig sind, deaktiviert. Anderenfalls, wenn das Produkt nicht als "Auslaufprodukt" gekennzeichnet ist, werden alle seine Preise aktiviert.', Help='Wenn das Produkt als "Auslaufprodukt" gekennzeichnet ist, werden alle Preise in den Preislistenversionen, die zum oder nach dem angegebenen "Auslaufdatum" gültig sind, deaktiviert. Anderenfalls, wenn das Produkt nicht als "Auslaufprodukt" gekennzeichnet ist, werden alle seine Preise aktiviert.', Name='Produktpreise aktivieren / deaktivieren',Updated=TO_TIMESTAMP('2023-11-08 11:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584763
;

-- Process: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process(de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process)
-- 2023-11-08T09:59:48.102Z
UPDATE AD_Process_Trl SET Name='Produktpreise aktivieren / deaktivieren',Updated=TO_TIMESTAMP('2023-11-08 11:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584763
;

-- Process: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process(de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process)
-- 2023-11-08T09:59:54.206Z
UPDATE AD_Process_Trl SET Name='Activate / Deactivate Product Prices',Updated=TO_TIMESTAMP('2023-11-08 11:59:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584763
;

-- Process: M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process(de.metas.product.process.M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process)
-- 2023-11-08T10:00:02.816Z
UPDATE AD_Process_Trl SET Name='Produktpreise aktivieren / deaktivieren',Updated=TO_TIMESTAMP('2023-11-08 12:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=584763
;

-- Element: DiscontinuedFrom
-- 2023-11-08T10:01:00.643Z
UPDATE AD_Element_Trl SET Name='Auslaufdatum', PrintName='Auslaufdatum',Updated=TO_TIMESTAMP('2023-11-08 12:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580348 AND AD_Language='de_CH'
;

-- 2023-11-08T10:01:00.687Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580348,'de_CH') 
;

-- Element: DiscontinuedFrom
-- 2023-11-08T10:01:03.622Z
UPDATE AD_Element_Trl SET Name='Auslaufdatum', PrintName='Auslaufdatum',Updated=TO_TIMESTAMP('2023-11-08 12:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580348 AND AD_Language='de_DE'
;

-- 2023-11-08T10:01:03.624Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580348,'de_DE') 
;

-- 2023-11-08T10:01:03.630Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580348,'de_DE') 
;

-- Element: DiscontinuedFrom
-- 2023-11-08T10:01:13.632Z
UPDATE AD_Element_Trl SET Name='Auslaufdatum', PrintName='Auslaufdatum',Updated=TO_TIMESTAMP('2023-11-08 12:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580348 AND AD_Language='fr_CH'
;

-- 2023-11-08T10:01:13.636Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580348,'fr_CH') 
;

