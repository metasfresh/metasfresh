-- 2020-11-16T09:01:56.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540524,'1=1  
and exists 
(  select 1 from ( select plv.M_PriceList_Version_ID  
					from M_PriceList_Version plv                  
					where plv.m_pricelist_id = M_PriceList_Version.m_pricelist_id                  
					order by M_PriceList_Version.ValidFrom desc                  
					Limit 3             
					) as records         
	where records.M_PriceList_Version_ID = M_PriceList_Version.M_PriceList_Version_ID     )',TO_TIMESTAMP('2020-11-16 11:01:56','YYYY-MM-DD HH24:MI:SS'),100,'NewestPriceListVersions','U','Y','NewestPriceListVersions','S',TO_TIMESTAMP('2020-11-16 11:01:56','YYYY-MM-DD HH24:MI:SS'),100)
;


