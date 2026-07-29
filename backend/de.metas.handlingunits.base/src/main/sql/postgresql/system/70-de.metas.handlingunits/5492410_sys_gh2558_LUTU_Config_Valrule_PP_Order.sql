-- 2018-05-03T10:06:35.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540396,'M_HU_LUTU_Configuration_ID.M_Product_ID=@M_Product_ID/-1@
',TO_TIMESTAMP('2018-05-03 10:06:35','YYYY-MM-DD HH24:MI:SS'),100,'Those M_HU_LUTU_Configuration_ID which are eligible for product','de.metas.handlingunits','Y','M_HU_LUTU_Configuration_ForProductPricing','S',TO_TIMESTAMP('2018-05-03 10:06:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-03T10:13:06.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='M_HU_LUTU_Configuration_ForProduct',Updated=TO_TIMESTAMP('2018-05-03 10:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540396
;

-- 2018-05-03T10:13:11.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540396,Updated=TO_TIMESTAMP('2018-05-03 10:13:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550971
;

-- 2018-05-03T10:14:50.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU_LUTU_Configuration.M_Product_ID=@M_Product_ID/-1@
',Updated=TO_TIMESTAMP('2018-05-03 10:14:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540396
;

