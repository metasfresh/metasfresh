-- Name: M_Pricelist_Version_Base_ID same country and currency
-- 2023-12-06T14:21:38.676Z
UPDATE AD_Val_Rule SET Code='M_Pricelist_Version_ID in (select M_Pricelist_Version_ID from M_PriceList_Version baseplv join M_PriceList basepl on baseplv.M_PriceList_ID= basepl.M_PriceList_ID join M_PriceList pl on pl.M_PriceList_ID = @M_PriceList_ID/-1@ where (COALESCE(basepl.C_Country_ID, 0) = COALESCE(pl.C_Country_ID, 0) OR (basepl.C_Country_ID ISNULL )) and basepl.C_Currency_ID = pl.C_Currency_ID )',Updated=TO_TIMESTAMP('2023-12-06 15:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540450
;

