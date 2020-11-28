-- 2017-11-06T12:05:06.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557797,560470,0,53030,TO_TIMESTAMP('2017-11-06 12:05:05','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',10,'EE01','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','Y','Y','N','N','N','N','N','Merkmale',TO_TIMESTAMP('2017-11-06 12:05:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-06T12:05:06.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560470 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-06T12:05:06.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557800,560471,0,53030,TO_TIMESTAMP('2017-11-06 12:05:06','YYYY-MM-DD HH24:MI:SS'),100,1024,'EE01','Y','Y','Y','N','N','N','N','N','StorageAttributesKey (technical)',TO_TIMESTAMP('2017-11-06 12:05:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-06T12:05:06.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560471 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-06T12:05:46.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=35,Updated=TO_TIMESTAMP('2017-11-06 12:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560470
;

-- 2017-11-06T12:05:52.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-11-06 12:05:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560471
;

-- 2017-11-06T12:06:17.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=35,Updated=TO_TIMESTAMP('2017-11-06 12:06:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560470
;

-- 2017-11-06T13:14:39.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-06 13:14:39','YYYY-MM-DD HH24:MI:SS'),Name='Create documents',PrintName='Create documents' WHERE AD_Element_ID=53258 AND AD_Language='en_GB'
;

-- 2017-11-06T13:14:39.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53258,'en_GB') 
;

-- 2017-11-06T13:14:41.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-06 13:14:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=53258 AND AD_Language='en_GB'
;

-- 2017-11-06T13:14:41.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53258,'en_GB') 
;

-- 2017-11-06T13:15:31.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Legt fest ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='',Updated=TO_TIMESTAMP('2017-11-06 13:15:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53258
;

-- 2017-11-06T13:15:31.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCreatePlan', Name='Plan erstellen', Description='Legt fest ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='' WHERE AD_Element_ID=53258
;

-- 2017-11-06T13:15:31.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreatePlan', Name='Plan erstellen', Description='Legt fest ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='', AD_Element_ID=53258 WHERE UPPER(ColumnName)='ISCREATEPLAN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-06T13:15:31.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreatePlan', Name='Plan erstellen', Description='Legt fest ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='' WHERE AD_Element_ID=53258 AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:15:31.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Plan erstellen', Description='Legt fest ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53258) AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:15:57.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.',Updated=TO_TIMESTAMP('2017-11-06 13:15:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53258
;

-- 2017-11-06T13:15:57.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCreatePlan', Name='Plan erstellen', Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='' WHERE AD_Element_ID=53258
;

-- 2017-11-06T13:15:57.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreatePlan', Name='Plan erstellen', Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='', AD_Element_ID=53258 WHERE UPPER(ColumnName)='ISCREATEPLAN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-06T13:15:57.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreatePlan', Name='Plan erstellen', Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='' WHERE AD_Element_ID=53258 AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:15:57.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Plan erstellen', Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53258) AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:16:11.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-06 13:16:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.',Help='' WHERE AD_Element_ID=53258 AND AD_Language='de_CH'
;

-- 2017-11-06T13:16:11.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53258,'de_CH') 
;

-- 2017-11-06T13:18:04.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.', Name='Beleg fertig stellen', PrintName='Beleg fertig stellen',Updated=TO_TIMESTAMP('2017-11-06 13:18:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542644
;

-- 2017-11-06T13:18:04.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsDocComplete', Name='Beleg fertig stellen', Description='Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.', Help=NULL WHERE AD_Element_ID=542644
;

-- 2017-11-06T13:18:04.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDocComplete', Name='Beleg fertig stellen', Description='Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.', Help=NULL, AD_Element_ID=542644 WHERE UPPER(ColumnName)='ISDOCCOMPLETE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-06T13:18:04.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDocComplete', Name='Beleg fertig stellen', Description='Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.', Help=NULL WHERE AD_Element_ID=542644 AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:18:04.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beleg fertig stellen', Description='Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542644) AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:18:04.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beleg fertig stellen', Name='Beleg fertig stellen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542644)
;

-- 2017-11-06T13:18:17.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-06 13:18:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.' WHERE AD_Element_ID=542644 AND AD_Language='de_CH'
;

-- 2017-11-06T13:18:17.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542644,'de_CH') 
;

-- 2017-11-06T13:18:23.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-06 13:18:23','YYYY-MM-DD HH24:MI:SS'),Name='Beleg fertig stellen',PrintName='Beleg fertig stellen' WHERE AD_Element_ID=542644 AND AD_Language='de_CH'
;

-- 2017-11-06T13:18:23.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542644,'de_CH') 
;

-- 2017-11-06T13:19:04.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Beleg erstellen', PrintName='Beleg erstellen',Updated=TO_TIMESTAMP('2017-11-06 13:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53258
;

-- 2017-11-06T13:19:04.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCreatePlan', Name='Beleg erstellen', Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='' WHERE AD_Element_ID=53258
;

-- 2017-11-06T13:19:04.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreatePlan', Name='Beleg erstellen', Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='', AD_Element_ID=53258 WHERE UPPER(ColumnName)='ISCREATEPLAN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-06T13:19:04.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreatePlan', Name='Beleg erstellen', Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='' WHERE AD_Element_ID=53258 AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:19:04.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beleg erstellen', Description='Legt fest, ob das System die betreffenden Belege (z.B. Produktionsaufträge) gegebenenfalls direkt erstellen soll.', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53258) AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:19:04.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beleg erstellen', Name='Beleg erstellen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53258)
;

-- 2017-11-06T13:19:14.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-06 13:19:14','YYYY-MM-DD HH24:MI:SS'),Name='Beleg erstellen',PrintName='Beleg erstellen' WHERE AD_Element_ID=53258 AND AD_Language='de_CH'
;

-- 2017-11-06T13:19:14.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53258,'de_CH') 
;

-- 2017-11-06T13:20:19.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N', SeqNo=104,Updated=TO_TIMESTAMP('2017-11-06 13:20:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53520
;

-- 2017-11-06T13:20:25.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-11-06 13:20:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555188
;

-- 2017-11-06T13:21:07.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=112,Updated=TO_TIMESTAMP('2017-11-06 13:21:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555188
;

-- 2017-11-06T13:24:18.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D', Name='Attributabhängig', PrintName='Attributabhängig',Updated=TO_TIMESTAMP('2017-11-06 13:24:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542237
;

-- 2017-11-06T13:24:18.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAttributeDependant', Name='Attributabhängig', Description=NULL, Help=NULL WHERE AD_Element_ID=542237
;

-- 2017-11-06T13:24:18.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAttributeDependant', Name='Attributabhängig', Description=NULL, Help=NULL, AD_Element_ID=542237 WHERE UPPER(ColumnName)='ISATTRIBUTEDEPENDANT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-06T13:24:18.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAttributeDependant', Name='Attributabhängig', Description=NULL, Help=NULL WHERE AD_Element_ID=542237 AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:24:18.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Attributabhängig', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542237) AND IsCentrallyMaintained='Y'
;

-- 2017-11-06T13:24:18.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Attributabhängig', Name='Attributabhängig' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542237)
;

-- 2017-11-06T13:24:42.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557819,542237,0,20,53020,'N','IsAttributeDependant',TO_TIMESTAMP('2017-11-06 13:24:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE01',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Attributabhängig',0,0,TO_TIMESTAMP('2017-11-06 13:24:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-06T13:24:42.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557819 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-06T13:24:45.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN IsAttributeDependant CHAR(1) DEFAULT ''N'' CHECK (IsAttributeDependant IN (''Y'',''N'')) NOT NULL')
;

-- 2017-11-06T13:25:26.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='1',Updated=TO_TIMESTAMP('2017-11-06 13:25:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53020
;

-- 2017-11-06T13:25:31.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2017-11-06 13:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53020
;

-- 2017-11-06T13:25:58.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=40,Updated=TO_TIMESTAMP('2017-11-06 13:25:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560470
;

-- 2017-11-06T13:26:20.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2017-11-06 13:26:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560470
;

-- 2017-11-06T13:27:15.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557819,560472,0,53030,0,TO_TIMESTAMP('2017-11-06 13:27:15','YYYY-MM-DD HH24:MI:SS'),100,0,'EE01',0,'Y','Y','Y','Y','N','N','N','N','N','Attributabhängig',40,40,0,1,1,TO_TIMESTAMP('2017-11-06 13:27:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-06T13:27:15.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560472 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-06T13:27:37.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-11-06 13:27:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560470
;

-- 2017-11-06T13:27:42.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=35,Updated=TO_TIMESTAMP('2017-11-06 13:27:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53511
;

-- 2017-11-06T13:28:26.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsAttributeDependand@ = ''Y''',Updated=TO_TIMESTAMP('2017-11-06 13:28:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560470
;

-- 2017-11-06T13:29:48.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsAttributeDependand@=Y',Updated=TO_TIMESTAMP('2017-11-06 13:29:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560470
;

-- 2017-11-06T15:31:01.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557820,566,0,11,53020,'N','SeqNo',TO_TIMESTAMP('2017-11-06 15:31:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','EE01',14,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Reihenfolge',0,0,TO_TIMESTAMP('2017-11-06 15:31:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-11-06T15:31:01.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557820 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-11-06T15:31:08.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN SeqNo NUMERIC(10)')
;

-- 2017-11-06T15:32:46.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=35,Updated=TO_TIMESTAMP('2017-11-06 15:32:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53511
;

-- 2017-11-06T15:33:24.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557820,560473,0,53030,0,TO_TIMESTAMP('2017-11-06 15:33:24','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',0,'EE01','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','Y','N','N','N','N','N','Reihenfolge',37,0,1,1,TO_TIMESTAMP('2017-11-06 15:33:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-06T15:33:24.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560473 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-11-06T15:44:48.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=37,Updated=TO_TIMESTAMP('2017-11-06 15:44:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560473
;

-- 2017-11-06T15:49:55.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsAttributeDependant@=Y',Updated=TO_TIMESTAMP('2017-11-06 15:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560470
;

