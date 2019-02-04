-- 2019-01-31T13:44:01.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N''
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
	/* exclude freight cost products */
	SELECT M_Product_ID
	from M_FreightCost
	where M_FreightCost.AD_Org_ID IN (0, @AD_Org_ID@)
)
',Updated=TO_TIMESTAMP('2019-01-31 13:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540051
;

-- 2019-01-31T13:46:12.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N''
AND M_Product.IsActive=''Y''
AND M_Product.M_Product_ID IN
(
	SELECT M_Product_ID
	from M_ProductPrice
	where M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID = ANY( getPriceListVersionsUpToBase(@M_PriceList_ID@, ''@DateInvoiced@'') )
)
',Updated=TO_TIMESTAMP('2019-01-31 13:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540163
;

-- 2019-01-31T13:47:50.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.IsSummary=''N'' AND M_Product.IsActive=''Y'' and M_Product.M_Product_ID in (
	SELECT M_Product_ID
	from M_ProductPrice
	where M_ProductPrice.IsActive=''Y''
	AND M_ProductPrice.M_PriceList_Version_ID = ANY( getPriceListVersionsUpToBase(@M_PriceList_ID@, ''@DateOrdered@'') )
)
',Updated=TO_TIMESTAMP('2019-01-31 13:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540196
;

-- 2019-01-31T14:13:53.610
-- "M_Product (Trx) without date"
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540149
;

-- 2019-01-31T14:14:28.627
-- "M_Product for C_Invoice_Candidate"
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540246
;

-- 2019-01-31T14:15:58.482
-- "M_Product no freight product and matching BPartner (Trx)"
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540215
;

-- 2019-01-31T14:18:28.397
-- "M_Product (Trx) Order" (Products that have prices valid at DateOrdered)
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540196
;

