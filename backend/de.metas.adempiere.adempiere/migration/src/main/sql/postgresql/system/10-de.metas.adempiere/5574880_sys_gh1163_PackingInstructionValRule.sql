-- 2020-12-15T09:45:18.895Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='-- GENERAL
    M_HU_PI_Item_Product.AD_Org_ID IN (0, @AD_Org_ID@)
					AND (M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID NOT IN (100)))
					AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
					AND M_HU_PI_Item_Product.ValidFrom <= ''@DatePromised@'' AND ( ''@DatePromised@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
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
AND (
		(
			M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID IN
			(
				SELECT pp.M_HU_PI_Item_Product_ID
				from M_ProductPrice pp
				where pp.M_Product_ID = @M_Product_ID@
				  AND pp.IsActive = ''Y''
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
									  AND M_PriceList_Version.ValidFrom <= ''@DatePromised@''
									  AND M_PriceList_Version.IsActive = ''Y''
									GROUP BY M_PriceList_ID
								)
					  )
			)
		)
		OR  M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID IN
		  (
			  SELECT pp.M_HU_PI_Item_Product_ID
			  from M_ProductPrice pp
			  where pp.M_Product_ID = @M_Product_ID@
				AND pp.IsActive = ''Y''
				AND pp.M_PriceList_Version_ID =
					(
						SELECT M_PriceList_Version.M_PriceList_Version_ID
						from M_PriceList_Version
						where M_PriceList_Version.M_PriceList_ID = (SELECT basepricelist_id from  M_Pricelist WHERE  m_pricelist_id = @M_PriceList_ID@)
						  AND M_PriceList_Version.IsActive = ''Y''
						  AND M_PriceList_Version.ValidFrom =
							  (
								  SELECT MAX(M_PriceList_Version.ValidFrom)
								  from M_PriceList_Version
								  where M_PriceList_Version.m_pricelist_id = (SELECT basepricelist_id from  M_Pricelist WHERE  m_pricelist_id = @M_PriceList_ID@)
									AND M_PriceList_Version.ValidFrom <= ''@DatePromised@''
									AND M_PriceList_Version.IsActive = ''Y''
								  GROUP BY M_PriceList_ID
							  )
					)
		  )
		OR EXISTS
		(
			SELECT 1
			from M_ProductPrice pp
			where pp.M_Product_ID = @M_Product_ID@
			  AND pp.IsActive = ''Y''
			  AND pp.M_PriceList_Version_ID = (SELECT M_PriceList_Version.M_PriceList_Version_ID
											   from M_PriceList_Version
											   where M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
												 AND M_PriceList_Version.IsActive = ''Y''
												 AND M_PriceList_Version.ValidFrom =
													 (SELECT MAX(M_PriceList_Version.ValidFrom)
													  from M_PriceList_Version
													  where M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
														AND M_PriceList_Version.ValidFrom <= ''@DatePromised@''
														AND M_PriceList_Version.IsActive = ''Y''
													  GROUP BY M_PriceList_ID)
			)
			  AND pp.m_hu_pi_item_product_id IS NULL
		)
		OR EXISTS
		(
            SELECT 1
            from M_ProductPrice pp
            where pp.M_Product_ID = @M_Product_ID@
              AND pp.IsActive = ''Y''
              AND pp.M_PriceList_Version_ID = (SELECT M_PriceList_Version.M_PriceList_Version_ID
                                               from M_PriceList_Version
                                               where M_PriceList_Version.M_PriceList_ID = (SELECT basepricelist_id from  M_Pricelist WHERE  m_pricelist_id = @M_PriceList_ID@)
                                                 AND M_PriceList_Version.IsActive = ''Y''
                                                 AND M_PriceList_Version.ValidFrom =
                                                     (SELECT MAX(M_PriceList_Version.ValidFrom)
                                                      from M_PriceList_Version
                                                      where M_PriceList_Version.M_PriceList_ID = (SELECT basepricelist_id from  M_Pricelist WHERE  m_pricelist_id = @M_PriceList_ID@)
                                                        AND M_PriceList_Version.ValidFrom <= ''@DatePromised@''
                                                        AND M_PriceList_Version.IsActive = ''Y''
                                                      GROUP BY M_PriceList_ID)
            )
              AND pp.m_hu_pi_item_product_id IS NULL
        )
    )',Updated=TO_TIMESTAMP('2020-12-15 11:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540365
;

