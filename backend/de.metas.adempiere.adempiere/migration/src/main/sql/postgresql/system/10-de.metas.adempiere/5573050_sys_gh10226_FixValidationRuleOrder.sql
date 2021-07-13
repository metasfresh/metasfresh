

-- 2020-11-23T13:53:12.208Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='1=1
and exists
(  select 1 from ( select plv.M_PriceList_Version_ID
					from M_PriceList_Version plv
					where plv.m_pricelist_id = M_PriceList_Version.m_pricelist_id
					order by plv.ValidFrom desc
					Limit 5
					) as records
	where records.M_PriceList_Version_ID = M_PriceList_Version.M_PriceList_Version_ID     )',Updated=TO_TIMESTAMP('2020-11-23 15:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540524
;

