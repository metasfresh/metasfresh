drop view if exists M_ProductPrice_CopySource_v;
create or replace view M_ProductPrice_CopySource_v as
select
	pp.Target_PriceList_Version_ID
	--
	, pp.M_Product_ID
	, pp.C_UOM_ID
	, pp.SeqNo
	, pp.MatchSeqNo
	--
	, (pp.prices).PriceList
	, pp.Source_PriceList
	, (pp.prices).PriceStd
	, pp.Source_PriceStd
	, (pp.prices).PriceLimit
	, pp.Source_PriceLimit
	, (pp.prices).M_DiscountSchemaLine_ID
	--
	, pp.Source_M_ProductPrice_ID
	--
	, pp.C_TaxCategory_ID
	, pp.UseScalePrice
	, pp.IsSeasonFixedPrice
	, pp.IsAttributeDependant
	, pp.M_AttributeSetInstance_ID
	, pp.IsDefault
	, pp.IsHUPrice -- FIXME: this shall be part of de.metas.handlingunits.base module
	, pp.M_HU_PI_Item_Product_ID -- FIXME: this shall be part of de.metas.handlingunits.base module
	--
	, pp.AD_Client_ID
	, pp.AD_Org_ID
	, pp.IsActive
from (
	select
		target_plv.M_PriceList_Version_ID as Target_PriceList_Version_ID
		--
		, M_DiscountSchemaLine_TransformPrices(
			dsl := dsl
			, p_PriceList := source_pp.PriceList
			, p_PriceStd := source_pp.PriceStd
			, p_PriceLimit := source_pp.PriceLimit
			, p_Source_Currency_ID := source_pl.C_Currency_ID
			, p_Target_Currency_ID := target_pl.C_Currency_ID
			, p_Conv_Client_ID := target_plv.AD_Client_ID
			, p_Conv_Org_ID := target_plv.AD_Org_ID
			, p_ApplyDiscountSchema := source_pp.IsSeasonFixedPrice='N' and source_pp.IsActive='Y'
		) as prices
		--
		, source_pp.M_Product_ID
		, source_pp.C_UOM_ID
		, source_pp.MatchSeqNo as MatchSeqNo
		, source_pp.SeqNo as SeqNo
		--
		, target_plv.AD_Client_ID as AD_Client_ID
		, target_plv.AD_Org_ID as AD_Org_ID
		, source_pp.IsActive as IsActive
		--
		, source_pp.PriceList as Source_PriceList
		, source_pp.PriceStd as Source_PriceStd
		, source_pp.PriceLimit as Source_PriceLimit
		--
		, source_pp.M_ProductPrice_ID as Source_M_ProductPrice_ID
		--
		, coalesce(dsl.C_TaxCategory_Target_ID, source_pp.C_TaxCategory_ID) as C_TaxCategory_ID
		, source_pp.UseScalePrice
		, source_pp.IsSeasonFixedPrice
		, source_pp.IsAttributeDependant
		, (case when source_pp.IsAttributeDependant='Y' then source_pp.M_AttributeSetInstance_ID else null end) as M_AttributeSetInstance_ID
		, source_pp.IsDefault
		, source_pp.IsHUPrice -- FIXME: this shall be part of de.metas.handlingunits.base module
		, source_pp.M_HU_PI_Item_Product_ID -- FIXME: this shall be part of de.metas.handlingunits.base module
	--
	from M_PriceList_Version target_plv
	inner join M_PriceList target_pl on (target_pl.M_PriceList_ID=target_plv.M_PriceList_ID)
	inner join C_Currency target_currency on (target_currency.C_Currency_ID=target_pl.C_Currency_ID)
	--
	inner join M_PriceList_Version source_plv on (source_plv.M_Pricelist_Version_ID = target_plv.M_Pricelist_Version_Base_ID)
	inner join M_PriceList source_pl on (source_pl.M_PriceList_ID=source_plv.M_PriceList_ID)
	--
	inner join M_ProductPrice source_pp on (source_pp.M_PriceList_Version_ID = source_plv.M_PriceList_Version_ID)
	--
	left outer join M_DiscountSchemaLine dsl on (dsl.M_DiscountSchemaLine_ID=getMatchingDiscountSchemaLine_ID(
		p_M_DiscountSchema_ID := target_plv.M_DiscountSchema_ID
		, p_M_Product_ID := source_pp.M_Product_ID
		, p_C_TaxCategory_ID := source_pp.C_TaxCategory_ID
	))
) pp
;


--
-- Test it
-- select * from M_ProductPrice_CopySource_v order by Target_PriceList_Version_ID
