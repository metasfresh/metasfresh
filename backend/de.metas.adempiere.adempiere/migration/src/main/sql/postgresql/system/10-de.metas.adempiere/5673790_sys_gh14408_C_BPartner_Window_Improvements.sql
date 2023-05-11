-- Column: C_BPartner_CreditLimit.IsActive
-- 2023-01-26T17:27:58.987Z
UPDATE AD_Column SET ReadOnlyLogic='@IsActive@=''N''',Updated=TO_TIMESTAMP('2023-01-26 19:27:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558972
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Delivery Credit Status
-- Column: C_BPartner_Stats.Delivery_CreditStatus
-- 2023-01-26T17:41:44.527Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-26 19:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614859
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Delivery Credit Used
-- Column: C_BPartner_Stats.Delivery_CreditUsed
-- 2023-01-26T17:41:44.536Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-26 19:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614738
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Offene Posten
-- Column: C_BPartner_Stats.OpenItems
-- 2023-01-26T17:41:44.544Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-01-26 19:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000362
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Section Code
-- Column: C_BPartner_Stats.M_SectionCode_ID
-- 2023-01-26T17:41:44.553Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-01-26 19:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614780
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Währung
-- Column: C_BPartner_Stats.C_Currency_ID
-- 2023-01-26T17:41:44.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-01-26 19:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550943
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Aktiv
-- Column: C_BPartner_Stats.IsActive
-- 2023-01-26T17:41:44.570Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-01-26 19:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000358
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner_Stats.AD_Org_ID
-- 2023-01-26T17:41:44.580Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-01-26 19:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546570
;




-- 2023-01-26T19:04:13.124Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581965,0,TO_TIMESTAMP('2023-01-26 21:04:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Credit Limit usage (Delivery','Credit Limit usage (Delivery',TO_TIMESTAMP('2023-01-26 21:04:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T19:04:13.129Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581965 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-01-26T19:04:27.696Z
UPDATE AD_Element_Trl SET Name='Credit Limit usage (Delivery)', PrintName='Credit Limit usage (Delivery)',Updated=TO_TIMESTAMP('2023-01-26 21:04:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581965 AND AD_Language='de_CH'
;

-- 2023-01-26T19:04:27.739Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581965,'de_CH') 
;

-- Element: null
-- 2023-01-26T19:04:34.186Z
UPDATE AD_Element_Trl SET Name='Credit Limit usage (Delivery)', PrintName='Credit Limit usage (Delivery)',Updated=TO_TIMESTAMP('2023-01-26 21:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581965 AND AD_Language='de_DE'
;

-- 2023-01-26T19:04:34.187Z
UPDATE AD_Element SET Name='Credit Limit usage (Delivery)', PrintName='Credit Limit usage (Delivery)' WHERE AD_Element_ID=581965
;

-- 2023-01-26T19:04:34.668Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581965,'de_DE') 
;

-- 2023-01-26T19:04:34.672Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581965,'de_DE') 
;

-- Element: null
-- 2023-01-26T19:04:40.090Z
UPDATE AD_Element_Trl SET Name='Credit Limit usage (Delivery)', PrintName='Credit Limit usage (Delivery)',Updated=TO_TIMESTAMP('2023-01-26 21:04:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581965 AND AD_Language='en_US'
;

-- 2023-01-26T19:04:40.093Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581965,'en_US') 
;

-- Element: null
-- 2023-01-26T19:04:45.904Z
UPDATE AD_Element_Trl SET Name='Credit Limit usage (Delivery)', PrintName='Credit Limit usage (Delivery)',Updated=TO_TIMESTAMP('2023-01-26 21:04:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581965 AND AD_Language='fr_CH'
;

-- 2023-01-26T19:04:45.906Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581965,'fr_CH') 
;

-- Element: null
-- 2023-01-26T19:04:50.488Z
UPDATE AD_Element_Trl SET Name='Credit Limit usage (Delivery)', PrintName='Credit Limit usage (Delivery)',Updated=TO_TIMESTAMP('2023-01-26 21:04:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581965 AND AD_Language='nl_NL'
;

-- 2023-01-26T19:04:50.490Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581965,'nl_NL') 
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Credit Limit usage (Delivery)
-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- 2023-01-26T19:04:59.262Z
UPDATE AD_Field SET AD_Name_ID=581965, Description=NULL, Help=NULL, Name='Credit Limit usage (Delivery)',Updated=TO_TIMESTAMP('2023-01-26 21:04:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710658
;

-- 2023-01-26T19:04:59.264Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Credit Limit usage (Delivery)' WHERE AD_Field_ID=710658 AND AD_Language='de_DE'
;

-- 2023-01-26T19:04:59.266Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581965) 
;

-- 2023-01-26T19:04:59.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710658
;

-- 2023-01-26T19:04:59.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710658)
;









-- 2023-01-26T19:06:10.746Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581966,0,TO_TIMESTAMP('2023-01-26 21:06:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Credit Limit usage (Order)','Credit Limit usage (Order)',TO_TIMESTAMP('2023-01-26 21:06:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T19:06:10.747Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581966 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Credit Limit usage (Order)
-- Column: C_BPartner.CreditLimitIndicator
-- 2023-01-26T19:06:18.315Z
UPDATE AD_Field SET AD_Name_ID=581966, Description=NULL, Help=NULL, Name='Credit Limit usage (Order)',Updated=TO_TIMESTAMP('2023-01-26 21:06:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561919
;

-- 2023-01-26T19:06:18.316Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Credit Limit usage (Order)' WHERE AD_Field_ID=561919 AND AD_Language='de_DE'
;

-- 2023-01-26T19:06:18.318Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581966) 
;

-- 2023-01-26T19:06:18.321Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=561919
;

-- 2023-01-26T19:06:18.322Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(561919)
;










-- Element: Delivery_CreditUsed
-- 2023-01-26T21:06:24.636Z
UPDATE AD_Element_Trl SET Description='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-26 23:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='de_CH'
;

-- 2023-01-26T21:06:24.652Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'de_CH') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-26T21:06:32.066Z
UPDATE AD_Element_Trl SET Description='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-26 23:06:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='de_DE'
;

-- 2023-01-26T21:06:32.068Z
UPDATE AD_Element SET Description='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ' WHERE AD_Element_ID=581934
;

-- 2023-01-26T21:06:32.703Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581934,'de_DE') 
;

-- 2023-01-26T21:06:32.706Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'de_DE') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-26T21:06:35.363Z
UPDATE AD_Element_Trl SET Description='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-26 23:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='en_US'
;

-- 2023-01-26T21:06:35.365Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'en_US') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-26T21:06:37.727Z
UPDATE AD_Element_Trl SET Description='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-26 23:06:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='fr_CH'
;

-- 2023-01-26T21:06:37.730Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'fr_CH') 
;

-- Element: Delivery_CreditUsed
-- 2023-01-26T21:06:41.417Z
UPDATE AD_Element_Trl SET Description='Specifies the amount of money that was already spent from the credit for completed delivery instructions. ',Updated=TO_TIMESTAMP('2023-01-26 23:06:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581934 AND AD_Language='nl_NL'
;

-- 2023-01-26T21:06:41.419Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581934,'nl_NL') 
;



-- UI Element: Business Partner_OLD(123,D) -> Statistics(540739,D) -> main -> 10 -> default.Delivery Credit Status
-- Column: C_BPartner_Stats.Delivery_CreditStatus
-- 2023-01-27T14:45:22.762Z
UPDATE AD_UI_Element SET SeqNo=34,Updated=TO_TIMESTAMP('2023-01-27 16:45:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614859
;

