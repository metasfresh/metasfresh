drop view if exists M_ProductScalePrice_CopySource_v;
create or replace view M_ProductScalePrice_CopySource_v as
select
	psp.Target_PriceList_Version_ID
	, psp.M_ProductPrice_ID
	--
	, psp.Qty
	--
	, (psp.prices).PriceList
	, (psp.prices).PriceStd
	, (psp.prices).PriceLimit
	, (psp.prices).M_DiscountSchemaLine_ID
	--
	, psp.AD_Client_ID
	, psp.AD_Org_ID
	, psp.IsActive
	--
	, psp.Source_ProductPrice_ID
	, psp.Source_ProductScalePrice_ID
from (
	select
		target_plv.M_PriceList_Version_ID as Target_PriceList_Version_ID
		-- , target_plv.Name as Target_PriceListVersionName
		, target_pp.M_ProductPrice_ID as M_ProductPrice_ID
		, source_pp.M_ProductPrice_ID as Source_ProductPrice_ID
		--
		, source_psp.Qty
		--
		, M_DiscountSchemaLine_TransformPrices(
			dsl := dsl
			, p_PriceList := source_psp.PriceList
			, p_PriceStd := source_psp.PriceStd
			, p_PriceLimit := source_psp.PriceLimit
			, p_Source_Currency_ID := source_pl.C_Currency_ID
			, p_Target_Currency_ID := target_pl.C_Currency_ID
			, p_Conv_Client_ID := target_plv.AD_Client_ID
			, p_Conv_Org_ID := target_plv.AD_Org_ID
		) as prices
		--
		, target_plv.AD_Client_ID
		, target_plv.AD_Org_ID
		, source_psp.IsActive
		, source_psp.M_ProductScalePrice_ID as Source_ProductScalePrice_ID
	from M_PriceList_Version target_plv
	inner join M_PriceList target_pl on (target_pl.M_PriceList_ID=target_plv.M_PriceList_ID)
	--
	inner join M_PriceList_Version source_plv on (source_plv.M_Pricelist_Version_ID = target_plv.M_Pricelist_Version_Base_ID)
	inner join M_PriceList source_pl on (source_pl.M_PriceList_ID=source_plv.M_PriceList_ID)
	--
	inner join M_ProductPrice target_pp on (target_pp.M_PriceList_Version_ID = target_plv.M_PriceList_Version_ID)
	inner join M_ProductPrice source_pp on (source_pp.M_ProductPrice_ID=target_pp.M_ProductPrice_Base_ID)
	inner join M_ProductScalePrice source_psp on (source_psp.M_ProductPrice_ID = source_pp.M_ProductPrice_ID)
	--
	inner join M_DiscountSchemaLine dsl on (dsl.M_DiscountSchemaLine_ID=target_pp.M_DiscountSchemaLine_ID)
) psp
;


--
-- Test it
-- select * from M_ProductScalePrice_CopySource_v;
