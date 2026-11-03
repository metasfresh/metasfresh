-- me03#28616: Fix AD_Val_Rule_ID=540051 "M_Product no freight product (Trx)"
-- Only exclude products with ACTIVE freight cost records (IsActive='Y')
-- Also use @AD_Org_ID/0@ to default to 0 when AD_Org_ID is not yet set in context

UPDATE AD_Val_Rule
SET Code='M_Product.IsSummary=''N''
AND M_Product.IsActive=''Y''
AND M_Product.M_Product_ID IN
(
	SELECT M_Product_ID
	from M_ProductPrice
	where M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID = ANY( getPriceListVersionsUpToBase(@M_PriceList_ID@, ''@DatePromised@'') )
)
AND M_Product.M_Product_ID NOT IN
(
	/* exclude active freight cost products */
	SELECT M_Product_ID
	from M_FreightCost
	where M_FreightCost.IsActive = ''Y''
	AND M_FreightCost.AD_Org_ID IN (0, @AD_Org_ID/0@)
)
',
    Updated='2026-03-06 12:00',
    UpdatedBy=100
WHERE AD_Val_Rule_ID=540051;
