
--
-- excluding foreign-org PiiPs
--
-- 02.06.2016 10:49
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='

-- GENERAL
M_HU_PI_Item_Product.AD_Org_ID IN (0, @AD_Org_ID@)
AND (M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DateOrdered@'' AND ( ''@DateOrdered@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
	( SELECT i.M_HU_PI_Item_ID 
	FROM M_HU_PI_Item i 
	WHERE i.M_HU_PI_Version_ID IN
		(SELECT v.M_HU_PI_Version_ID 
		FROM M_HU_PI_Version v 
		WHERE v.HU_UnitType = ''TU''
		)
	)
	
	
-- AUFTRAG
AND
(
(
	''@IsSOTrx@'' = ''Y''
)

-- BESTELLUNG
OR

(

''@IsSOTrx@'' = ''N''

AND

M_HU_PI_Item_Product_ID IN
(
	SELECT M_HU_PI_Item_Product_ID  FROM M_ProductPrice_Attribute  ppa
	INNER JOIN M_ProductPrice pp ON pp.M_ProductPrice_ID = ppa.M_ProductPrice_ID
	INNER JOIN M_Product p ON p.M_Product_ID = pp.M_Product_ID
	WHERE p.M_Product_ID = @M_Product_ID@ AND ppa.IsActive = ''Y''
	AND pp.M_PriceList_Version_ID =
	(
		SELECT M_PriceList_Version.M_PriceList_Version_ID
		FROM M_PriceList_Version
		WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
		AND M_PriceList_Version.IsActive = ''Y''
		AND M_PriceList_Version.ValidFrom =
		(
			SELECT MAX(M_PriceList_Version.ValidFrom)
			FROM M_PriceList_Version
			WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
			AND M_PriceList_Version.ValidFrom <=  ''@DateOrdered@''
			GROUP BY M_PriceList_ID
		)
	)
)

)
)', Name='M_HU_PI_Item_Product_For_Org_and_Product_and_DateOrdered',Updated=TO_TIMESTAMP('2016-06-02 10:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540199
;

