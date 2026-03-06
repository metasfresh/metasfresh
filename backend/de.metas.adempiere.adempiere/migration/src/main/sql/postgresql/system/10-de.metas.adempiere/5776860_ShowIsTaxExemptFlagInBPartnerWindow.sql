-- Run mode: SWING_CLIENT
-- Field: Geschäftspartner(123,D) -> Kunde(223,D) -> Steuerbefreit
-- Column: C_BPartner.IsTaxExempt
-- 2025-11-13T14:41:04.524Z
UPDATE AD_Field SET IsActive='Y',Updated=TO_TIMESTAMP('2025-11-13 14:41:04.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=9657
;

-- Element: IsTaxExempt
-- 2025-11-13T14:41:26.207Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-13 14:41:26.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=930 AND AD_Language='fr_CH'
;

-- 2025-11-13T14:41:26.210Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(930,'fr_CH')
;

-- Element: IsTaxExempt
-- 2025-11-13T14:41:43.752Z
UPDATE AD_Element_Trl SET Name='Tax Exempt', PrintName='Tax Exempt',Updated=TO_TIMESTAMP('2025-11-13 14:41:43.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=930 AND AD_Language='en_GB'
;

-- 2025-11-13T14:41:43.754Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-13T14:41:43.939Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(930,'en_GB')
;

-- Element: IsTaxExempt
-- 2025-11-13T14:41:53.001Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuerbefreit', PrintName='Steuerbefreit',Updated=TO_TIMESTAMP('2025-11-13 14:41:53.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=930 AND AD_Language='de_CH'
;

-- 2025-11-13T14:41:53.002Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-13T14:41:53.189Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(930,'de_CH')
;

-- Element: IsTaxExempt
-- 2025-11-13T14:42:07.147Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='SO Tax exempt', PrintName='SO Tax exempt',Updated=TO_TIMESTAMP('2025-11-13 14:42:07.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=930 AND AD_Language='en_GB'
;

-- 2025-11-13T14:42:07.148Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-13T14:42:07.339Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(930,'en_GB')
;

-- Element: IsTaxExempt
-- 2025-11-13T14:42:15.981Z
UPDATE AD_Element_Trl SET Description='Business partner is exempt from tax on sales',Updated=TO_TIMESTAMP('2025-11-13 14:42:15.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=930 AND AD_Language='en_GB'
;

-- 2025-11-13T14:42:15.982Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-13T14:42:16.164Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(930,'en_GB')
;

-- Element: IsTaxExempt
-- 2025-11-13T14:42:25.980Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-13 14:42:25.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=930 AND AD_Language='de_DE'
;

-- 2025-11-13T14:42:25.982Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(930,'de_DE')
;

-- 2025-11-13T14:42:25.983Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(930,'de_DE')
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Steuerbefreit
-- Column: C_BPartner.IsTaxExempt
-- 2025-11-13T14:43:31.843Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9657,0,223,540672,638714,'F',TO_TIMESTAMP('2025-11-13 14:43:31.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Achtung, Steuersatz kann auch nur 0% (steuerfrei) sein.','Wenn ein Geschäftspartner von der Umsatzsteuer befreit ist, wird der befreite Steuersatz verwendet. Dazu müssen Sie einen Steuersatz mit einem Satz von 0 % einrichten und angeben, dass dies Ihr steuerbefreiter Satz ist.  Dies ist für die Steuerberichterstattung erforderlich, damit Sie steuerbefreite Transaktionen verfolgen können.','Y','Y','N','Y','N','N','N',0,'Steuerbefreit',360,0,0,TO_TIMESTAMP('2025-11-13 14:43:31.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Steuerbefreit
-- Column: C_BPartner.IsTaxExempt
-- 2025-11-13T14:44:05.937Z
UPDATE AD_UI_Element SET SeqNo=312,Updated=TO_TIMESTAMP('2025-11-13 14:44:05.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638714
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Steuerbefreit
-- Column: C_BPartner.IsTaxExempt
-- 2025-11-13T14:44:53.807Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-11-13 14:44:53.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638714
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Preissystem
-- Column: C_BPartner.M_PricingSystem_ID
-- 2025-11-13T14:44:53.814Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-11-13 14:44:53.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545722
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Mutate Price
-- Column: C_BPartner.IsAllowPriceMutation
-- 2025-11-13T14:44:53.822Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-11-13 14:44:53.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560686
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Rabatt Schema
-- Column: C_BPartner.M_DiscountSchema_ID
-- 2025-11-13T14:44:53.829Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-11-13 14:44:53.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545723
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Mahnung
-- Column: C_BPartner.C_Dunning_ID
-- 2025-11-13T14:44:53.835Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-11-13 14:44:53.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545724
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Erster Verkauf
-- Column: C_BPartner.FirstSale
-- 2025-11-13T14:44:53.843Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-11-13 14:44:53.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000266
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.IsAllowActionPrice
-- Column: C_BPartner.IsAllowActionPrice
-- 2025-11-13T14:44:53.850Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-11-13 14:44:53.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560295
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Vollständige LU Erforderlich
-- Column: C_BPartner.IsFullLURequired
-- 2025-11-13T14:44:53.857Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-11-13 14:44:53.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631381
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Sektion
-- Column: C_BPartner.AD_Org_ID
-- 2025-11-13T14:44:53.863Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-11-13 14:44:53.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545731
;

