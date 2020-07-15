
-- Apr 11, 2016 2:52 PM
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540329,'(
EXISTS (
	/* UOM is a default UOM and no product selected */
	SELECT * 
	FROM C_UOM uu 
	WHERE C_UOM.C_UOM_ID=uu.C_UOM_ID AND IsActive =''Y'' AND IsDefault=''Y'' AND @M_Product_ID@=0
)
OR EXISTS (
	/* UOM is the products UOM */
	SELECT * 
	FROM M_Product p 
	WHERE C_UOM.C_UOM_ID=p.C_UOM_ID AND @M_Product_ID@=p.M_Product_ID
)
OR EXISTS (
	/* For the products UOM there is a conversion that is explicitly bound to the product */
	SELECT * 
	FROM M_Product p INNER JOIN C_UOM_Conversion c ON (p.C_UOM_ID=c.C_UOM_ID AND p.M_PRODUCT_ID=c.M_Product_ID AND c.IsActive =''Y'' )
	WHERE C_UOM.C_UOM_ID=c.C_UOM_TO_ID AND @M_Product_ID@=p.M_Product_ID
)
OR EXISTS (
	/* For the products UOM there is a conversion that is not bound to any product explicitly */
	SELECT * 
	FROM M_Product p INNER JOIN C_UOM_Conversion c ON (p.C_UOM_ID=c.C_UOM_ID AND c.M_Product_ID IS NULL AND c.IsActive =''Y'' )
	WHERE C_UOM.C_UOM_ID=c.C_UOM_TO_ID AND @M_Product_ID@=p.M_Product_ID
))',TO_TIMESTAMP('2016-04-11 14:52:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','C_UOM PMM_Product Options','S',TO_TIMESTAMP('2016-04-11 14:52:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Apr 11, 2016 2:56 PM
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(
EXISTS (
	/* UOM is a default UOM and no product selected */
	SELECT 1 
	FROM C_UOM uu 
	WHERE C_UOM.C_UOM_ID=uu.C_UOM_ID AND IsActive =''Y'' AND IsDefault=''Y'' AND @M_Product_ID@=0
)
OR EXISTS (
	/* UOM is the products UOM */
	SELECT 1 
	FROM PMM_Product pmm 
		INNER JOIN M_Product p ON p.M_Product_ID=pmm.M_Product_ID
	WHERE C_UOM.C_UOM_ID=p.C_UOM_ID AND @PMM_Product_ID@=pmm.PMM_Product_ID
)
OR EXISTS (
	/* For the products UOM there is a conversion that is explicitly bound to the product */
	SELECT 1 
	FROM PMM_Product pmm 
		INNER JOIN M_Product p ON p.M_Product_ID=pmm.M_Product_ID
			INNER JOIN C_UOM_Conversion c ON (p.C_UOM_ID=c.C_UOM_ID AND p.M_PRODUCT_ID=c.M_Product_ID AND c.IsActive =''Y'' )
	WHERE C_UOM.C_UOM_ID=c.C_UOM_TO_ID AND @PMM_Product_ID@=pmm.PMM_Product_ID
)
OR EXISTS (
	/* For the products UOM there is a conversion that is not bound to any product explicitly */
	SELECT 1 
	FROM PMM_Product pmm 
		INNER JOIN M_Product p ON p.M_Product_ID=pmm.M_Product_ID
			INNER JOIN C_UOM_Conversion c ON (p.C_UOM_ID=c.C_UOM_ID AND c.M_Product_ID IS NULL AND c.IsActive =''Y'' )
	WHERE C_UOM.C_UOM_ID=c.C_UOM_TO_ID AND @PMM_Product_ID@=pmm.PMM_Product_ID
))',Updated=TO_TIMESTAMP('2016-04-11 14:56:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540329
;

-- Apr 11, 2016 2:56 PM
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540329,Updated=TO_TIMESTAMP('2016-04-11 14:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540920
;

