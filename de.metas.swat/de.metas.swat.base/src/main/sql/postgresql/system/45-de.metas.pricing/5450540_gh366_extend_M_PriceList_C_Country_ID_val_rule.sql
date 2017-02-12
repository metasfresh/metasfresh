-- 13.09.2016 10:55
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='not exists (
select 1
from M_PriceList p
where p.M_PriceList_ID<>@M_PriceList_ID/0@
                AND p.M_PricingSystem_ID=@M_PricingSystem_ID@
                AND p.IsSOPriceList=@IsSOPriceList@
                AND p.C_Country_ID=C_Country.C_Country_ID
)', Description='try to make sure that each c_country_id is unique for the same m_pricingsystem_id and IsSOPriceList',Updated=TO_TIMESTAMP('2016-09-13 10:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=505135
;

-- 13.09.2016 10:57
-- URL zum Konzept
UPDATE AD_Val_Rule SET EntityType='de.metas.pricing',Updated=TO_TIMESTAMP('2016-09-13 10:57:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=505135
;

-- 13.09.2016 10:59
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='not exists (
select 1
from M_PriceList p
where p.M_PriceList_ID<>@M_PriceList_ID/0@
                AND p.M_PricingSystem_ID=@M_PricingSystem_ID@
                AND p.IsSOPriceList=''@IsSOPriceList@''
                AND p.C_Country_ID=C_Country.C_Country_ID
)',Updated=TO_TIMESTAMP('2016-09-13 10:59:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=505135
;

