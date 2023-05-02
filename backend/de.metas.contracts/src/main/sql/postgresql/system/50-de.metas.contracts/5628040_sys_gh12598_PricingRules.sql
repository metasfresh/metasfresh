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


-- 2022-02-27T20:48:46.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission''',Updated=TO_TIMESTAMP('2022-02-27 22:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563597
;

-- 2022-02-27T23:03:10.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-02-28 01:03:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=545427
;

-- 2022-02-27T23:03:53.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-02-28 01:03:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=548141
;

