-- 2018-09-05T12:18:11.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-09-05 12:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565169
;

-- 2018-09-05T12:24:41.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Netto',Updated=TO_TIMESTAMP('2018-09-05 12:24:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547093
;

-- 2018-09-05T12:25:03.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Zeilensumme',Updated=TO_TIMESTAMP('2018-09-05 12:25:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547097
;

-- 2018-09-05T12:29:19.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,565168,0,293,540927,553747,'F',TO_TIMESTAMP('2018-09-05 12:29:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N',0,'No Price Conditions',220,0,0,TO_TIMESTAMP('2018-09-05 12:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-05T12:30:07.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,565169,0,293,540927,553748,'F',TO_TIMESTAMP('2018-09-05 12:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Discount Schema Break',230,0,0,TO_TIMESTAMP('2018-09-05 12:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-05T12:35:07.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2018-09-05 12:35:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553747
;

-- 2018-09-05T12:35:07.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2018-09-05 12:35:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548022
;

-- 2018-09-05T12:47:32.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='The value will contain the  AD_Colour.Name of the colour that will be set in C_OrderLine.NoPriceConditionsColor_ID if the C_OrderLine.M_DiscountSchemaBreak_ID is null.
Leave empty or set the value to "-" if the functionality is not needed', Updated=TO_TIMESTAMP('2018-09-05 12:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541204
;

-- 2018-09-05T12:48:12.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='The value will contain the  AD_Colour.Name of the colour that will be set in C_OrderLine.NoPriceConditionsColor_ID if the C_OrderLine.M_DiscountSchemaBreak_ID is null but temporary conditions flag is set.
Leave empty or set the value to "-" if the functionality is not needed.', Updated=TO_TIMESTAMP('2018-09-05 12:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541205
;

-- 2018-09-05T12:49:25.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='The value will contain the  AD_Color.Name of the colour that will be set in C_OrderLine.NoPriceConditionsColor_ID if the C_OrderLine.M_DiscountSchemaBreak_ID is null but temporary conditions flag is set.
Leave empty or set the value to "-" if the functionality is not needed.',Updated=TO_TIMESTAMP('2018-09-05 12:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541205
;

-- 2018-09-05T12:49:29.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='The value will contain the  AD_Color.Name of the colour that will be set in C_OrderLine.NoPriceConditionsColor_ID if the C_OrderLine.M_DiscountSchemaBreak_ID is null.
Leave empty or set the value to "-" if the functionality is not needed',Updated=TO_TIMESTAMP('2018-09-05 12:49:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541204
;

-- 2018-09-05T14:21:27.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.order.pricingconditions.process.PricingConditionsView_CopyRowToEditable',Updated=TO_TIMESTAMP('2018-09-05 14:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540954
;

-- 2018-09-05T14:21:58.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.order.pricingconditions.process.PricingConditionsView_SaveEditableRow',Updated=TO_TIMESTAMP('2018-09-05 14:21:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540955
;

-- 2018-09-05T14:22:22.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.order.pricingconditions.process.WEBUI_SalesOrder_PricingConditionsView_Launcher',Updated=TO_TIMESTAMP('2018-09-05 14:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540953
;

-- 2018-09-05T14:28:36.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Editierbare Kopie erstellen',Updated=TO_TIMESTAMP('2018-09-05 14:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540954
;

-- 2018-09-05T14:28:39.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-05 14:28:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Editierbare Kopie erstellen' WHERE AD_Process_ID=540954 AND AD_Language='de_CH'
;

-- 2018-09-05T14:28:57.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-05 14:28:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Make editable copy' WHERE AD_Process_ID=540954 AND AD_Language='en_US'
;

-- 2018-09-05T14:34:30.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Für zukünftige Nutzung abspeichern',Updated=TO_TIMESTAMP('2018-09-05 14:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540955
;

-- 2018-09-05T14:34:40.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-05 14:34:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Save for future use' WHERE AD_Process_ID=540955 AND AD_Language='en_US'
;

-- 2018-09-05T14:34:47.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-05 14:34:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Für zukünftige Nutzung abspeichern' WHERE AD_Process_ID=540955 AND AD_Language='de_CH'
;

-- 2018-09-05T14:56:51.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544776,0,TO_TIMESTAMP('2018-09-05 14:56:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Zum Geschäftspartner ist kein Rabattschema/Preiskondition hinterlegt','I',TO_TIMESTAMP('2018-09-05 14:56:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.order.pricingconditions.C_BPartnerHasNoPricingConditions')
;

-- 2018-09-05T14:56:51.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544776 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-09-05T14:57:45.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-05 14:57:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544776 AND AD_Language='de_CH'
;

-- 2018-09-05T14:58:22.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-05 14:58:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='The business partner has no pricing-condition/discount-schema' WHERE AD_Message_ID=544776 AND AD_Language='en_US'
;

-- 2018-09-05T14:58:30.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-05 14:58:30','YYYY-MM-DD HH24:MI:SS'),MsgText='The business partner has no discount-schema/pricing-condition' WHERE AD_Message_ID=544776 AND AD_Language='en_US'
;

