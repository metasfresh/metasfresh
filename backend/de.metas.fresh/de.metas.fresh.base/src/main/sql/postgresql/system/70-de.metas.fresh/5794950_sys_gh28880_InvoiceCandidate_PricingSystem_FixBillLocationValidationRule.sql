-- Run mode: SWING_CLIENT

-- Name: M_PricingSystem Invoice Candidate
-- 2026-03-20T11:14:51.402Z
UPDATE AD_Val_Rule SET Code='M_PricingSystem.IsSubscriptionOnly=''N'' AND EXISTS (select * from M_PriceList p where p.M_PricingSystem_ID=M_PricingSystem.M_PricingSystem_ID and p.IsSOPriceList = ''@IsSOTrx/X@'' and (p.C_Country_ID is null or @Bill_Location_ID/-1@=0 or p.C_Country_ID=( select max(C_Country_ID) from ( select l.C_Country_ID from C_BPartner_Location bpl left join C_Location l on l.C_Location_ID=bpl.C_Location_ID where bpl.C_BPartner_Location_ID=@Bill_Location_ID/-1@ union select 0) val ) ) )',Updated=TO_TIMESTAMP('2026-03-20 11:14:51.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540247
;
