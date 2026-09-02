-- Feb 12, 2017 8:20 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='-- GENERAL
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
	
-- Price list
AND
(
	-- AUFTRAG
	(
		''@IsSOTrx@'' = ''Y''
	)

	-- BESTELLUNG
	OR
	(
		''@IsSOTrx@'' = ''N''
		AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID IN
		(
			SELECT pp.M_HU_PI_Item_Product_ID
			FROM M_ProductPrice pp
			WHERE pp.M_Product_ID = @M_Product_ID@ AND pp.IsActive = ''Y''
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
)
',Updated=TO_TIMESTAMP('2017-02-12 20:20:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540199
;

-- Feb 12, 2017 8:25 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='-- GENERAL
(M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DateInvoiced@'' AND ( ''@DateInvoiced@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
	( SELECT i.M_HU_PI_Item_ID 
	FROM M_HU_PI_Item i 
	WHERE i.M_HU_PI_Version_ID IN
		(SELECT v.M_HU_PI_Version_ID 
		FROM M_HU_PI_Version v 
		WHERE v.HU_UnitType = ''TU''
		)
	)
	
AND
(
	-- Rechnung
	(
		''@IsSOTrx@'' = ''Y''
	)

	-- Eingangsrechnungs
	OR
	(
		''@IsSOTrx@'' = ''N''
		AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID IN
		(
			SELECT pp.M_HU_PI_Item_Product_ID
			FROM M_ProductPrice pp
			WHERE pp.M_Product_ID = @M_Product_ID@ AND pp.IsActive = ''Y''
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
					AND M_PriceList_Version.ValidFrom <=  ''@DateInvoiced@''
					GROUP BY M_PriceList_ID
				)
			)
		)
	)
)
',Updated=TO_TIMESTAMP('2017-02-12 20:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540297
;

-- Feb 12, 2017 8:28 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='-- GENERAL
(M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DatePromised@'' AND ( ''@DatePromised@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
	( SELECT i.M_HU_PI_Item_ID 
	FROM M_HU_PI_Item i 
	WHERE i.M_HU_PI_Version_ID IN
		(SELECT v.M_HU_PI_Version_ID 
		FROM M_HU_PI_Version v 
		WHERE v.HU_UnitType = ''TU''
		)
	)

-- Price list	
AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID IN
(
	SELECT pp.M_HU_PI_Item_Product_ID
	FROM M_ProductPrice pp
	WHERE pp.M_Product_ID = @M_Product_ID@ AND pp.IsActive = ''Y''
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
			AND M_PriceList_Version.ValidFrom <=  ''@DatePromised@''
			GROUP BY M_PriceList_ID
		)
	)
)
', Description='used in PMM_PurchaseCandidate',Updated=TO_TIMESTAMP('2017-02-12 20:28:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540330
;

