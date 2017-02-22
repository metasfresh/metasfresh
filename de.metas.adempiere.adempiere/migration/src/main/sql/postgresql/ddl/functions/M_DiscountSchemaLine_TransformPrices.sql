DROP FUNCTION IF EXISTS M_DiscountSchemaLine_TransformPrices(
	/* dsl */ M_DiscountSchemaLine
	, /* p_PriceList */ numeric
	, /* p_PriceStd */ numeric
	, /* p_PriceLimit */ numeric
	, /* p_Source_Currency_ID */ numeric
	, /* p_Target_Currency_ID */ numeric
	, /* p_Conv_Client_ID */ numeric
	, /* p_Conv_Org_ID */ numeric
	, /* p_ApplyDiscountSchema */ boolean
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
	, p_ApplyDiscountSchema boolean = true
)
RETURNS M_DiscountSchemaLine_TransformResult AS
$BODY$
DECLARE
	v_result M_DiscountSchemaLine_TransformResult;
BEGIN
	--
	-- Convert prices to target currency
	p_PriceList = coalesce(currencyConvert(p_PriceList, p_Source_Currency_ID, p_Target_Currency_ID, dsl.ConversionDate, dsl.C_ConversionType_ID, p_Conv_Client_ID, p_Conv_Org_ID), 0);
	p_PriceStd = coalesce(currencyConvert(p_PriceStd, p_Source_Currency_ID, p_Target_Currency_ID, dsl.ConversionDate, dsl.C_ConversionType_ID, p_Conv_Client_ID, p_Conv_Org_ID), 0);
	p_PriceLimit = coalesce(currencyConvert(p_PriceLimit, p_Source_Currency_ID, p_Target_Currency_ID, dsl.ConversionDate, dsl.C_ConversionType_ID, p_Conv_Client_ID, p_Conv_Org_ID), 0);
	
	-- Initialize the result with converted prices, just in case the discount schema line rules will not be applied
	v_result.PriceList = p_PriceList;
	v_result.PriceStd = p_PriceStd;
	v_result.PriceLimit = p_PriceLimit;
	v_result.M_DiscountSchemaLine_ID = dsl.M_DiscountSchemaLine_ID;

	--
	-- Apply discount schema line rules, if not a "season fixed price"
	if (p_ApplyDiscountSchema) then
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
		-- Apply discount schema line fixed prices
		v_result.PriceList = (case dsl.List_Base when 'F' then dsl.List_Fixed else v_result.PriceList end);
		v_result.PriceStd = (case dsl.Std_Base when 'F' then dsl.Std_Fixed else v_result.PriceStd end);
		v_result.PriceLimit = (case dsl.Limit_Base when 'F' then dsl.Limit_Fixed else v_result.PriceLimit end);
	end if;
	
	
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
;
*/

