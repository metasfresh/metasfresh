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
			, p_IsSeasonFixedPrice := source_pp.IsSeasonFixedPrice
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




































drop function if exists M_PriceList_Version_CopyFromBase
(
	/* p_M_PriceList_Version_ID */ numeric
	, /* p_AD_User_ID */ numeric
);

create or replace function M_PriceList_Version_CopyFromBase
(
	p_M_PriceList_Version_ID numeric
	, p_AD_User_ID numeric = 0
)
returns void
as
$BODY$
begin
	--
	-- Delete existing product prices
	DELETE FROM M_ProductPrice where M_PriceList_Version_ID = p_M_PriceList_Version_ID;

	--
	-- Create the new product prices
	INSERT INTO M_ProductPrice
	(
		-- M_ProductPrice_ID -- not needed
		M_PriceList_Version_ID
		, SeqNo
		, MatchSeqNo
		, M_Product_ID
		, C_UOM_ID
		--
		, PriceList
		, PriceStd
		, PriceLimit
		--
		, C_TaxCategory_ID
		--
		, UseScalePrice
		, IsSeasonFixedPrice
		--
		, IsAttributeDependant
		, M_AttributeSetInstance_ID
		--
		, IsDefault
		, IsHUPrice -- FIXME: this shall be part of de.metas.handlingunits.base module
		, M_HU_PI_Item_Product_ID -- FIXME: this shall be part of de.metas.handlingunits.base module
		--
		, AD_Client_ID
		, AD_Org_ID
		, IsActive
		, Created
		, CreatedBy
		, Updated
		, UpdatedBy
		--
		, M_ProductPrice_Base_ID
		, M_DiscountSchemaLine_ID
	)
	--
	SELECT
		s.Target_PriceList_Version_ID
		, s.SeqNo
		, s.MatchSeqNo
		, s.M_Product_ID
		, s.C_UOM_ID
		--
		, s.PriceList
		, s.PriceStd
		, s.PriceLimit
		--
		, s.C_TaxCategory_ID
		--
		, s.UseScalePrice
		, s.IsSeasonFixedPrice
		--
		, s.IsAttributeDependant
		, s.M_AttributeSetInstance_ID
		--
		, s.IsDefault
		, s.IsHUPrice -- FIXME: this shall be part of de.metas.handlingunits.base module
		, s.M_HU_PI_Item_Product_ID -- FIXME: this shall be part of de.metas.handlingunits.base module
		--
		, s.AD_Client_ID
		, s.AD_Org_ID
		, s.IsActive
		, now() as Created
		, p_AD_User_ID as CreatedBy
		, now() as Updated
		, p_AD_User_ID as UpdatedBy
		--
		, s.Source_M_ProductPrice_ID
		, s.M_DiscountSchemaLine_ID
	FROM M_ProductPrice_CopySource_v s
	WHERE
		s.Target_PriceList_Version_ID = p_M_PriceList_Version_ID
		and s.M_DiscountSchemaLine_ID is not null
	;


	--
	-- Create new product scale prices
	INSERT INTO M_ProductScalePrice
	(
		-- m_productscaleprice_id -- not needed
		M_ProductPrice_ID
		--
		, Qty
		, PriceLimit
		, PriceList
		, PriceStd
		--
		, AD_Client_ID
		, AD_Org_ID
		, IsActive
		, Created
		, CreatedBy
		, Updated
		, UpdatedBy
	)
	select
		psp.M_ProductPrice_ID
		--
		, psp.Qty
		, psp.PriceLimit
		, psp.PriceList
		, psp.PriceStd
		--
		, psp.AD_Client_ID
		, psp.AD_Org_ID
		, psp.IsActive
		, now() as Created
		, p_AD_User_ID as CreatedBy
		, now() as Updated
		, p_AD_User_ID as UpdatedBy
	from M_ProductScalePrice_CopySource_v psp
	where
		psp.Target_PriceList_Version_ID = p_M_PriceList_Version_ID
		and psp.M_DiscountSchemaLine_ID is not null
	;

end;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;


