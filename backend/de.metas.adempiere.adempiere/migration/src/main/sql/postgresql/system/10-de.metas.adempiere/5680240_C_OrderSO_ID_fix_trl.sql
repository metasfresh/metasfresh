-- Element: C_OrderSO_ID
-- 2023-03-02T16:03:01.864Z
UPDATE AD_Element_Trl SET Description='', Help='', Name='Sales Order',Updated=TO_TIMESTAMP('2023-03-02 18:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543479 AND AD_Language='en_US'
;

-- 2023-03-02T16:03:01.866Z
UPDATE AD_Element SET Description='', Help='', Name='Sales Order' WHERE AD_Element_ID=543479
;

-- 2023-03-02T16:03:02.230Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543479,'en_US') 
;

-- 2023-03-02T16:03:02.233Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543479,'en_US') 
;

-- Element: C_OrderSO_ID
-- 2023-03-02T16:03:06.680Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-02 18:03:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543479 AND AD_Language='de_CH'
;

-- 2023-03-02T16:03:06.682Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543479,'de_CH') 
;

-- Element: C_OrderSO_ID
-- 2023-03-02T16:03:13.063Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-02 18:03:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543479 AND AD_Language='de_DE'
;

-- 2023-03-02T16:03:13.065Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543479,'de_DE') 
;

