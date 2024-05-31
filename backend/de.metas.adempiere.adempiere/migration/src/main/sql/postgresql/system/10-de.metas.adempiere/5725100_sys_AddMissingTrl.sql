
-- Element: AsNewPrice
-- 2024-05-31T08:13:15.097Z
UPDATE AD_Element_Trl SET Name='As New Price',Updated=TO_TIMESTAMP('2024-05-31 11:13:15.097','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583127 AND AD_Language='fr_CH'
;

-- 2024-05-31T08:13:15.102Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583127,'fr_CH')
;

-- Element: AsNewPrice
-- 2024-05-31T08:13:18.503Z
UPDATE AD_Element_Trl SET Name='As New Price',Updated=TO_TIMESTAMP('2024-05-31 11:13:18.503','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583127 AND AD_Language='en_US'
;

-- 2024-05-31T08:13:18.507Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583127,'en_US')
;

-- Element: AsNewPrice
-- 2024-05-31T08:13:24.723Z
UPDATE AD_Element_Trl SET Name='Neupreis',Updated=TO_TIMESTAMP('2024-05-31 11:13:24.723','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583127 AND AD_Language='de_DE'
;

-- 2024-05-31T08:13:24.724Z
UPDATE AD_Element SET Name='Neupreis' WHERE AD_Element_ID=583127
;

-- 2024-05-31T08:13:25.181Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583127,'de_DE')
;

-- 2024-05-31T08:13:25.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583127,'de_DE')
;

-- Element: AsNewPrice
-- 2024-05-31T08:13:28.417Z
UPDATE AD_Element_Trl SET Name='Neupreis',Updated=TO_TIMESTAMP('2024-05-31 11:13:28.417','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583127 AND AD_Language='de_CH'
;

-- 2024-05-31T08:13:28.421Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583127,'de_CH')
;



-- Element: IsAttributeDependant
-- 2024-05-31T08:21:57.469Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Attribute Dependant', PrintName='Attribute Dependant',Updated=TO_TIMESTAMP('2024-05-31 11:21:57.468','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=542237 AND AD_Language='en_GB'
;

-- 2024-05-31T08:21:57.473Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542237,'en_GB')
;

-- Element: IsAttributeDependant
-- 2024-05-31T08:22:09.326Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Attributabhängig', PrintName='Attributabhängig',Updated=TO_TIMESTAMP('2024-05-31 11:22:09.326','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=542237 AND AD_Language='de_CH'
;

-- 2024-05-31T08:22:09.333Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542237,'de_CH')
;

-- Element: IsAttributeDependant
-- 2024-05-31T08:22:19.106Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Attribute Dependant', PrintName='Attribute Dependant',Updated=TO_TIMESTAMP('2024-05-31 11:22:19.106','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=542237 AND AD_Language='en_US'
;

-- 2024-05-31T08:22:19.110Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542237,'en_US')
;

-- Element: IsAttributeDependant
-- 2024-05-31T08:22:22.387Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-31 11:22:22.387','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=542237 AND AD_Language='de_DE'
;

-- 2024-05-31T08:22:22.390Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(542237,'de_DE')
;

-- 2024-05-31T08:22:22.393Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542237,'de_DE')
;

----



-- 2024-05-31T09:01:05.891Z
UPDATE AD_Index_Table SET ErrorMsg='There should be unique minimum value per product.',Updated=TO_TIMESTAMP('2024-05-31 12:01:05.889','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540793
;

-- 2024-05-31T09:01:05.892Z
UPDATE AD_Index_Table_Trl trl SET ErrorMsg='Der Minimalwert sollte für jedes Produkt einzigartig sein.' WHERE AD_Index_Table_ID=540793 AND AD_Language IN ('de_DE', 'de_CH')
;


