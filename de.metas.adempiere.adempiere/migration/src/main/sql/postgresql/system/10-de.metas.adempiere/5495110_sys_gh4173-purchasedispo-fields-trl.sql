-- 2018-06-05T07:28:49.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='VK Netto',Updated=TO_TIMESTAMP('2018-06-05 07:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544093
;

-- 2018-06-05T07:28:49.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='VK Netto', Name='Kd-Rohertragspreis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544093)
;

-- 2018-06-05T07:29:30.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Effektiver Einkaufspreis', Name='EK Netto', PrintName='EK Preis netto',Updated=TO_TIMESTAMP('2018-06-05 07:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544092
;

-- 2018-06-05T07:29:30.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PurchasePriceActual', Name='EK Netto', Description='Effektiver Einkaufspreis', Help='' WHERE AD_Element_ID=544092
;

-- 2018-06-05T07:29:30.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PurchasePriceActual', Name='EK Netto', Description='Effektiver Einkaufspreis', Help='', AD_Element_ID=544092 WHERE UPPER(ColumnName)='PURCHASEPRICEACTUAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-06-05T07:29:30.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PurchasePriceActual', Name='EK Netto', Description='Effektiver Einkaufspreis', Help='' WHERE AD_Element_ID=544092 AND IsCentrallyMaintained='Y'
;

-- 2018-06-05T07:29:30.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='EK Netto', Description='Effektiver Einkaufspreis', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544092) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544092)
;

-- 2018-06-05T07:29:30.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='EK Preis netto', Name='EK Netto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544092)
;

-- 2018-06-05T07:33:39.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544743,0,TO_TIMESTAMP('2018-06-05 07:33:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','Marge %','I',TO_TIMESTAMP('2018-06-05 07:33:39','YYYY-MM-DD HH24:MI:SS'),100,'PercentGrossProfit')
;

-- 2018-06-05T07:33:39.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544743 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-06-05T07:40:17.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='VK Netto',Updated=TO_TIMESTAMP('2018-06-05 07:40:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544093
;

-- 2018-06-05T07:40:17.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CustomerPriceGrossProfit', Name='VK Netto', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE AD_Element_ID=544093
;

-- 2018-06-05T07:40:17.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerPriceGrossProfit', Name='VK Netto', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='', AD_Element_ID=544093 WHERE UPPER(ColumnName)='CUSTOMERPRICEGROSSPROFIT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-06-05T07:40:17.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerPriceGrossProfit', Name='VK Netto', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE AD_Element_ID=544093 AND IsCentrallyMaintained='Y'
;

-- 2018-06-05T07:40:17.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='VK Netto', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544093) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544093)
;

-- 2018-06-05T07:40:17.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='VK Netto', Name='VK Netto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544093)
;

-- 2018-06-05T07:43:07.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-05 07:43:07','YYYY-MM-DD HH24:MI:SS'),Name='Sales net',PrintName='Sales net' WHERE AD_Element_ID=544093 AND AD_Language='en_US'
;

-- 2018-06-05T07:43:07.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544093,'en_US') 
;

-- 2018-06-05T07:43:30.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-05 07:43:30','YYYY-MM-DD HH24:MI:SS'),Name='Purchase net',PrintName='Purchase net' WHERE AD_Element_ID=544092 AND AD_Language='en_US'
;

-- 2018-06-05T07:43:30.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544092,'en_US') 
;

-- 2018-06-05T07:43:44.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-05 07:43:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Margin %' WHERE AD_Message_ID=544743 AND AD_Language='en_US'
;

