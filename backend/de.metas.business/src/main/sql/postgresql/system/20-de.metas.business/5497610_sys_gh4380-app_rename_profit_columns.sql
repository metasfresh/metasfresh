-- 2018-07-16T12:46:55.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ProfitPriceActual', Description='Effektiver Preis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung.', Name='Ertrag netto', PrintName='Ertrag netto',Updated=TO_TIMESTAMP('2018-07-16 12:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544089
;

-- 2018-07-16T12:46:55.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProfitPriceActual', Name='Ertrag netto', Description='Effektiver Preis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung.', Help=NULL WHERE AD_Element_ID=544089
;

-- 2018-07-16T12:46:55.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProfitPriceActual', Name='Ertrag netto', Description='Effektiver Preis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung.', Help=NULL, AD_Element_ID=544089 WHERE UPPER(ColumnName)='PROFITPRICEACTUAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-16T12:46:55.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProfitPriceActual', Name='Ertrag netto', Description='Effektiver Preis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung.', Help=NULL WHERE AD_Element_ID=544089 AND IsCentrallyMaintained='Y'
;

-- 2018-07-16T12:46:55.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ertrag netto', Description='Effektiver Preis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544089) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544089)
;

-- 2018-07-16T12:46:55.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ertrag netto', Name='Ertrag netto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544089)
;

SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine RENAME COLUMN PriceGrossProfit TO ProfitPriceActual');

-- 2018-07-16T12:49:07.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ProfitPercent', Description='Differenz zwischen VK Ertrag und EK Ertrag.', EntityType='de.metas.order',Updated=TO_TIMESTAMP('2018-07-16 12:49:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544177
;

-- 2018-07-16T12:49:07.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProfitPercent', Name='Marge %', Description='Differenz zwischen VK Ertrag und EK Ertrag.', Help=NULL WHERE AD_Element_ID=544177
;

-- 2018-07-16T12:49:07.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProfitPercent', Name='Marge %', Description='Differenz zwischen VK Ertrag und EK Ertrag.', Help=NULL, AD_Element_ID=544177 WHERE UPPER(ColumnName)='PROFITPERCENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-16T12:49:07.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProfitPercent', Name='Marge %', Description='Differenz zwischen VK Ertrag und EK Ertrag.', Help=NULL WHERE AD_Element_ID=544177 AND IsCentrallyMaintained='Y'
;

-- 2018-07-16T12:49:07.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Marge %', Description='Differenz zwischen VK Ertrag und EK Ertrag.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544177) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544177)
;

-- 2018-07-16T12:49:20.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN ProfitPercent NUMERIC')
;

-- 2018-07-16T12:51:33.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560676,565104,0,187,0,TO_TIMESTAMP('2018-07-16 12:51:32','YYYY-MM-DD HH24:MI:SS'),100,'Differenz zwischen VK Ertrag und EK Ertrag.',0,'de.metas.order',0,'Y','Y','Y','N','N','N','N','N','Marge %',270,260,0,1,1,TO_TIMESTAMP('2018-07-16 12:51:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-16T12:51:33.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-07-16T12:51:50.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560174,565105,0,187,0,TO_TIMESTAMP('2018-07-16 12:51:50','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung.',0,'de.metas.order',0,'Y','Y','Y','N','N','N','N','N','Ertrag netto',280,270,0,1,1,TO_TIMESTAMP('2018-07-16 12:51:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-16T12:51:50.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-07-16T12:54:49.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ProfitPurchasePriceActual', Description='Effektiver Einkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Name='EK Ertrag netto', PrintName='EK Ertrag netto',Updated=TO_TIMESTAMP('2018-07-16 12:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544092
;

-- 2018-07-16T12:54:49.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProfitPurchasePriceActual', Name='EK Ertrag netto', Description='Effektiver Einkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='' WHERE AD_Element_ID=544092
;

-- 2018-07-16T12:54:49.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProfitPurchasePriceActual', Name='EK Ertrag netto', Description='Effektiver Einkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='', AD_Element_ID=544092 WHERE UPPER(ColumnName)='PROFITPURCHASEPRICEACTUAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-16T12:54:49.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProfitPurchasePriceActual', Name='EK Ertrag netto', Description='Effektiver Einkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='' WHERE AD_Element_ID=544092 AND IsCentrallyMaintained='Y'
;

-- 2018-07-16T12:54:49.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='EK Ertrag netto', Description='Effektiver Einkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544092) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544092)
;

-- 2018-07-16T12:54:49.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='EK Ertrag netto', Name='EK Ertrag netto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544092)
;

SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate RENAME COLUMN PurchasePriceActual TO ProfitPurchasePriceActual')
;
-- 2018-07-16T12:57:11.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Name='VK Ertrag netto', PrintName='VK Ertrag netto',Updated=TO_TIMESTAMP('2018-07-16 12:57:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544093
;

-- 2018-07-16T12:57:11.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CustomerPriceGrossProfit', Name='VK Ertrag netto', Description='Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='' WHERE AD_Element_ID=544093
;

-- 2018-07-16T12:57:11.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerPriceGrossProfit', Name='VK Ertrag netto', Description='Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='', AD_Element_ID=544093 WHERE UPPER(ColumnName)='CUSTOMERPRICEGROSSPROFIT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-16T12:57:11.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerPriceGrossProfit', Name='VK Ertrag netto', Description='Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='' WHERE AD_Element_ID=544093 AND IsCentrallyMaintained='Y'
;

-- 2018-07-16T12:57:11.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='VK Ertrag netto', Description='Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544093) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544093)
;

-- 2018-07-16T12:57:11.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='VK Ertrag netto', Name='VK Ertrag netto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544093)
;

-- 2018-07-16T12:57:29.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ProfitSalesPriceActual',Updated=TO_TIMESTAMP('2018-07-16 12:57:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544093
;

-- 2018-07-16T12:57:29.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProfitSalesPriceActual', Name='VK Ertrag netto', Description='Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='' WHERE AD_Element_ID=544093
;

-- 2018-07-16T12:57:29.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProfitSalesPriceActual', Name='VK Ertrag netto', Description='Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='', AD_Element_ID=544093 WHERE UPPER(ColumnName)='PROFITSALESPRICEACTUAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-16T12:57:29.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProfitSalesPriceActual', Name='VK Ertrag netto', Description='Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung', Help='' WHERE AD_Element_ID=544093 AND IsCentrallyMaintained='Y'
;

SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate RENAME COLUMN CustomerPriceGrossProfit TO ProfitSalesPriceActual')
;
-- 2018-07-16T13:23:39.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=519, ColumnName='PriceActual', Description='Effektiver Preis', Help='Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.', Name='Einzelpreis',Updated=TO_TIMESTAMP('2018-07-16 13:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560358
;

-- 2018-07-16T13:23:39.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einzelpreis', Description='Effektiver Preis', Help='Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.' WHERE AD_Column_ID=560358
;

SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate RENAME COLUMN ProfitPurchasePriceActual TO PriceActual');

SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate RENAME COLUMN pricegrossprofit TO ProfitPurchasePriceActual');
-- 2018-07-16T16:23:53.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Mandatory, unless AD_Issue_ID>0',Updated=TO_TIMESTAMP('2018-07-16 16:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559005
;

-- 2018-07-16T16:23:58.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Mandatory, unless AD_Issue_ID>0',Updated=TO_TIMESTAMP('2018-07-16 16:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559012
;

-- 2018-07-16T16:24:04.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Mandatory, unless AD_Issue_ID>0',Updated=TO_TIMESTAMP('2018-07-16 16:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559017
;

-- 2018-07-16T16:26:02.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Mandatory, unless AD_Issue_ID>0; may be equal to "NO_REMOTE_PURCHASE_ID"',Updated=TO_TIMESTAMP('2018-07-16 16:26:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559018
;

