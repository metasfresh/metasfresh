-- 2023-08-03T10:22:36.693Z
UPDATE AD_Element SET ColumnName='FullyDeliveredAndCompletelyInvoiced',Updated=TO_TIMESTAMP('2023-08-03 13:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582619
;

-- 2023-08-03T10:22:36.701Z
UPDATE AD_Column SET ColumnName='FullyDeliveredAndCompletelyInvoiced' WHERE AD_Element_ID=582619
;

-- 2023-08-03T10:22:36.703Z
UPDATE AD_Process_Para SET ColumnName='FullyDeliveredAndCompletelyInvoiced' WHERE AD_Element_ID=582619
;

-- 2023-08-03T10:22:36.750Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582619,'en_US') 
;

-- Element: FullyDeliveredAndCompletelyInvoiced
-- 2023-08-03T10:23:20.618Z
UPDATE AD_Element_Trl SET Name='Fully delivered and completely invoiced', PrintName='Fully delivered and completely invoiced',Updated=TO_TIMESTAMP('2023-08-03 13:23:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582619 AND AD_Language='en_US'
;

-- 2023-08-03T10:23:20.620Z
UPDATE AD_Element SET Name='Fully delivered and completely invoiced', PrintName='Fully delivered and completely invoiced', Updated=TO_TIMESTAMP('2023-08-03 13:23:20','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=582619
;

-- 2023-08-03T10:23:21.483Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582619,'en_US') 
;

-- 2023-08-03T10:23:21.491Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582619,'en_US') 
;

-- Element: FullyDeliveredAndCompletelyInvoiced
-- 2023-08-03T10:23:26.621Z
UPDATE AD_Element_Trl SET Name='Fully delivered and completely invoiced', PrintName='Fully delivered and completely invoiced',Updated=TO_TIMESTAMP('2023-08-03 13:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582619 AND AD_Language='fr_CH'
;

-- 2023-08-03T10:23:26.623Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582619,'fr_CH') 
;

-- Element: FullyDeliveredAndCompletelyInvoiced
-- 2023-08-03T10:23:32.591Z
UPDATE AD_Element_Trl SET Name='Fully delivered and completely invoiced', PrintName='Fully delivered and completely invoiced',Updated=TO_TIMESTAMP('2023-08-03 13:23:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582619 AND AD_Language='nl_NL'
;

-- 2023-08-03T10:23:32.593Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582619,'nl_NL') 
;

-- Element: FullyDeliveredAndCompletelyInvoiced
-- 2023-08-03T10:25:05.192Z
UPDATE AD_Element_Trl SET Name='Vollständig ausgeliefert und fakturiert', PrintName='Vollständig ausgeliefert und fakturiert',Updated=TO_TIMESTAMP('2023-08-03 13:25:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582619 AND AD_Language='de_CH'
;

-- 2023-08-03T10:25:05.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582619,'de_CH') 
;

-- Element: FullyDeliveredAndCompletelyInvoiced
-- 2023-08-03T10:25:10.561Z
UPDATE AD_Element_Trl SET Name='Vollständig ausgeliefert und fakturiert', PrintName='Vollständig ausgeliefert und fakturiert',Updated=TO_TIMESTAMP('2023-08-03 13:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582619 AND AD_Language='de_DE'
;

-- 2023-08-03T10:25:10.564Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582619,'de_DE') 
;

-- Column: C_Order.FullyDeliveredAndCompletelyInvoiced
-- Column SQL (old): ( CASE WHEN (SELECT SUM(qtydelivered - qtyordered) from C_OrderLine where C_Order_ID = C_Order.c_order_id) >= 0 AND C_Order.DocStatus IN ('CO', 'CL') THEN 'Y'     ELSE 'N'    END )
-- 2023-08-03T10:26:43.192Z
UPDATE AD_Column SET ColumnSQL='( CASE WHEN  ((SELECT SUM(qtydelivered - qtyordered) from C_OrderLine where C_Order_ID = C_Order.c_order_id) >= 0  AND  C_Order.DocStatus IN (''CO'', ''CL'')) AND  (C_Order.QtyMoved <= C_Order.QtyInvoiced  AND  C_Order.QtyMoved > 0) THEN ''Y''     ELSE ''N''    END )',Updated=TO_TIMESTAMP('2023-08-03 13:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587241
;

-- Column: C_Order.CompletelyInvoiced
-- 2023-08-03T10:28:01.816Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587242
;

-- 2023-08-03T10:28:01.830Z
DELETE FROM AD_Column WHERE AD_Column_ID=587242
;

