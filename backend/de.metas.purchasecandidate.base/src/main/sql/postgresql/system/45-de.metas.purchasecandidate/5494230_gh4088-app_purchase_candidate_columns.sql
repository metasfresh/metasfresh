-- 2018-05-22T14:26:02.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560190,519,0,12,540861,'N','PriceActual',TO_TIMESTAMP('2018-05-22 14:26:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Effektiver Preis','de.metas.purchasecandidate',14,'Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Einzelpreis',0,0,TO_TIMESTAMP('2018-05-22 14:26:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-05-22T14:26:02.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560190 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-22T14:26:06.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN PriceActual NUMERIC')
;

-- 2018-05-22T14:39:03.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560191,193,0,19,540861,'N','C_Currency_ID',TO_TIMESTAMP('2018-05-22 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Währung für diesen Eintrag','de.metas.purchasecandidate',10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Währung',0,0,TO_TIMESTAMP('2018-05-22 14:39:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-05-22T14:39:03.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560191 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-22T14:39:07.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN C_Currency_ID NUMERIC(10)')
;

-- 2018-05-22T14:39:07.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT CCurrency_CPurchaseCandidate FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- 2018-05-23T08:43:37.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544092,0,'SalesPriceActual',TO_TIMESTAMP('2018-05-23 08:43:37','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Einkaufspreis','de.metas.purchasecandidate','','Y','EK Preis netto','EK Preis netto',TO_TIMESTAMP('2018-05-23 08:43:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-23T08:43:37.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544092 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-23T08:43:43.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-23 08:43:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544092 AND AD_Language='de_CH'
;

-- 2018-05-23T08:43:43.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544092,'de_CH') 
;

-- 2018-05-23T08:44:20.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-23 08:44:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Purchase net price',PrintName='Purchase net price',Description='' WHERE AD_Element_ID=544092 AND AD_Language='en_US'
;

-- 2018-05-23T08:44:20.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544092,'en_US') 
;

-- 2018-05-23T08:44:50.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544093,0,'PurchasePriceActual',TO_TIMESTAMP('2018-05-23 08:44:50','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Verkaufspreis','de.metas.purchasecandidate','','Y','VK Preis netto','VK Preis netto',TO_TIMESTAMP('2018-05-23 08:44:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-23T08:44:50.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544093 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-23T08:44:59.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-23 08:44:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544093 AND AD_Language='de_CH'
;

-- 2018-05-23T08:44:59.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544093,'de_CH') 
;

-- 2018-05-23T08:45:25.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-23 08:45:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sales net price',PrintName='Sales net price',Description='' WHERE AD_Element_ID=544093 AND AD_Language='en_US'
;

-- 2018-05-23T08:45:25.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544093,'en_US') 
;

-- 2018-05-23T09:08:39.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Help='', ColumnName='SalesPriceActual', Description='Effektiver Einkaufspreis', Name='EK Preis netto', AD_Element_ID=544092,Updated=TO_TIMESTAMP('2018-05-23 09:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560190
;

-- 2018-05-23T09:08:39.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='EK Preis netto', Description='Effektiver Einkaufspreis', Help='' WHERE AD_Column_ID=560190
;

-- 2018-05-23T09:08:42.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN SalesPriceActual NUMERIC')
;

-- 2018-05-23T09:08:58.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,CreatedBy,AD_Client_ID,IsActive,Created,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (12,14,0,'N','N','N','N',0,100,0,'Y',TO_TIMESTAMP('2018-05-23 09:08:57','YYYY-MM-DD HH24:MI:SS'),'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-05-23 09:08:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540861,'N','N','','PurchasePriceActual',560194,'N','N','N','N','N','N','N','N','Effektiver Verkaufspreis',0,0,'VK Preis netto',544093,'de.metas.purchasecandidate','N','N')
;

-- 2018-05-23T09:08:58.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560194 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-23T09:09:02.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN PurchasePriceActual NUMERIC')
;

/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate DROP COLUMN PriceActual')
;

-- 2018-05-23T09:55:12.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PurchaseGrossProfitPrice', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Name='Kd-Rohertragspreis', PrintName='Kd-Rohertragspreis',Updated=TO_TIMESTAMP('2018-05-23 09:55:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544093
;

-- 2018-05-23T09:55:12.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PurchaseGrossProfitPrice', Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE AD_Element_ID=544093
;

-- 2018-05-23T09:55:12.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PurchaseGrossProfitPrice', Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='', AD_Element_ID=544093 WHERE UPPER(ColumnName)='PURCHASEGROSSPROFITPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-23T09:55:12.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PurchaseGrossProfitPrice', Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE AD_Element_ID=544093 AND IsCentrallyMaintained='Y'
;

-- 2018-05-23T09:55:12.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544093) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544093)
;

-- 2018-05-23T09:55:12.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kd-Rohertragspreis', Name='Kd-Rohertragspreis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544093)
;

-- 2018-05-23T09:55:20.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='CustomerGrossProfitPrice',Updated=TO_TIMESTAMP('2018-05-23 09:55:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544093
;

-- 2018-05-23T09:55:20.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CustomerGrossProfitPrice', Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE AD_Element_ID=544093
;

-- 2018-05-23T09:55:20.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerGrossProfitPrice', Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='', AD_Element_ID=544093 WHERE UPPER(ColumnName)='CUSTOMERGROSSPROFITPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-23T09:55:20.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerGrossProfitPrice', Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE AD_Element_ID=544093 AND IsCentrallyMaintained='Y'
;

-- 2018-05-23T09:55:53.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN CustomerGrossProfitPrice NUMERIC')
;

/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate DROP COLUMN SalesPriceActual')
;-- 2018-05-23T10:07:53.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='GrossProfitPrice',Updated=TO_TIMESTAMP('2018-05-23 10:07:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544089
;

-- 2018-05-23T10:07:53.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='GrossProfitPrice', Name='Rohertragspreis', Description='Endpreis pro Einheit nach Abzug des erwarteten Rohertrages (Skonto, Rückvergütung usw).', Help=NULL WHERE AD_Element_ID=544089
;

-- 2018-05-23T10:07:53.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GrossProfitPrice', Name='Rohertragspreis', Description='Endpreis pro Einheit nach Abzug des erwarteten Rohertrages (Skonto, Rückvergütung usw).', Help=NULL, AD_Element_ID=544089 WHERE UPPER(ColumnName)='GROSSPROFITPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-23T10:07:53.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GrossProfitPrice', Name='Rohertragspreis', Description='Endpreis pro Einheit nach Abzug des erwarteten Rohertrages (Skonto, Rückvergütung usw).', Help=NULL WHERE AD_Element_ID=544089 AND IsCentrallyMaintained='Y'
;

-- 2018-05-23T10:08:05.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PriceGrossProfit',Updated=TO_TIMESTAMP('2018-05-23 10:08:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544089
;

-- 2018-05-23T10:08:05.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PriceGrossProfit', Name='Rohertragspreis', Description='Endpreis pro Einheit nach Abzug des erwarteten Rohertrages (Skonto, Rückvergütung usw).', Help=NULL WHERE AD_Element_ID=544089
;

-- 2018-05-23T10:08:05.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceGrossProfit', Name='Rohertragspreis', Description='Endpreis pro Einheit nach Abzug des erwarteten Rohertrages (Skonto, Rückvergütung usw).', Help=NULL, AD_Element_ID=544089 WHERE UPPER(ColumnName)='PRICEGROSSPROFIT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-23T10:08:05.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceGrossProfit', Name='Rohertragspreis', Description='Endpreis pro Einheit nach Abzug des erwarteten Rohertrages (Skonto, Rückvergütung usw).', Help=NULL WHERE AD_Element_ID=544089 AND IsCentrallyMaintained='Y'
;

-- 2018-05-23T10:08:34.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='CustomerPriceGrossProfit',Updated=TO_TIMESTAMP('2018-05-23 10:08:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544093
;

-- 2018-05-23T10:08:34.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CustomerPriceGrossProfit', Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE AD_Element_ID=544093
;

-- 2018-05-23T10:08:34.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerPriceGrossProfit', Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='', AD_Element_ID=544093 WHERE UPPER(ColumnName)='CUSTOMERPRICEGROSSPROFIT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-23T10:08:34.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CustomerPriceGrossProfit', Name='Kd-Rohertragspreis', Description='Effektiver Verkaufspreis minus Skonto und Rückerstattung', Help='' WHERE AD_Element_ID=544093 AND IsCentrallyMaintained='Y'
;

/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate DROP COLUMN CustomerGrossProfitPrice')
;
-- 2018-05-23T10:10:54.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN CustomerPriceGrossProfit NUMERIC')
;

/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate DROP COLUMN IF EXISTS SalesPriceActual')
;
-- 2018-05-23T10:16:19.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PurchasePriceActual', Description='Effektiver Verkaufspreis', Name='VK Preis netto', PrintName='VK Preis netto',Updated=TO_TIMESTAMP('2018-05-23 10:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544092
;

-- 2018-05-23T10:16:19.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PurchasePriceActual', Name='VK Preis netto', Description='Effektiver Verkaufspreis', Help='' WHERE AD_Element_ID=544092
;

-- 2018-05-23T10:16:19.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PurchasePriceActual', Name='VK Preis netto', Description='Effektiver Verkaufspreis', Help='', AD_Element_ID=544092 WHERE UPPER(ColumnName)='PURCHASEPRICEACTUAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-23T10:16:19.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PurchasePriceActual', Name='VK Preis netto', Description='Effektiver Verkaufspreis', Help='' WHERE AD_Element_ID=544092 AND IsCentrallyMaintained='Y'
;

-- 2018-05-23T10:16:19.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='VK Preis netto', Description='Effektiver Verkaufspreis', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544092) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544092)
;

-- 2018-05-23T10:16:19.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='VK Preis netto', Name='VK Preis netto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544092)
;

-- 2018-05-23T10:16:28.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-23 10:16:28','YYYY-MM-DD HH24:MI:SS'),Name='VK Preis netto',PrintName='VK Preis netto',Description='Effektiver Verkaufspreis' WHERE AD_Element_ID=544092 AND AD_Language='de_CH'
;

-- 2018-05-23T10:16:28.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544092,'de_CH') 
;

