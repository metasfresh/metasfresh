-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_sectioncode
-- 2023-02-08T19:59:26.074Z
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2023-02-08 20:59:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585850
;

-- Field: Credit Limit Usage(541673,D) -> CL Usage(546804,D) -> creditlimit_by_sectioncode
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_sectioncode
-- 2023-02-08T19:47:56.834Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2023-02-08 20:47:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712136
;


-- 2023-02-08T19:54:38.032Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712136
;

-- Field: Credit Limit Usage(541673,D) -> CL Usage(546804,D) -> creditlimit_by_sectioncode
-- Column: C_BPartner_Creditlimit_Usage_V.creditlimit_by_sectioncode
-- 2023-02-08T19:54:38.036Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712136
;

-- 2023-02-08T19:54:38.037Z
DELETE FROM AD_Field WHERE AD_Field_ID=712136
;


-- Field: Credit Limit Usage(541673,D) -> CL Usage(546804,D) -> Department
-- Column: C_BPartner_Creditlimit_Usage_V.M_Department_ID
-- 2023-02-08T20:51:44.724Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2023-02-08 21:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712132
;

-- Field: Credit Limit Usage(541673,D) -> CL Usage(546804,D) -> CL Usage
-- Column: C_BPartner_Creditlimit_Usage_V.C_BPartner_Creditlimit_Usage_V_ID
-- 2023-02-08T20:52:07.944Z
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2023-02-08 21:52:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712129
;

-- Column: C_BPartner_Creditlimit_Usage_V.CreditLimit
-- 2023-02-08T22:04:33.970Z
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=255,Updated=TO_TIMESTAMP('2023-02-08 23:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585848
;


