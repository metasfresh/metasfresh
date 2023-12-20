-- 2023-05-02T10:33:50.140617200Z
UPDATE AD_Table_Trl SET Name='Prüfanlagen Gruppe',Updated=TO_TIMESTAMP('2023-05-02 13:33:50.137','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542326
;

-- 2023-05-02T10:33:54.547121800Z
UPDATE AD_Table_Trl SET Name='Prüfanlagen Gruppe',Updated=TO_TIMESTAMP('2023-05-02 13:33:54.546','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542326
;

-- 2023-05-02T10:33:54.548291400Z
UPDATE AD_Table SET Name='Prüfanlagen Gruppe' WHERE AD_Table_ID=542326
;

-- 2023-05-02T10:33:59.002090300Z
UPDATE AD_Table_Trl SET Name='Prüfanlagen Gruppe',Updated=TO_TIMESTAMP('2023-05-02 13:33:59.001','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542326
;

-- Element: S_HumanResourceTestGroup_ID
-- 2023-05-02T10:34:46.863508700Z
UPDATE AD_Element_Trl SET Name='Prüfanlagen Gruppe', PrintName='Prüfanlagen Gruppe',Updated=TO_TIMESTAMP('2023-05-02 13:34:46.863','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582271 AND AD_Language='de_CH'
;

-- 2023-05-02T10:34:46.890674200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582271,'de_CH') 
;

-- Element: S_HumanResourceTestGroup_ID
-- 2023-05-02T10:34:50.111912900Z
UPDATE AD_Element_Trl SET Name='Prüfanlagen Gruppe', PrintName='Prüfanlagen Gruppe',Updated=TO_TIMESTAMP('2023-05-02 13:34:50.111','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582271 AND AD_Language='de_DE'
;

-- 2023-05-02T10:34:50.113915400Z
UPDATE AD_Element SET Name='Prüfanlagen Gruppe', PrintName='Prüfanlagen Gruppe' WHERE AD_Element_ID=582271
;

-- 2023-05-02T10:34:50.865774200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582271,'de_DE') 
;

-- 2023-05-02T10:34:50.870790600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582271,'de_DE') 
;

-- Element: S_HumanResourceTestGroup_ID
-- 2023-05-02T10:34:55.196563300Z
UPDATE AD_Element_Trl SET Name='Prüfanlagen Gruppe', PrintName='Prüfanlagen Gruppe',Updated=TO_TIMESTAMP('2023-05-02 13:34:55.195','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582271 AND AD_Language='fr_CH'
;

-- 2023-05-02T10:34:55.197658500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582271,'fr_CH') 
;

