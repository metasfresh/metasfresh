-- 2018-05-17T21:35:05.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2018-05-17 21:35:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541106
;

-- 2018-05-17T21:35:40.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-05-17 21:35:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560130
;

-- 2018-05-17T21:35:56.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2018-05-17 21:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560132
;

-- 2018-05-17T21:36:25.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2018-05-17 21:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560131
;

-- 2018-05-17T21:37:10.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ValueMax='100', ValueMin='0.01',Updated=TO_TIMESTAMP('2018-05-17 21:37:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560133
;

-- 2018-05-17T22:10:29.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544087,0,'InvoiceInterval',TO_TIMESTAMP('2018-05-17 22:10:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Anz. Einheiten zwischen zwei Rechnungsstellungen','Anz. Einheiten zwischen zwei Rechnungsstellungen',TO_TIMESTAMP('2018-05-17 22:10:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-17T22:10:29.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544087 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-17T22:10:55.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,ValueMin,Version) VALUES (0,560168,544087,0,11,257,'N','InvoiceInterval',TO_TIMESTAMP('2018-05-17 22:10:55','YYYY-MM-DD HH24:MI:SS'),100,'N','1','D',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Anz. Einheiten zwischen zwei Rechnungsstellungen',0,0,TO_TIMESTAMP('2018-05-17 22:10:55','YYYY-MM-DD HH24:MI:SS'),100,'1',0)
;

-- 2018-05-17T22:10:55.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560168 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-17T22:11:58.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549326,564237,0,193,TO_TIMESTAMP('2018-05-17 22:11:58','YYYY-MM-DD HH24:MI:SS'),100,'When data is imported from a an external datasource, this element can be used to identify the data record',255,'D','Y','Y','N','N','N','N','N','Migration_Key',TO_TIMESTAMP('2018-05-17 22:11:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-17T22:11:58.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564237 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-05-17T22:11:58.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560168,564238,0,193,TO_TIMESTAMP('2018-05-17 22:11:58','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','Anz. Einheiten zwischen zwei Rechnungsstellungen',TO_TIMESTAMP('2018-05-17 22:11:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-17T22:11:58.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564238 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-05-17T22:12:13.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-05-17 22:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1201
;

-- 2018-05-17T22:12:54.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=105, SeqNoGrid=105,Updated=TO_TIMESTAMP('2018-05-17 22:12:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564238
;

-- 2018-05-18T05:34:57.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='',Updated=TO_TIMESTAMP('2018-05-18 05:34:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=257
;

-- 2018-05-18T05:35:15.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-05-18 05:35:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- 2018-05-18T05:39:14.118
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-05-18 05:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2882
;

-- 2018-05-18T05:39:33.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-05-18 05:39:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2883
;

-- 2018-05-18T05:42:13.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Example: if this rerod sais to invoice on wednesdays and this column has a value of 2, then a delivery from tuesday shall not be invoiced in the same week, but one from monday shall be.
IMPORTANT: currently not used;',Updated=TO_TIMESTAMP('2018-05-18 05:42:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3698
;

-- 2018-05-18T05:42:48.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Description='Send invoices only if the amount exceeds the limit
IMPORTANT: currently not used;',Updated=TO_TIMESTAMP('2018-05-18 05:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2144
;

-- 2018-05-18T05:42:48.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Betragsgrenze', Description='Send invoices only if the amount exceeds the limit
IMPORTANT: currently not used;', Help='The Amount Limit checkbox indicates if invoices will be sent out if they are below the entered limit.   	' WHERE AD_Column_ID=2144
;

-- 2018-05-18T05:48:48.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='See InvoiceWeekDayCutoff for what I know about it
IMPORTANT: currently not used;
',Updated=TO_TIMESTAMP('2018-05-18 05:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3700
;

-- 2018-05-18T05:49:53.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='InvoiceDistance',Updated=TO_TIMESTAMP('2018-05-18 05:49:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544087
;

-- 2018-05-18T05:49:53.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoiceDistance', Name='Anz. Einheiten zwischen zwei Rechnungsstellungen', Description=NULL, Help=NULL WHERE AD_Element_ID=544087
;

-- 2018-05-18T05:49:53.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceDistance', Name='Anz. Einheiten zwischen zwei Rechnungsstellungen', Description=NULL, Help=NULL, AD_Element_ID=544087 WHERE UPPER(ColumnName)='INVOICEDISTANCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-18T05:49:53.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoiceDistance', Name='Anz. Einheiten zwischen zwei Rechnungsstellungen', Description=NULL, Help=NULL WHERE AD_Element_ID=544087 AND IsCentrallyMaintained='Y'
;


-- 2018-05-17T22:11:00.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_InvoiceSchedule','ALTER TABLE public.C_InvoiceSchedule ADD COLUMN InvoiceDistance NUMERIC(10) NOT NULL DEFAULT 1')
;

-- 2018-05-18T05:51:28.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='0', ValueMin='0',Updated=TO_TIMESTAMP('2018-05-18 05:51:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- 2018-05-18T05:53:14.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='1', TechnicalNote='Example: InvoiceFrequency = Montly and InvoiceDistance = 1 means that there can be one invoice per month; 3 means that there can be one invoice per quarter.', ValueMin='1',Updated=TO_TIMESTAMP('2018-05-18 05:53:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- 2018-05-18T06:06:05.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Example: InvoiceFrequency = Montly and InvoiceDistance = 1 means that there can be one invoice per month; 3 means that there can be one invoice per quarter.
This value is ignored if the schedule is set to "TwiceMonthly".',Updated=TO_TIMESTAMP('2018-05-18 06:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560168
;

-- fix ValueNAmes
UPDATE AD_Ref_List SET ValueName='Daily' WHERE AD_Ref_List_ID=252; -- täglich
UPDATE AD_Ref_List SET ValueName='Monthly' WHERE AD_Ref_List_ID=254; -- monatlich
UPDATE AD_Ref_List SET ValueName='TwiceMonthly' WHERE AD_Ref_List_ID=255; -- 
UPDATE AD_Ref_List SET ValueName='Weekly' WHERE AD_Ref_List_ID=253; -- 
-- 2018-05-18T06:08:20.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@InvoiceFrequency@!T',Updated=TO_TIMESTAMP('2018-05-18 06:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564238
;

-- 2018-05-18T06:08:42.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-05-18 06:08:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2881
;

-- 2018-05-18T06:09:05.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='IMPORTANT: currently not used;',Updated=TO_TIMESTAMP('2018-05-18 06:09:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3699
;

-- 2018-05-18T06:09:59.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=1,Updated=TO_TIMESTAMP('2018-05-18 06:09:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1236
;

-- 2018-05-18T06:10:08.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2018-05-18 06:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1237
;

-- 2018-05-18T06:10:30.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-05-18 06:10:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564237
;

-- 2018-05-18T06:10:47.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-05-18 06:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1202
;

-- 2018-05-18T06:10:58.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='IMPORTANT: currently not used;',Updated=TO_TIMESTAMP('2018-05-18 06:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2145
;

-- 2018-05-18T06:12:04.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET IsOrderByValue='Y',Updated=TO_TIMESTAMP('2018-05-18 06:12:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=167
;

-- 2018-05-18T06:27:55.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:27:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551910
;

-- 2018-05-18T06:28:04.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:28:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551009
;

-- 2018-05-18T06:28:13.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551476
;

-- 2018-05-18T06:28:37.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548120
;

-- 2018-05-18T06:28:52.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563597
;

-- 2018-05-18T06:29:22.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Subscr''|@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548088
;

-- 2018-05-18T06:30:17.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Subscr''&@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:30:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548088
;

-- 2018-05-18T06:30:51.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Subscr'' & @Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:30:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548088
;

-- 2018-05-18T06:31:38.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:31:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547848
;

-- 2018-05-18T06:37:56.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560396
;

-- 2018-05-18T06:38:31.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:38:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560398
;

-- 2018-05-18T06:38:42.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''FlatFee'' & @Type_Conditions@!''HoldingFee'' & @Type_Conditions@!''Procuremnt'' & @Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:38:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559764
;

-- 2018-05-18T06:39:18.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refundable'' & @Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:39:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559780
;

-- 2018-05-18T06:40:18.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refundable'' & @Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548428
;

-- 2018-05-18T06:40:53.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:40:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560395
;

-- 2018-05-18T06:40:59.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:40:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560397
;

-- 2018-05-18T06:41:10.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''FlatFee'' & @Type_Conditions@!''HoldingFee'' & @Type_Conditions@!''Procuremnt'' & @Type_Conditions@!''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:41:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551055
;

-- 2018-05-18T06:41:37.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''Subscr''|@Type_Conditions@=''QualityBsd''|@Type_Conditions@=''Procuremnt''|@Type_Conditions@=''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:41:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550474
;

-- 2018-05-18T06:42:51.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@=''Subscr''|@Type_Conditions@=''QualityBsd''|@Type_Conditions@=''Procuremnt''|@Type_Conditions@=''Refund''',Updated=TO_TIMESTAMP('2018-05-18 06:42:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559779
;

UPDATE AD_Ref_List SET ValueName='Monday' WHERE AD_Ref_List_ID=246;
UPDATE AD_Ref_List SET ValueName='Tuesday' WHERE AD_Ref_List_ID=247;
UPDATE AD_Ref_List SET ValueName='Wednesday' WHERE AD_Ref_List_ID=248;
UPDATE AD_Ref_List SET ValueName='Thursday' WHERE AD_Ref_List_ID=249;
UPDATE AD_Ref_List SET ValueName='Friday' WHERE AD_Ref_List_ID=250;
UPDATE AD_Ref_List SET ValueName='Saturday' WHERE AD_Ref_List_ID=251;
UPDATE AD_Ref_List SET ValueName='Sunday' WHERE AD_Ref_List_ID=245;

-- 2018-05-18T05:35:20.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_InvoiceSchedule SET InvoiceDistance=1 WHERE InvoiceDistance IS NULL
;