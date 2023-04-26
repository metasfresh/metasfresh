

update ad_val_rule set updated='2022-06-20 11:26', updatedby=99, code='1=1
and exists
(  select 1 from ( select plv.M_PriceList_Version_ID
					from M_PriceList_Version plv
					where plv.m_pricelist_id = M_PriceList_Version.m_pricelist_id
					order by plv.ValidFrom desc
					Limit 10
					) as records
	where records.M_PriceList_Version_ID = M_PriceList_Version.M_PriceList_Version_ID     )'
where AD_val_rule_id=540524;