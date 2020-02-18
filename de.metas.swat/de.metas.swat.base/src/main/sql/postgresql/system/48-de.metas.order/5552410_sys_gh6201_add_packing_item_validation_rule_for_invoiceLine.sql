-- 2020-02-17T13:14:26.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540480,'-- GENERAL
M_HU_PI_Item_Product.AD_Org_ID IN (0, @AD_Org_ID@)
AND (M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DateInvoiced@'' AND ( ''@DateInvoiced@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
	( SELECT i.M_HU_PI_Item_ID
	from M_HU_PI_Item i
	where i.M_HU_PI_Version_ID IN
		(SELECT v.M_HU_PI_Version_ID
		from M_HU_PI_Version v
		where v.HU_UnitType = ''TU''
		)
	)
-- Price list
AND
(
     M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID IN
    (
        SELECT pp.M_HU_PI_Item_Product_ID
        from M_ProductPrice pp
        where pp.M_Product_ID = @M_Product_ID@ AND pp.IsActive = ''Y''
        AND pp.M_PriceList_Version_ID =
        (
            SELECT M_PriceList_Version.M_PriceList_Version_ID
            from M_PriceList_Version
            where M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
            AND M_PriceList_Version.IsActive = ''Y''
            AND M_PriceList_Version.ValidFrom =
            (
                SELECT MAX(M_PriceList_Version.ValidFrom)
                from M_PriceList_Version
                where M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
                AND M_PriceList_Version.ValidFrom <=  ''@DateInvoiced@''
                AND M_PriceList_Version.IsActive = ''Y''
                GROUP BY M_PriceList_ID
            )
        )
    )
)',TO_TIMESTAMP('2020-02-17 15:14:26','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','M_HU_PI_Item_Product_For_Org_Product_And_DateInvoiced','S',TO_TIMESTAMP('2020-02-17 15:14:26','YYYY-MM-DD HH24:MI:SS'),100)
;

