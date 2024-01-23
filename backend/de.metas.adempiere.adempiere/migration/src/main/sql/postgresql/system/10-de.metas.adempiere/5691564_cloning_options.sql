-- Reference: AD_Table_DownlineCopyingStrategy
-- Value: S
-- ValueName: Skip
-- 2023-06-13T20:17:04.068Z
UPDATE AD_Ref_List SET Name='Skip copying children',Updated=TO_TIMESTAMP('2023-06-13 23:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543494
;

-- 2023-06-13T20:17:04.069Z
UPDATE AD_Ref_List_Trl trl SET Name='Skip copying children' WHERE AD_Ref_List_ID=543494 AND AD_Language='en_US'
;

-- Reference: AD_Table_DownlineCopyingStrategy
-- Value: S
-- ValueName: Skip
-- 2023-06-13T20:17:12.946Z
UPDATE AD_Ref_List SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543494
;

-- 2023-06-13T20:17:12.947Z
UPDATE AD_Ref_List_Trl trl SET Name='Skip Copying Children' WHERE AD_Ref_List_ID=543494 AND AD_Language='en_US'
;

-- Reference Item: AD_Table_DownlineCopyingStrategy -> S_Skip
-- 2023-06-13T20:17:17.083Z
UPDATE AD_Ref_List_Trl SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543494
;

-- Reference Item: AD_Table_DownlineCopyingStrategy -> S_Skip
-- 2023-06-13T20:17:18.718Z
UPDATE AD_Ref_List_Trl SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543494
;

-- Reference Item: AD_Table_DownlineCopyingStrategy -> S_Skip
-- 2023-06-13T20:17:29.760Z
UPDATE AD_Ref_List_Trl SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543494
;

-- Reference Item: AD_Table_DownlineCopyingStrategy -> S_Skip
-- 2023-06-13T20:17:31.983Z
UPDATE AD_Ref_List_Trl SET Name='Skip Copying Children',Updated=TO_TIMESTAMP('2023-06-13 23:17:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543494
;

-- 2023-06-13T20:19:15.607Z
UPDATE AD_Element SET ColumnName='DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435
;

-- 2023-06-13T20:19:15.610Z
UPDATE AD_Column SET ColumnName='DownlineCloningStrategy' WHERE AD_Element_ID=582435
;

-- 2023-06-13T20:19:15.612Z
UPDATE AD_Process_Para SET ColumnName='DownlineCloningStrategy' WHERE AD_Element_ID=582435
;

-- 2023-06-13T20:19:15.627Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'en_US')
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:26.883Z
UPDATE AD_Element_Trl SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='de_CH'
;

-- 2023-06-13T20:19:26.886Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'de_CH')
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:30.337Z
UPDATE AD_Element_Trl SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='de_DE'
;

-- 2023-06-13T20:19:30.339Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'de_DE')
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:34.074Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='en_US'
;

-- 2023-06-13T20:19:34.075Z
UPDATE AD_Element SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy' WHERE AD_Element_ID=582435
;

-- 2023-06-13T20:19:34.435Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582435,'en_US')
;

-- 2023-06-13T20:19:34.437Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'en_US')
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:37.699Z
UPDATE AD_Element_Trl SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='fr_CH'
;

-- 2023-06-13T20:19:37.701Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'fr_CH')
;

-- Element: DownlineCloningStrategy
-- 2023-06-13T20:19:42.167Z
UPDATE AD_Element_Trl SET Name='Downline Cloning Strategy', PrintName='Downline Cloning Strategy',Updated=TO_TIMESTAMP('2023-06-13 23:19:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582435 AND AD_Language='nl_NL'
;

-- 2023-06-13T20:19:42.169Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582435,'nl_NL')
;

-- Name: AD_Table_DownlineCloningStrategy
-- 2023-06-13T20:20:05.180Z
UPDATE AD_Reference SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541755
;

-- 2023-06-13T20:20:05.181Z
UPDATE AD_Reference_Trl trl SET Name='AD_Table_DownlineCloningStrategy' WHERE AD_Reference_ID=541755 AND AD_Language='en_US'
;

-- 2023-06-13T20:20:08.663Z
UPDATE AD_Reference_Trl SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541755
;

-- 2023-06-13T20:20:10.357Z
UPDATE AD_Reference_Trl SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541755
;

-- 2023-06-13T20:20:12.533Z
UPDATE AD_Reference_Trl SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541755
;

-- 2023-06-13T20:20:17.369Z
UPDATE AD_Reference_Trl SET Name='AD_Table_DownlineCloningStrategy',Updated=TO_TIMESTAMP('2023-06-13 23:20:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541755
;

