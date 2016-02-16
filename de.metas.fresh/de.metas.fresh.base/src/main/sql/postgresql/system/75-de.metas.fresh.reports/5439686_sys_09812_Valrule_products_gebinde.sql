-- 12.02.2016 16:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540317,TO_TIMESTAMP('2016-02-12 16:51:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','M_Product_Gebinde','S',TO_TIMESTAMP('2016-02-12 16:51:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.02.2016 16:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXIST (SELECT 1 FROM M_Product_Category pc WHERE pc.M_Product_Category_ID = M_Product.M_Product_Category_ID AND pc.isTradingUnit = ''Y'')',Updated=TO_TIMESTAMP('2016-02-12 16:54:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540317
;

-- 12.02.2016 16:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540317,Updated=TO_TIMESTAMP('2016-02-12 16:54:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540895
;

-- 12.02.2016 16:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT 1 FROM M_Product_Category pc WHERE pc.M_Product_Category_ID = M_Product.M_Product_Category_ID AND pc.isTradingUnit = ''Y'')',Updated=TO_TIMESTAMP('2016-02-12 16:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540317
;

