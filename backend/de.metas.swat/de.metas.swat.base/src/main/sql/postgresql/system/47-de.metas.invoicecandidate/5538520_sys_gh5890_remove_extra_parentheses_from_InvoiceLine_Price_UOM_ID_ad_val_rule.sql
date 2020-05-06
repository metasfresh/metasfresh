-- 2019-12-10T08:52:32.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS (
	/* UOM is a default UOM and no product selected */
	SELECT * 
	from C_UOM uu 
	where C_UOM.C_UOM_ID=uu.C_UOM_ID AND IsActive =''Y'' AND IsDefault=''Y'' AND @M_Product_ID@=0
)
OR EXISTS (
	/* UOM is the product''s UOM */
	SELECT * 
	from M_Product p 
	where C_UOM.C_UOM_ID=p.C_UOM_ID AND @M_Product_ID@=p.M_Product_ID
)
OR EXISTS (
	/* For the product''s UOM there is a conversion that is explicitly bound to the product */
	SELECT * 
	from M_Product p INNER JOIN C_UOM_Conversion c ON (p.C_UOM_ID=c.C_UOM_ID AND p.M_PRODUCT_ID=c.M_Product_ID AND c.IsActive =''Y'' )
	where C_UOM.C_UOM_ID=c.C_UOM_TO_ID AND @M_Product_ID@=p.M_Product_ID
)
OR EXISTS (
	/* For the product''s UOM there is a conversion that is not explicitly bound to any other product */
	SELECT * 
	from M_Product p INNER JOIN C_UOM_Conversion c ON (p.C_UOM_ID=c.C_UOM_ID AND c.M_Product_ID IS NULL AND c.IsActive =''Y'' )
	where C_UOM.C_UOM_ID=c.C_UOM_TO_ID AND @M_Product_ID@=p.M_Product_ID
)',Updated=TO_TIMESTAMP('2019-12-10 10:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540472
;

