-- 2018-05-15T18:17:12.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560158,3020,0,10,533,'N','Status',TO_TIMESTAMP('2018-05-15 18:17:12','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',1,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Status',0,0,TO_TIMESTAMP('2018-05-15 18:17:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-05-15T18:17:12.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560158 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-15T18:17:20.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN Status VARCHAR(1)')
;

-- 2018-05-15T18:18:03.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560158,564208,0,441,0,TO_TIMESTAMP('2018-05-15 18:18:03','YYYY-MM-DD HH24:MI:SS'),100,'',0,'D','',0,'Y','Y','Y','N','N','N','N','N','Status',960,800,0,1,1,TO_TIMESTAMP('2018-05-15 18:18:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-15T18:18:03.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-05-15T18:18:10.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=790,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564208
;

-- 2018-05-15T18:18:10.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=800,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5932
;

-- 2018-05-15T18:18:10.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=810,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564184
;

-- 2018-05-15T18:18:10.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=820,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564185
;

-- 2018-05-15T18:18:10.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=830,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560564
;

-- 2018-05-15T18:18:10.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=840,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560566
;

-- 2018-05-15T18:18:10.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=850,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564169
;

-- 2018-05-15T18:18:10.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=860,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564170
;

-- 2018-05-15T18:18:10.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=870,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564175
;

-- 2018-05-15T18:18:10.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=880,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564176
;

-- 2018-05-15T18:18:10.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=890,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564173
;

-- 2018-05-15T18:18:10.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=900,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564172
;

-- 2018-05-15T18:18:10.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=910,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564177
;

-- 2018-05-15T18:18:10.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=920,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564174
;

-- 2018-05-15T18:18:10.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=930,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564171
;

-- 2018-05-15T18:18:10.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=940,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564203
;

-- 2018-05-15T18:18:10.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=950,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5941
;

-- 2018-05-15T18:18:10.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=960,Updated=TO_TIMESTAMP('2018-05-15 18:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5924
;

-- 2018-05-15T18:18:17.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=800,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564184
;

-- 2018-05-15T18:18:17.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=810,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564185
;

-- 2018-05-15T18:18:17.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=820,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560564
;

-- 2018-05-15T18:18:17.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=830,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560566
;

-- 2018-05-15T18:18:17.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=840,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564169
;

-- 2018-05-15T18:18:17.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=850,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564170
;

-- 2018-05-15T18:18:17.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=860,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564175
;

-- 2018-05-15T18:18:17.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=870,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564176
;

-- 2018-05-15T18:18:17.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=880,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564173
;

-- 2018-05-15T18:18:17.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=890,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564172
;

-- 2018-05-15T18:18:17.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=900,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564177
;

-- 2018-05-15T18:18:17.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=910,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564174
;

-- 2018-05-15T18:18:17.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=920,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564171
;

-- 2018-05-15T18:18:17.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=930,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=564203
;

-- 2018-05-15T18:18:17.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=940,Updated=TO_TIMESTAMP('2018-05-15 18:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5932
;

-- 2018-05-15T18:21:11.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544084,0,'IsActiveStatus',TO_TIMESTAMP('2018-05-15 18:21:11','YYYY-MM-DD HH24:MI:SS'),100,'','D','','Y','Active Status','Active Status',TO_TIMESTAMP('2018-05-15 18:21:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-15T18:21:11.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544084 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-15T18:21:27.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544084, AD_Reference_ID=20, ColumnName='IsActiveStatus', DefaultValue='Y', Description='', Help='', IsMandatory='Y', Name='Active Status',Updated=TO_TIMESTAMP('2018-05-15 18:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560158
;

-- 2018-05-15T18:21:27.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Active Status', Description='', Help='' WHERE AD_Column_ID=560158
;

-- 2018-05-15T18:21:29.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN IsActiveStatus CHAR(1) DEFAULT ''Y'' CHECK (IsActiveStatus IN (''Y'',''N'')) NOT NULL')
;


/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner DROP COLUMN Status')
;




-- 2018-05-15T18:30:44.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=868, AD_Reference_ID=10, ColumnName='DocumentNote', DefaultValue='', Description='Zusätzliche Information für den Kunden', Help='"Notiz" wird für zusätzliche Informationen zu diesem Produkt verwendet.', IsMandatory='N', Name='Notiz / Zeilentext',Updated=TO_TIMESTAMP('2018-05-15 18:30:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560151
;

-- 2018-05-15T18:30:44.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Notiz / Zeilentext', Description='Zusätzliche Information für den Kunden', Help='"Notiz" wird für zusätzliche Informationen zu diesem Produkt verwendet.' WHERE AD_Column_ID=560151
;

-- 2018-05-15T18:30:55.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN DocumentNote VARCHAR(1)')
;


/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner DROP COLUMN DocumentNote')
;


-- 2018-05-15T18:33:33.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544081, AD_Reference_ID=20, ColumnName='IsShowDeliveryNote', DefaultValue='N', Description=NULL, Help=NULL, IsMandatory='Y', Name='Show Delivery Note',Updated=TO_TIMESTAMP('2018-05-15 18:33:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560151
;

-- 2018-05-15T18:33:33.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Show Delivery Note', Description=NULL, Help=NULL WHERE AD_Column_ID=560151
;


