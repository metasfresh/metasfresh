-- 11.12.2015 09:21
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
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
	
	',Updated=TO_TIMESTAMP('2015-12-11 09:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540199
;

