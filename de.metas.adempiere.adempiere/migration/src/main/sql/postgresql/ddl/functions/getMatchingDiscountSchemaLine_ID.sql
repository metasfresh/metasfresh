DROP FUNCTION IF EXISTS getMatchingDiscountSchemaLine_ID
(
	/* p_M_DiscountSchema_ID */ numeric
	, /* p_M_Product_ID */ numeric
	, /* p_C_TaxCategory_ID */ numeric
);


CREATE OR REPLACE FUNCTION getMatchingDiscountSchemaLine_ID
(
	p_M_DiscountSchema_ID numeric
	, p_M_Product_ID numeric
	, p_C_TaxCategory_ID numeric
)
RETURNS numeric
AS
$BODY$
            -- Warning about this function: if a line (discount schema line) with a small seqno exists and has no product/category/anything, it will be selected for all the products which appear below it (have greater seqno)!
            -- in other words: be careful about having empty lines as first lines!
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
