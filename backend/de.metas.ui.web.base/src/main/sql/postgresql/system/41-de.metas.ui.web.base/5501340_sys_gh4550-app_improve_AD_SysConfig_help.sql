-- 2018-09-12T17:03:14.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='The value will contain the  AD_Color.Name of the color that will be set in C_OrderLine.NoPriceConditionsColor_ID if the C_OrderLine.M_DiscountSchemaBreak_ID is null.
Leave empty or set the value to "-" if the functionality is not needed.
Hint: currently available AD_Color.Names include Rot, Gelb, Gruen and a few others.',Updated=TO_TIMESTAMP('2018-09-12 17:03:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541204
;

-- 2018-09-12T17:03:21.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='The value will contain the  AD_Color.Name of the colour that will be set in C_OrderLine.NoPriceConditionsColor_ID if the C_OrderLine.M_DiscountSchemaBreak_ID is null but temporary conditions flag is set.
Leave empty or set the value to "-" if the functionality is not needed.
Hint: currently available AD_Color.Names include Rot, Gelb, Gruen and a few others.',Updated=TO_TIMESTAMP('2018-09-12 17:03:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541205
;

