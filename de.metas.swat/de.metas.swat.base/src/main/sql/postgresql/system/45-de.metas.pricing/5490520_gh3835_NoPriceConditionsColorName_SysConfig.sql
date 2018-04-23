-- 2018-04-12T16:28:25.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541204,'S',TO_TIMESTAMP('2018-04-12 16:28:24','YYYY-MM-DD HH24:MI:SS'),100,'The value will contain the  AD_Color_ID that will be set in C_OrderLine.NoPriceConditionsColor_ID if the C_OrderLine.M_DiscountSchemaBreak_ID is null.
Leave empty or set the value to "-" if the functionality is not needed','D','Y','de.metas.order.NoPriceConditionsColorName',TO_TIMESTAMP('2018-04-12 16:28:24','YYYY-MM-DD HH24:MI:SS'),100,'-')
;



-- 2018-04-13T10:20:55.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='The value will contain the  name of the colour that will be set in C_OrderLine.NoPriceConditionsColor_ID if the C_OrderLine.M_DiscountSchemaBreak_ID is null.
Leave empty or set the value to "-" if the functionality is not needed', Value='-',Updated=TO_TIMESTAMP('2018-04-13 10:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541204
;

