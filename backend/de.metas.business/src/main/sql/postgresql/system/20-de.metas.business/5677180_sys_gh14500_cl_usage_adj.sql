-- UI Element: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> main -> 10 -> default.Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-14T21:46:52.046Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615683
;

-- 2023-02-14T21:46:52.051Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712322
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-14T21:46:52.054Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712322
;

-- 2023-02-14T21:46:52.056Z
DELETE FROM AD_Field WHERE AD_Field_ID=712322
;

-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-14T21:47:45.336Z
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2023-02-14 22:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586029
;

-- Field: Credit limit Usage(541679,D) -> CreditLimit_Usage_V(546816,D) -> Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-14T21:50:25.598Z
UPDATE AD_Field SET SortNo=3.000000000000,Updated=TO_TIMESTAMP('2023-02-14 22:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712323
;

-- UI Element: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> main -> 10 -> primary.Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-14T21:56:24.138Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615712
;

-- 2023-02-14T21:56:24.139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712357
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> Business Partner
-- Column: CreditLimit_Usage_V.C_BPartner_ID
-- 2023-02-14T21:56:24.140Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712357
;

-- 2023-02-14T21:56:24.141Z
DELETE FROM AD_Field WHERE AD_Field_ID=712357
;

-- Field: Business Partner_OLD(123,D) -> CL Usage(546818,D) -> Section Code
-- Column: CreditLimit_Usage_V.M_SectionCode_ID
-- 2023-02-14T21:56:51.815Z
UPDATE AD_Field SET SortNo=3.000000000000,Updated=TO_TIMESTAMP('2023-02-14 22:56:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712358
;

