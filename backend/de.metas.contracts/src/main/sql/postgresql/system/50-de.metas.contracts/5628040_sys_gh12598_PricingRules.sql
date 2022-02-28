-- 2022-02-27T23:09:25.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PricingRule (AD_Client_ID,AD_Org_ID,C_PricingRule_ID,Classname,Created,CreatedBy,EntityType,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,0,540016,'de.metas.contracts.callorder.price.CallOrderContractLinePricingRule',TO_TIMESTAMP('2022-02-28 01:09:25','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Call Order Contract',10,TO_TIMESTAMP('2022-02-28 01:09:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-27T23:09:37.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_PricingRule SET EntityType='D',Updated=TO_TIMESTAMP('2022-02-28 01:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_PricingRule_ID=540016
;

-- 2022-02-27T23:10:28.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_PricingRule (AD_Client_ID,AD_Org_ID,C_PricingRule_ID,Classname,Created,CreatedBy,EntityType,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (1000000,0,540017,'de.metas.contracts.callorder.price.CallOrderPricingRule',TO_TIMESTAMP('2022-02-28 01:10:28','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Call Order',15,TO_TIMESTAMP('2022-02-28 01:10:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-27T23:10:41.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_PricingRule SET EntityType='D',Updated=TO_TIMESTAMP('2022-02-28 01:10:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_PricingRule_ID=540017
;

