-- 23.09.2015 13:30
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N'' AND M_Product.IsActive=''Y'' and M_Product.M_Product_ID in (
	SELECT m_product_id 
	FROM m_productprice 
	where m_productprice.m_pricelist_version_id = 
		(
		SELECT m_pricelist_version.m_pricelist_version_id 
		from m_pricelist_version 
		where exists (
				select 1
				from m_pricelist pl
				where pl.m_pricelist_id=m_pricelist_version.m_pricelist_id
				and pl.IsSOPriceList=''@IsSOTrx@''
				and pl.M_PricingSystem_ID = @M_PricingSystem_ID@
				AND pl.C_Currency_ID = @C_Currency_ID@
				and (pl.C_Country_ID IS NULL OR pl.C_Country_ID IN (
					SELECT C_Country_ID
					FROM C_BPartner_Location
					INNER JOIN C_Location ON C_Location.C_Location_ID=C_BPartner_Location.C_Location_ID
					WHERE C_BPartner_Location_ID=@Bill_Location_ID@
				))
			)
		and m_pricelist_version.isActive = ''Y''
		and m_pricelist_version.validfrom = 
		(
			SELECT max(plv1.validfrom) 
			from m_pricelist_version plv1
			where plv1.m_pricelist_id=m_pricelist_version.m_pricelist_id
			and plv1.validfrom <=  ''@DateOrdered@''
			and plv1.isActive = ''Y''
		)
	)
)',Updated=TO_TIMESTAMP('2015-09-23 13:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540246
;

