drop function if exists M_PriceList_Version_CopyFromBase
(
	/* p_M_PriceList_Version_ID */ numeric
	, /* p_AD_User_ID */ numeric
);
drop view if exists M_ProductScalePrice_CopySource_v;
drop view if exists M_ProductPrice_CopySource_v;
DROP FUNCTION IF EXISTS getMatchingDiscountSchemaLine_ID
(
	/* p_M_DiscountSchema_ID */ numeric
	, /* p_M_Product_ID */ numeric
	, /* p_C_TaxCategory_ID */ numeric
);
DROP FUNCTION IF EXISTS M_DiscountSchemaLine_TransformPrices(
	/* dsl */ M_DiscountSchemaLine
	, /* p_PriceList */ numeric
	, /* p_PriceStd */ numeric
	, /* p_PriceLimit */ numeric
	, /* p_Source_Currency_ID */ numeric
	, /* p_Target_Currency_ID */ numeric
	, /* p_Conv_Client_ID */ numeric
	, /* p_Conv_Org_ID */ numeric
)
;
DROP FUNCTION IF EXISTS M_DiscountSchemaLine_ROUND
(
	/* p_Price */ numeric
	, /* p_RoundingMode */ varchar
	, /* p_C_Currency_ID */ numeric
);
--
DROP TYPE IF EXISTS M_DiscountSchemaLine_TransformResult;



CREATE OR REPLACE FUNCTION getMatchingDiscountSchemaLine_ID
(
	p_M_DiscountSchema_ID numeric
	, p_M_Product_ID numeric
	, p_C_TaxCategory_ID numeric
)
RETURNS numeric
AS
$BODY$
			select dsl.M_DiscountSchemaLine_ID
			from M_DiscountSchemaLine dsl
			where dsl.M_DiscountSchema_ID=p_M_DiscountSchema_ID
			and dsl.IsActive='Y'
			and (dsl.M_Product_ID is null or dsl.M_Product_ID=p_M_Product_ID)
			and (
				dsl.M_Product_Category_ID is null
				or exists (select 1 from M_Product p where p.M_Product_ID=p_M_Product_ID and p.M_Product_Category_ID=dsl.M_Product_Category_ID)
			)
			and (dsl.C_TaxCategory_ID is null or dsl.C_TaxCategory_ID=p_C_TaxCategory_ID)
			order by SeqNo, M_DiscountSchemaLine_ID
			limit 1
$BODY$
LANGUAGE sql STABLE;



















create type M_DiscountSchemaLine_TransformResult as (
	PriceList numeric
	, PriceStd numeric
	, PriceLimit numeric
	, M_DiscountSchemaLine_ID numeric
);
COMMENT ON TYPE M_DiscountSchemaLine_TransformResult IS 'The result of M_DiscountSchemaLine_TransformPrices';



--
--
CREATE OR REPLACE FUNCTION M_DiscountSchemaLine_ROUND
(
	p_Price numeric
	, p_RoundingMode varchar
	, p_C_Currency_ID numeric
)
RETURNS numeric AS
$BODY$
BEGIN
	return CASE p_RoundingMode
		WHEN 'N' THEN p_Price -- No rounding
		WHEN '0' THEN ROUND(p_Price, 0) -- Even .00
		WHEN 'D' THEN ROUND(p_Price, 1) -- Dime .10
		WHEN 'T' THEN ROUND(p_Price, -1)  -- Ten 10.00
		WHEN '5' THEN ROUND(p_Price*20,0)/20 -- Nickle .05
		WHEN 'Q' THEN ROUND(p_Price*4,0)/4 -- Quarter .25
		WHEN '9' THEN
			CASE  -- Whole 9 or 5
				WHEN MOD(ROUND(p_Price),10)<=5 THEN ROUND(p_Price)+(5-MOD(ROUND(p_Price),10))
				WHEN MOD(ROUND(p_Price),10)>5 THEN ROUND(p_Price)+(9-MOD(ROUND(p_Price),10))
			END
		ELSE currencyRound(p_Price, p_C_Currency_ID, 'N') -- Currency rounding
	END;
END;
$BODY$
LANGUAGE plpgsql STABLE
COST 100;




--
--
CREATE OR REPLACE FUNCTION M_DiscountSchemaLine_TransformPrices
(
	dsl M_DiscountSchemaLine
	, p_PriceList numeric
	, p_PriceStd numeric
	, p_PriceLimit numeric
	, p_Source_Currency_ID numeric
	, p_Target_Currency_ID numeric
	, p_Conv_Client_ID numeric
	, p_Conv_Org_ID numeric
)
RETURNS M_DiscountSchemaLine_TransformResult AS
$BODY$
DECLARE
	v_result M_DiscountSchemaLine_TransformResult;
BEGIN
	v_result.M_DiscountSchemaLine_ID = dsl.M_DiscountSchemaLine_ID;

	--
	-- Convert prices to target currency
	p_PriceList = coalesce(currencyConvert(p_PriceList, p_Source_Currency_ID, p_Target_Currency_ID, dsl.ConversionDate, dsl.C_ConversionType_ID, p_Conv_Client_ID, p_Conv_Org_ID), 0);
	p_PriceStd = coalesce(currencyConvert(p_PriceStd, p_Source_Currency_ID, p_Target_Currency_ID, dsl.ConversionDate, dsl.C_ConversionType_ID, p_Conv_Client_ID, p_Conv_Org_ID), 0);
	p_PriceLimit = coalesce(currencyConvert(p_PriceLimit, p_Source_Currency_ID, p_Target_Currency_ID, dsl.ConversionDate, dsl.C_ConversionType_ID, p_Conv_Client_ID, p_Conv_Org_ID), 0);

	--
	-- Copy/apply discount schema line rules:
	-- * copy base price based on M_DiscountSchemaLine.*_Base (S - PriceStd, L - PriceList, X - PriceLimit), NOTE: F-Fixed price will be applied later
	-- * add M_DiscountSchemaLine.*_AddAmt
	-- * apply M_DiscountSchemaLine.*_Discount
	-- NOTE: task 08136, additional requirement: if a product price is zero in the base/source PLV, then it shall remain zero, even if an *_AddAmt was specified
	v_result.PriceList = (CASE dsl.List_Base WHEN 'S' THEN p_PriceStd WHEN 'X' THEN p_PriceLimit ELSE p_PriceList END + CASE WHEN p_PriceList=0 THEN 0 ELSE dsl.List_AddAmt END) * (1 - dsl.List_Discount/100);
	v_result.PriceStd = (CASE dsl.Std_Base WHEN 'L' THEN p_PriceList WHEN 'X' THEN p_PriceLimit ELSE p_PriceStd END  + CASE WHEN p_PriceStd=0 THEN 0 ELSE dsl.Std_AddAmt END) * (1 - dsl.Std_Discount/100);
	v_result.PriceLimit = (CASE dsl.Limit_Base WHEN 'L' THEN p_PriceList WHEN 'S' THEN p_PriceStd ELSE p_PriceLimit END + CASE WHEN p_PriceLimit=0 THEN 0 ELSE dsl.Limit_AddAmt END) * (1 - dsl.Limit_Discount /100);

	--
	-- Apply discount schema line roundings
	v_result.PriceList = M_DiscountSchemaLine_ROUND(v_result.PriceList, dsl.List_Rounding, p_Target_Currency_ID);
	v_result.PriceStd = M_DiscountSchemaLine_ROUND(v_result.PriceStd, dsl.Std_Rounding, p_Target_Currency_ID);
	v_result.PriceLimit = M_DiscountSchemaLine_ROUND(v_result.PriceLimit, dsl.Limit_Rounding, p_Target_Currency_ID);

	--
	--
	v_result.PriceList = (case dsl.List_Base when 'F' then dsl.List_Fixed else v_result.PriceList end);
	v_result.PriceStd = (case dsl.Std_Base when 'F' then dsl.Std_Fixed else v_result.PriceStd end);
	v_result.PriceLimit = (case dsl.Limit_Base when 'F' then dsl.Limit_Fixed else v_result.PriceLimit end);
	
	
	return v_result;
END;
$BODY$
LANGUAGE plpgsql STABLE
COST 100;



--
-- Test
/*
select
	(t.prices).PriceList
	, (t.prices).PriceStd
	, (t.prices).PriceLimit
	, t.*
from (
	select
		M_DiscountSchemaLine_TransformPrices(
			dsl := dsl
			, p_PriceList := 1
			, p_PriceStd := 2
			, p_PriceLimit := 3
			, p_Source_Currency_ID := 318
			, p_Target_Currency_ID := 318
			, p_Conv_Client_ID := 1000000
			, p_Conv_Org_ID := 1000000
		) as prices
		, dsl.*
	from M_DiscountSchemaLine dsl
) t
*/















































create or replace view M_ProductPrice_CopySource_v as
select
	pp.Target_PriceList_Version_ID

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
	--
	, pp.AD_Client_ID
	, pp.AD_Org_ID
	, pp.IsActive
from (
	select
		target_plv.M_PriceList_Version_ID as Target_PriceList_Version_ID
		-- , target_plv.Name as Target_PriceListVersionName
		-- , source_plv.Name as Source_PriceListVersionName
		-- , source_p.Name as Source_ProductName
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
		, source_pp.C_TaxCategory_ID
		, source_pp.UseScalePrice
		, source_pp.IsSeasonFixedPrice
		, source_pp.IsAttributeDependant
		, (case when source_pp.IsAttributeDependant='Y' then source_pp.M_AttributeSetInstance_ID else null end) as M_AttributeSetInstance_ID
		, source_pp.IsDefault
	--
	from M_PriceList_Version target_plv
	inner join M_PriceList target_pl on (target_pl.M_PriceList_ID=target_plv.M_PriceList_ID)
	inner join C_Currency target_currency on (target_currency.C_Currency_ID=target_pl.C_Currency_ID)
	--
	inner join M_PriceList_Version source_plv on (source_plv.M_Pricelist_Version_ID = target_plv.M_Pricelist_Version_Base_ID)
	inner join M_PriceList source_pl on (source_pl.M_PriceList_ID=source_plv.M_PriceList_ID)
	--
	inner join M_ProductPrice source_pp on (source_pp.M_PriceList_Version_ID = source_plv.M_PriceList_Version_ID)
	-- inner join M_Product source_p on (source_p.M_Product_ID=source_pp.M_Product_ID)
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


