

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
		v_result.PriceList = (CASE dsl.List_Base WHEN 'S' THEN p_PriceStd WHEN 'X' THEN p_PriceLimit ELSE p_PriceList END +  dsl.List_AddAmt) * (1 - dsl.List_Discount/100);
		v_result.PriceStd = (CASE dsl.Std_Base WHEN 'L' THEN p_PriceList WHEN 'X' THEN p_PriceLimit ELSE p_PriceStd END  +dsl.Std_AddAmt ) * (1 - dsl.Std_Discount/100);
		v_result.PriceLimit = (CASE dsl.Limit_Base WHEN 'L' THEN p_PriceList WHEN 'S' THEN p_PriceStd ELSE p_PriceLimit END +  dsl.Limit_AddAmt) * (1 - dsl.Limit_Discount /100);

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




