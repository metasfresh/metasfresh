--
-- create a new rule as a copy of validation rule M_HU_PI_Item_Product_For_Org_and_Product_and_dateOrdered
--
--

-- 2017-09-04T09:26:56.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540365,'-- GENERAL
M_HU_PI_Item_Product.AD_Org_ID IN (0, @AD_Org_ID@)
AND (M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
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
					AND M_PriceList_Version.ValidFrom <=  ''@DatePromised@''
					GROUP BY M_PriceList_ID
				)
			)
		)
	)
)
',TO_TIMESTAMP('2017-09-04 09:26:55','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','M_HU_PI_Item_Product_For_Org_and_Product_and_DatePromised','S',TO_TIMESTAMP('2017-09-04 09:26:55','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2017-09-04T09:58:21.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2017-09-04 09:58:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540365
;

--
-- C_Order.M_HU_PI_Item_Product_ID
--
-- 2017-09-04T09:27:58.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540365,Updated=TO_TIMESTAMP('2017-09-04 09:27:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550219
;

--
-- C_OrderLine.M_HU_PI_Item_Product_ID
--
-- 2017-09-04T09:28:38.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540365,Updated=TO_TIMESTAMP('2017-09-04 09:28:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549764
;

