
-- 2019-11-25T09:36:48.536Z
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
))',Updated=TO_TIMESTAMP('2019-11-25 10:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=210
;

-- 2019-11-25T09:37:55.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='C_UOM Product options including ForTU-UOMs',Updated=TO_TIMESTAMP('2019-11-25 10:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=210
;

-- 2019-11-25T09:38:33.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540472,'',TO_TIMESTAMP('2019-11-25 10:38:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_UOM Product options excluding ForTU-UOMs','C',TO_TIMESTAMP('2019-11-25 10:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-25T09:39:06.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule_Included (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,AD_Val_Rule_Included_ID,Created,CreatedBy,EntityType,Included_Val_Rule_ID,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540472,540031,TO_TIMESTAMP('2019-11-25 10:39:05','YYYY-MM-DD HH24:MI:SS'),100,'D',210,'Y',20,TO_TIMESTAMP('2019-11-25 10:39:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-25T09:42:42.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540473,'X12DE355 NOT IN (''TU'', ''COLI'')',TO_TIMESTAMP('2019-11-25 10:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Needs to be kept in sync with de.metas.uom.IUOMDAO.isUOMForTUs(UomId)','D','Y','C_UOM excluding ForTU-UOMs','S',TO_TIMESTAMP('2019-11-25 10:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-25T09:42:53.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_UOM.X12DE355 NOT IN (''TU'', ''COLI'')',Updated=TO_TIMESTAMP('2019-11-25 10:42:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540473
;

-- 2019-11-25T09:43:11.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='C_UOM excluding UOMs for TUs',Updated=TO_TIMESTAMP('2019-11-25 10:43:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540473
;

-- 2019-11-25T09:43:39.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='C_UOM Product options including UOMs for TUs',Updated=TO_TIMESTAMP('2019-11-25 10:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=210
;

-- 2019-11-25T09:44:08.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule_Included (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,AD_Val_Rule_Included_ID,Created,CreatedBy,Included_Val_Rule_ID,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540472,540032,TO_TIMESTAMP('2019-11-25 10:44:08','YYYY-MM-DD HH24:MI:SS'),100,540473,'Y',10,TO_TIMESTAMP('2019-11-25 10:44:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-25T09:44:12.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule_Included SET EntityType='D',Updated=TO_TIMESTAMP('2019-11-25 10:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_Included_ID=540032
;
