-- Run mode: SWING_CLIENT

-- Element: IsTaxIncluded
-- 2023-09-21T15:27:44.937Z
UPDATE AD_Element_Trl SET Description_Customized='Steuern inklusive', IsUseCustomization='Y', Name_Customized='Steuern inklusive',Updated=TO_TIMESTAMP('2023-09-21 16:27:44.937','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1065 AND AD_Language='de_DE'
;

-- 2023-09-21T15:27:44.952Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(1065,'de_DE')
;

-- 2023-09-21T15:27:44.958Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1065,'de_DE')
;

-- Element: IsTaxIncluded
-- 2023-09-21T16:38:35.178Z
UPDATE AD_Element_Trl SET Description_Customized='Tax Included', IsUseCustomization='Y', Name_Customized='Tax Included',Updated=TO_TIMESTAMP('2023-09-21 17:38:35.177','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1065 AND AD_Language='en_US'
;

-- 2023-09-21T16:38:35.193Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1065,'en_US')
;

-- Element: IsTaxIncluded
-- 2023-09-21T16:39:21.755Z
UPDATE AD_Element_Trl SET Description_Customized='Tax Included', IsUseCustomization='Y', Name_Customized='Tax Included',Updated=TO_TIMESTAMP('2023-09-21 17:39:21.755','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1065 AND AD_Language='en_GB'
;

-- 2023-09-21T16:39:21.757Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1065,'en_GB')
;

-- Element: IsTaxIncluded
-- 2023-09-21T16:39:37.378Z
UPDATE AD_Element_Trl SET Description_Customized='Steuern inklusive', IsUseCustomization='Y', Name_Customized='Steuern inklusive',Updated=TO_TIMESTAMP('2023-09-21 17:39:37.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1065 AND AD_Language='de_CH'
;

-- 2023-09-21T16:39:37.382Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1065,'de_CH')
;

-- Run mode: SWING_CLIENT

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Steuern inklusive
-- Column: SAP_GLJournalLine.IsTaxIncluded
-- 2023-09-21T18:40:52.138Z
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Steuern inklusive', Help=NULL, Name='Steuern inklusive',Updated=TO_TIMESTAMP('2023-09-21 19:40:52.138','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720484
;

-- 2023-09-21T18:40:52.142Z
UPDATE AD_Field_Trl trl SET Description='Steuern inklusive',Name='Steuern inklusive' WHERE AD_Field_ID=720484 AND AD_Language='de_DE'
;

-- 2023-09-21T18:40:52.161Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1065)
;

-- 2023-09-21T18:40:52.185Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720484
;

-- 2023-09-21T18:40:52.190Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720484)
;

