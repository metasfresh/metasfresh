-- Run mode: SWING_CLIENT

-- 2024-07-11T14:01:35.028Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Lieferavisposition',Updated=TO_TIMESTAMP('2024-07-11 17:01:35.027','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542366
;

-- 2024-07-11T14:01:46.525Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Lieferavisposition',Updated=TO_TIMESTAMP('2024-07-11 17:01:46.523','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542366
;

-- 2024-07-11T14:01:46.526Z
UPDATE AD_Table SET Name='Lieferavisposition' WHERE AD_Table_ID=542366
;

-- Element: M_Shipping_NotificationLine_ID
-- 2024-07-11T14:02:50.473Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferavisposition', PrintName='Lieferavisposition',Updated=TO_TIMESTAMP('2024-07-11 17:02:50.473','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582701 AND AD_Language='de_CH'
;

-- 2024-07-11T14:02:50.506Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582701,'de_CH')
;

-- Element: M_Shipping_NotificationLine_ID
-- 2024-07-11T14:02:57.801Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferavisposition', PrintName='Lieferavisposition',Updated=TO_TIMESTAMP('2024-07-11 17:02:57.801','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582701 AND AD_Language='de_DE'
;

-- 2024-07-11T14:02:57.802Z
UPDATE AD_Element SET Name='Lieferavisposition', PrintName='Lieferavisposition' WHERE AD_Element_ID=582701
;

-- 2024-07-11T14:02:58.175Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582701,'de_DE')
;

-- 2024-07-11T14:02:58.177Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582701,'de_DE')
;

