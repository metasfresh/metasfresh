-- 2018-08-08T07:01:06.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544199,0,'RefundPercent',TO_TIMESTAMP('2018-08-08 07:01:06','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.contracts','','Y','Rückvergütung %','Rückvergütung %',TO_TIMESTAMP('2018-08-08 07:01:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T07:01:06.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544199 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-08T07:01:10.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544199 AND AD_Language='de_CH'
;

-- 2018-08-08T07:01:10.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544199,'de_CH') 
;

-- 2018-08-08T07:01:21.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Refund %',PrintName='Refund %' WHERE AD_Element_ID=544199 AND AD_Language='en_US'
;

-- 2018-08-08T07:01:21.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544199,'en_US') 
;

-- 2018-08-08T07:02:02.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Help='', ColumnName='RefundPercent', Description='', Name='Rückvergütung %', AD_Element_ID=544199,Updated=TO_TIMESTAMP('2018-08-08 07:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560133
;

-- 2018-08-08T07:02:02.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rückvergütung %', Description='', Help='' WHERE AD_Column_ID=560133
;

SELECT db_alter_table('C_Flatrate_RefundConfig','ALTER TABLE C_Flatrate_RefundConfig RENAME COLUMN Percent TO RefundPercent;');

-- 2018-08-08T07:03:25.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544200,0,'RefundAmt',TO_TIMESTAMP('2018-08-08 07:03:25','YYYY-MM-DD HH24:MI:SS'),100,'Rückvergütungsbetrag pro Produkt-Einheit','de.metas.contracts','','Y','Rückvergütung Betrag','Rückvergütung Betrag',TO_TIMESTAMP('2018-08-08 07:03:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T07:03:25.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544200 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-08T07:03:35.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Rückvergütungsbetrag', PrintName='Rückvergütungsbetrag',Updated=TO_TIMESTAMP('2018-08-08 07:03:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544200
;

-- 2018-08-08T07:03:35.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RefundAmt', Name='Rückvergütungsbetrag', Description='Rückvergütungsbetrag pro Produkt-Einheit', Help='' WHERE AD_Element_ID=544200
;

-- 2018-08-08T07:03:35.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RefundAmt', Name='Rückvergütungsbetrag', Description='Rückvergütungsbetrag pro Produkt-Einheit', Help='', AD_Element_ID=544200 WHERE UPPER(ColumnName)='REFUNDAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-08T07:03:35.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RefundAmt', Name='Rückvergütungsbetrag', Description='Rückvergütungsbetrag pro Produkt-Einheit', Help='' WHERE AD_Element_ID=544200 AND IsCentrallyMaintained='Y'
;

-- 2018-08-08T07:03:35.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rückvergütungsbetrag', Description='Rückvergütungsbetrag pro Produkt-Einheit', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544200) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544200)
;

-- 2018-08-08T07:03:35.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rückvergütungsbetrag', Name='Rückvergütungsbetrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544200)
;

-- 2018-08-08T07:04:03.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Refund amount',PrintName='Refund amount',Description='Refund amount per product unit' WHERE AD_Element_ID=544200 AND AD_Language='en_US'
;

-- 2018-08-08T07:04:03.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544200,'en_US') 
;

-- 2018-08-08T07:04:15.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Rückvergütungsbetrag',PrintName='Rückvergütungsbetrag' WHERE AD_Element_ID=544200 AND AD_Language='de_CH'
;

-- 2018-08-08T07:04:15.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544200,'de_CH') 
;

-- 2018-08-08T07:08:06.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,CreatedBy,AD_Client_ID,IsActive,Created,IsUpdateable,ValueMin,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (12,10,0,'N','N','N','N',0,100,0,'Y',TO_TIMESTAMP('2018-08-08 07:08:06','YYYY-MM-DD HH24:MI:SS'),'Y','0.00','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-08-08 07:08:06','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540980,'N','','RefundAmt',560752,'N','Y','N','N','N','N','N','N','Rückvergütungsbetrag pro Produkt-Einheit',0,0,'Rückvergütungsbetrag',544200,'de.metas.contracts','N','N')
;

-- 2018-08-08T07:08:06.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-08T07:08:15.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_RefundConfig','ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN RefundAmt NUMERIC')
;

-- 2018-08-08T07:08:25.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ValueMin='0.00',Updated=TO_TIMESTAMP('2018-08-08 07:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560133
;

-- 2018-08-08T07:10:16.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544201,0,'RefundBase',TO_TIMESTAMP('2018-08-08 07:10:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Vergütung basiert auf','Vergütung basiert auf',TO_TIMESTAMP('2018-08-08 07:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T07:10:16.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544201 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-08T07:10:21.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544201 AND AD_Language='de_CH'
;

-- 2018-08-08T07:10:21.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544201,'de_CH') 
;

-- 2018-08-08T07:10:32.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Refund based on',PrintName='Refund based on' WHERE AD_Element_ID=544201 AND AD_Language='en_US'
;

-- 2018-08-08T07:10:32.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544201,'en_US') 
;

-- 2018-08-08T07:10:36.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544201 AND AD_Language='en_US'
;

-- 2018-08-08T07:10:36.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544201,'en_US') 
;

-- 2018-08-08T07:11:08.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540902,TO_TIMESTAMP('2018-08-08 07:11:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','RefundBase',TO_TIMESTAMP('2018-08-08 07:11:08','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-08-08T07:11:08.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540902 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-08-08T07:17:52.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540902,541667,TO_TIMESTAMP('2018-08-08 07:17:52','YYYY-MM-DD HH24:MI:SS'),100,'Die Rückvergütung ist ein Prozentsatz des jeweiligen Netto-Rechnungsbetrages','de.metas.contracts','Y','Prozentwert',TO_TIMESTAMP('2018-08-08 07:17:52','YYYY-MM-DD HH24:MI:SS'),100,'P','percentage')
;

-- 2018-08-08T07:17:52.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541667 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-08-08T07:18:01.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y' WHERE AD_Ref_List_ID=541667 AND AD_Language='de_CH'
;

-- 2018-08-08T07:18:44.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Description='The refund ist computed as a percentage of the invoicable net amount.' WHERE AD_Ref_List_ID=541667 AND AD_Language='en_US'
;

-- 2018-08-08T07:18:48.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Die Rückvergütung ist ein Prozentsatz des jeweiligen Netto-Rechnungsbetrages.' WHERE AD_Ref_List_ID=541667 AND AD_Language='nl_NL'
;

-- 2018-08-08T07:18:51.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Die Rückvergütung ist ein Prozentsatz des jeweiligen Netto-Rechnungsbetrages.' WHERE AD_Ref_List_ID=541667 AND AD_Language='de_CH'
;

-- 2018-08-08T07:20:28.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540902,541668,TO_TIMESTAMP('2018-08-08 07:20:28','YYYY-MM-DD HH24:MI:SS'),100,'Die Rückvergütung ist fixer Betrag pro Stück des abregechneten Produkts.','de.metas.contracts','Y','Betrag',TO_TIMESTAMP('2018-08-08 07:20:28','YYYY-MM-DD HH24:MI:SS'),100,'F','amount')
;

-- 2018-08-08T07:20:28.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541668 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-08-08T07:20:35.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y' WHERE AD_Ref_List_ID=541668 AND AD_Language='de_CH'
;

-- 2018-08-08T07:21:28.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Description='The refund ist computed as a fix amount per invoiced product.' WHERE AD_Ref_List_ID=541668 AND AD_Language='en_US'
;

-- 2018-08-08T07:21:54.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,CreatedBy,AD_Client_ID,IsActive,Created,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (17,'P',1,0,'N','N','N','N',0,100,0,'Y',TO_TIMESTAMP('2018-08-08 07:21:53','YYYY-MM-DD HH24:MI:SS'),'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-08-08 07:21:53','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540980,'N',540902,'RefundBase',560753,'N','Y','N','N','N','N','N','N',0,0,'Vergütung basiert auf',544201,'de.metas.contracts','N','N')
;

-- 2018-08-08T07:21:54.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560753 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-08T07:21:59.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_RefundConfig','ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN RefundBase CHAR(1) DEFAULT ''P'' NOT NULL')
;

-- 2018-08-08T07:23:44.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Mindestmenge', PrintName='Mindestmenge',Updated=TO_TIMESTAMP('2018-08-08 07:23:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2414
;

-- 2018-08-08T07:23:44.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MinQty', Name='Mindestmenge', Description='Minimum quantity for the business partner', Help='If a minimum quantity is defined, and the quantity is based on the percentage is lower, the minimum quantity is used.' WHERE AD_Element_ID=2414
;

-- 2018-08-08T07:23:44.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MinQty', Name='Mindestmenge', Description='Minimum quantity for the business partner', Help='If a minimum quantity is defined, and the quantity is based on the percentage is lower, the minimum quantity is used.', AD_Element_ID=2414 WHERE UPPER(ColumnName)='MINQTY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-08T07:23:44.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MinQty', Name='Mindestmenge', Description='Minimum quantity for the business partner', Help='If a minimum quantity is defined, and the quantity is based on the percentage is lower, the minimum quantity is used.' WHERE AD_Element_ID=2414 AND IsCentrallyMaintained='Y'
;

-- 2018-08-08T07:23:44.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mindestmenge', Description='Minimum quantity for the business partner', Help='If a minimum quantity is defined, and the quantity is based on the percentage is lower, the minimum quantity is used.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2414) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2414)
;

-- 2018-08-08T07:23:44.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mindestmenge', Name='Mindestmenge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2414)
;

-- 2018-08-08T07:23:53.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Help='',Updated=TO_TIMESTAMP('2018-08-08 07:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2414
;

-- 2018-08-08T07:23:53.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MinQty', Name='Mindestmenge', Description='', Help='' WHERE AD_Element_ID=2414
;

-- 2018-08-08T07:23:53.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MinQty', Name='Mindestmenge', Description='', Help='', AD_Element_ID=2414 WHERE UPPER(ColumnName)='MINQTY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-08T07:23:53.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MinQty', Name='Mindestmenge', Description='', Help='' WHERE AD_Element_ID=2414 AND IsCentrallyMaintained='Y'
;

-- 2018-08-08T07:23:53.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mindestmenge', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2414) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2414)
;

-- 2018-08-08T07:24:02.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2414 AND AD_Language='fr_CH'
;

-- 2018-08-08T07:24:04.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2414 AND AD_Language='it_CH'
;

-- 2018-08-08T07:24:07.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2414 AND AD_Language='en_GB'
;

-- 2018-08-08T07:24:26.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Minimum quantity',PrintName='Minimum quantity',Description='',Help='' WHERE AD_Element_ID=2414 AND AD_Language='en_US'
;

-- 2018-08-08T07:24:26.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2414,'en_US') 
;

-- 2018-08-08T07:24:33.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Minimum quantity' WHERE AD_Element_ID=2414 AND AD_Language='de_CH'
;

-- 2018-08-08T07:24:33.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2414,'de_CH') 
;

-- 2018-08-08T07:24:40.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Mindestmenge',PrintName='Mindestmenge',Description='',Help='' WHERE AD_Element_ID=2414 AND AD_Language='de_CH'
;

-- 2018-08-08T07:24:40.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2414,'de_CH') 
;

-- 2018-08-08T07:25:02.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,CreatedBy,AD_Client_ID,IsActive,Created,IsUpdateable,ValueMin,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (29,10,0,'N','N','N','N',0,100,0,'Y',TO_TIMESTAMP('2018-08-08 07:25:01','YYYY-MM-DD HH24:MI:SS'),'Y','0','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-08-08 07:25:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540980,'N','','MinQty',560754,'N','Y','N','N','N','N','N','N','',0,0,'Mindestmenge',2414,'de.metas.contracts','N','N')
;

-- 2018-08-08T07:25:02.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560754 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-08T07:25:06.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_RefundConfig','ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN MinQty NUMERIC NOT NULL DEFAULT 0')
;

-- 2018-08-08T07:25:35.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,560754,540880,540436,0,TO_TIMESTAMP('2018-08-08 07:25:34','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',30,TO_TIMESTAMP('2018-08-08 07:25:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T07:27:02.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(C_Flatrate_RefundConfig.MinQty, 0)', EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2018-08-08 07:27:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540880
;

-- 2018-08-08T07:27:52.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='Pro Produkt und Mindestmenge ist nur ein aktiver Datensatz erlaubt.',Updated=TO_TIMESTAMP('2018-08-08 07:27:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540436
;

-- 2018-08-08T07:28:01.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',ErrorMsg='Pro Produkt und Mindestmenge ist nur ein aktiver Datensatz erlaubt.' WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540436
;

-- 2018-08-08T07:28:35.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',ErrorMsg='Only one active record is allowed per product and min quantity.' WHERE AD_Language='en_US' AND AD_Index_Table_ID=540436
;

-- 2018-08-08T07:29:34.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS uc_c_flatrate_refundconfig
;

-- 2018-08-08T07:29:34.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX UC_C_Flatrate_RefundConfig ON C_Flatrate_RefundConfig (C_Flatrate_Conditions_ID,COALESCE(C_Flatrate_RefundConfig.M_Product_ID, 0),COALESCE(C_Flatrate_RefundConfig.MinQty, 0)) WHERE IsActive='Y'
;

-- 2018-08-08T07:32:28.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544202,0,'RefundMode',TO_TIMESTAMP('2018-08-08 07:32:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Staffel-Modus','Staffel-Modus',TO_TIMESTAMP('2018-08-08 07:32:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T07:32:28.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544202 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-08T07:32:43.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544202 AND AD_Language='de_CH'
;

-- 2018-08-08T07:32:43.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544202,'de_CH') 
;

-- 2018-08-08T07:32:54.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Mode',PrintName='Mode' WHERE AD_Element_ID=544202 AND AD_Language='en_US'
;

-- 2018-08-08T07:32:54.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544202,'en_US') 
;

-- 2018-08-08T07:33:24.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540903,TO_TIMESTAMP('2018-08-08 07:33:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','RefundMode',TO_TIMESTAMP('2018-08-08 07:33:24','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-08-08T07:33:24.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540903 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-08-08T07:35:51.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540903,541669,TO_TIMESTAMP('2018-08-08 07:35:50','YYYY-MM-DD HH24:MI:SS'),100,'Die eingekauften Produkte werden mit den jeweiligen Staffelsätzen verrechnet.','de.metas.contracts','Y','Gestaffelte Rückvergütung',TO_TIMESTAMP('2018-08-08 07:35:50','YYYY-MM-DD HH24:MI:SS'),100,'S','PerScale')
;

-- 2018-08-08T07:35:51.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541669 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-08-08T07:36:48.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y' WHERE AD_Ref_List_ID=541669 AND AD_Language='de_CH'
;

-- 2018-08-08T07:38:35.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Name='Tiered refund',Description='Refund is based on the respective min quantity at the time.' WHERE AD_Ref_List_ID=541669 AND AD_Language='en_US'
;

-- 2018-08-08T07:40:03.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540903,541670,TO_TIMESTAMP('2018-08-08 07:40:03','YYYY-MM-DD HH24:MI:SS'),100,'Die Erreichung einer Stufe wird auf ALLE Einkäufe des Produkts angewandt.','de.metas.contracts','Y','Gesamtrückvergütung',TO_TIMESTAMP('2018-08-08 07:40:03','YYYY-MM-DD HH24:MI:SS'),100,'A','Accumulated')
;

-- 2018-08-08T07:40:03.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541670 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-08-08T07:40:08.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y' WHERE AD_Ref_List_ID=541670 AND AD_Language='de_CH'
;

-- 2018-08-08T07:41:31.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Name='Accumulated',Description='The refund for the highest tier reached is applied to all invoicable items.' WHERE AD_Ref_List_ID=541670 AND AD_Language='en_US'
;

-- 2018-08-08T07:42:17.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,CreatedBy,AD_Client_ID,IsActive,Created,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (17,'S',1,0,'N','N','N','N',0,100,0,'Y',TO_TIMESTAMP('2018-08-08 07:42:17','YYYY-MM-DD HH24:MI:SS'),'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-08-08 07:42:17','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540980,'N',540903,'RefundMode',560755,'N','Y','N','N','N','N','N','N',0,0,'Staffel-Modus',544202,'de.metas.contracts','N','N')
;

-- 2018-08-08T07:42:17.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560755 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-08T07:42:19.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Flatrate_RefundConfig','ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN RefundMode CHAR(1) DEFAULT ''S'' NOT NULL')
;

-- 2018-08-08T07:43:23.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560752,565575,0,541106,TO_TIMESTAMP('2018-08-08 07:43:23','YYYY-MM-DD HH24:MI:SS'),100,'Rückvergütungsbetrag pro Produkt-Einheit',10,'de.metas.contracts','','Y','N','N','N','N','N','N','N','Rückvergütungsbetrag',TO_TIMESTAMP('2018-08-08 07:43:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T07:43:23.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565575 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-08-08T07:43:23.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560753,565576,0,541106,TO_TIMESTAMP('2018-08-08 07:43:23','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.contracts','Y','N','N','N','N','N','N','N','Vergütung basiert auf',TO_TIMESTAMP('2018-08-08 07:43:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T07:43:23.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565576 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-08-08T07:43:23.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560754,565577,0,541106,TO_TIMESTAMP('2018-08-08 07:43:23','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.contracts','','Y','N','N','N','N','N','N','N','Mindestmenge',TO_TIMESTAMP('2018-08-08 07:43:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T07:43:23.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565577 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-08-08T07:43:23.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560755,565578,0,541106,TO_TIMESTAMP('2018-08-08 07:43:23','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.contracts','Y','N','N','N','N','N','N','N','Staffel-Modus',TO_TIMESTAMP('2018-08-08 07:43:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T07:43:23.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565578 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-08-08T07:44:20.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-08-08 07:44:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560133
;

-- 2018-08-08T07:44:48.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ValueMin='', IsMandatory='N',Updated=TO_TIMESTAMP('2018-08-08 07:44:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560752
;

-- 2018-08-08T07:44:52.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_refundconfig','RefundAmt','NUMERIC',null,null)
;

-- 2018-08-08T07:44:52.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_refundconfig','RefundAmt',null,'NULL',null)
;

-- 2018-08-08T07:45:24.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-08-08 07:45:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565576
;

-- 2018-08-08T07:45:27.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-08-08 07:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565578
;

-- 2018-08-08T07:45:30.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-08-08 07:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565575
;

-- 2018-08-08T07:45:34.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-08-08 07:45:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565577
;

-- 2018-08-08T07:45:35.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-08-08 07:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565575
;

-- 2018-08-08T07:45:36.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-08-08 07:45:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565576
;

-- 2018-08-08T07:45:41.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-08-08 07:45:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565578
;

-- 2018-08-08T07:46:45.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@RefundBase/P@=''F''',Updated=TO_TIMESTAMP('2018-08-08 07:46:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565575
;

-- 2018-08-08T07:46:57.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@RefundBase/P@=''P''',Updated=TO_TIMESTAMP('2018-08-08 07:46:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564195
;

-- 2018-08-08T07:47:34.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=39,Updated=TO_TIMESTAMP('2018-08-08 07:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565576
;

-- 2018-08-08T07:48:37.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=47,Updated=TO_TIMESTAMP('2018-08-08 07:48:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565578
;

-- 2018-08-08T07:48:41.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=32,Updated=TO_TIMESTAMP('2018-08-08 07:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565577
;

-- 2018-08-08T07:48:59.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=32,Updated=TO_TIMESTAMP('2018-08-08 07:48:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565577
;

-- 2018-08-08T07:49:02.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=39,Updated=TO_TIMESTAMP('2018-08-08 07:49:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565576
;

-- 2018-08-08T07:49:05.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=45,Updated=TO_TIMESTAMP('2018-08-08 07:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565575
;

-- 2018-08-08T07:49:10.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=47,Updated=TO_TIMESTAMP('2018-08-08 07:49:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565578
;

-- 2018-08-08T07:49:17.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-08-08 07:49:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564249
;

-- 2018-08-08T08:20:50.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,565575,0,541106,541612,552497,'F',TO_TIMESTAMP('2018-08-08 08:20:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Rückvergütungsbetrag',80,0,0,TO_TIMESTAMP('2018-08-08 08:20:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T08:21:10.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,565576,0,541106,541612,552498,'F',TO_TIMESTAMP('2018-08-08 08:21:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Vergütung basiert auf',90,0,0,TO_TIMESTAMP('2018-08-08 08:21:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T08:23:43.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,565577,0,541106,541612,552499,'F',TO_TIMESTAMP('2018-08-08 08:23:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Mindestmenge',100,0,0,TO_TIMESTAMP('2018-08-08 08:23:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T08:23:55.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,565578,0,541106,541612,552500,'F',TO_TIMESTAMP('2018-08-08 08:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Staffel-Modus',110,0,0,TO_TIMESTAMP('2018-08-08 08:23:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-08T08:24:58.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-08-08 08:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552499
;

-- 2018-08-08T08:24:58.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-08-08 08:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552057
;

-- 2018-08-08T08:24:58.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-08-08 08:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552498
;

-- 2018-08-08T08:24:58.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-08-08 08:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552056
;

-- 2018-08-08T08:24:58.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-08-08 08:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552497
;

-- 2018-08-08T08:24:58.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-08-08 08:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552058
;

-- 2018-08-08T08:24:58.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-08-08 08:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552500
;

-- 2018-08-08T08:24:58.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2018-08-08 08:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552054
;

-- 2018-08-08T08:24:58.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2018-08-08 08:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552052
;

-- 2018-08-08T08:25:29.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2018-08-08 08:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552051
;

-- 2018-08-08T08:25:32.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2018-08-08 08:25:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552499
;

-- 2018-08-08T08:25:35.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-08-08 08:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552057
;

-- 2018-08-08T08:25:37.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-08-08 08:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552498
;

-- 2018-08-08T08:25:39.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-08-08 08:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552056
;

-- 2018-08-08T08:25:41.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-08-08 08:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552497
;

-- 2018-08-08T08:25:43.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2018-08-08 08:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552058
;

-- 2018-08-08T08:25:45.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2018-08-08 08:25:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552500
;

-- 2018-08-08T08:25:47.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2018-08-08 08:25:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552054
;

-- 2018-08-08T08:25:54.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2018-08-08 08:25:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552052
;

-- 2018-08-08T08:27:24.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-08-08 08:27:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564187
;

-- 2018-08-08T08:27:42.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2018-08-08 08:27:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552051
;

-- 2018-08-08T08:28:12.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2018-08-08 08:28:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552051
;

-- 2018-08-08T08:36:18.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-08-08 08:36:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552498
;

-- 2018-08-08T08:36:18.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-08-08 08:36:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552056
;

-- 2018-08-08T08:36:18.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-08-08 08:36:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552497
;

-- 2018-08-08T08:36:18.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-08-08 08:36:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552500
;

-- 2018-08-08T08:36:18.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2018-08-08 08:36:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552057
;

-- 2018-08-08T08:36:56.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2018-08-08 08:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552057
;

-- 2018-08-08T08:37:45.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2018-08-08 08:37:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552498
;

-- 2018-08-08T08:37:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2018-08-08 08:37:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552056
;

-- 2018-08-08T08:37:52.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2018-08-08 08:37:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552497
;

-- 2018-08-08T08:37:57.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2018-08-08 08:37:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552500
;

