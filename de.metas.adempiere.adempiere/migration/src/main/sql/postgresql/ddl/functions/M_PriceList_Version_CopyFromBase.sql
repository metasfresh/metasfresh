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


