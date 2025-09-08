-- Run mode: SWING_CLIENT

-- Name: M_PricingSystem Order
-- 2025-08-18T08:58:40.929Z
UPDATE AD_Val_Rule SET Code='M_PricingSystem.IsSubscriptionOnly=''N'' AND EXISTS ( SELECT 1 from M_PriceList p where p.M_PricingSystem_ID=M_PricingSystem.M_PricingSystem_ID AND p.IsSOPriceList = ''@IsSOTrx@'' AND p.IsActive=''Y'' AND ( p.C_Country_ID IS NULL OR @C_BPartner_Location_ID@=0 OR p.C_Country_ID= ( SELECT MAX(C_Country_ID) from ( SELECT l.C_Country_ID from C_BPartner_Location bpl LEFT JOIN C_Location l ON l.C_Location_ID=bpl.C_Location_ID where bpl.C_BPartner_Location_ID=@C_BPartner_Location_ID@ UNION SELECT 0 ) val ) ) AND EXISTS ( SELECT 1 from M_PriceList where M_PriceList.M_PricingSystem_ID=M_PricingSystem.M_PricingSystem_ID AND M_PriceList.IsActive=''Y'' AND EXISTS ( SELECT 1 from M_PriceList_Version where M_PriceList_Version.M_PriceList_ID=M_PriceList.M_PriceList_ID AND M_PriceList_Version.IsActive=''Y'' ) ) )',Updated=TO_TIMESTAMP('2025-08-18 08:58:40.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=505273
;

-- Name: M_PricingSystem SO only
-- 2025-08-18T09:05:55.966Z
UPDATE AD_Val_Rule SET Code='Value!=''NPL'' AND EXISTS (select * from M_PriceList p where p.M_PricingSystem_ID=M_PricingSystem.M_PricingSystem_ID and p.IsSOPriceList = ''Y'' and p.IsActive=''Y'')',Updated=TO_TIMESTAMP('2025-08-18 09:05:55.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540055
;

-- Name: M_PricingSystem PO only
-- 2025-08-18T09:06:54.819Z
UPDATE AD_Val_Rule SET Code='EXISTS (select * from M_PriceList p where p.M_PricingSystem_ID=M_PricingSystem.M_PricingSystem_ID and p.IsSOPriceList = ''N'' and p.IsActive=''Y'')',Updated=TO_TIMESTAMP('2025-08-18 09:06:54.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540056
;

