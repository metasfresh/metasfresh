-- 2018-09-07T07:59:39.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544232,0,'M_Product_Note',TO_TIMESTAMP('2018-09-07 07:59:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produktnotiz','Produktnotiz',TO_TIMESTAMP('2018-09-07 07:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T07:59:39.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544232 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-07T07:59:48.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 07:59:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544232 AND AD_Language='de_CH'
;

-- 2018-09-07T07:59:48.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544232,'de_CH') 
;

-- 2018-09-07T08:00:02.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 08:00:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Product note',PrintName='Product note' WHERE AD_Element_ID=544232 AND AD_Language='en_US'
;

-- 2018-09-07T08:00:02.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544232,'en_US') 
;

-- 2018-09-07T08:03:08.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='M_Product_DocumentNote',Updated=TO_TIMESTAMP('2018-09-07 08:03:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544232
;

-- 2018-09-07T08:03:08.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Product_DocumentNote', Name='Produktnotiz', Description=NULL, Help=NULL WHERE AD_Element_ID=544232
;

-- 2018-09-07T08:03:08.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_DocumentNote', Name='Produktnotiz', Description=NULL, Help=NULL, AD_Element_ID=544232 WHERE UPPER(ColumnName)='M_PRODUCT_DOCUMENTNOTE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-07T08:03:08.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_DocumentNote', Name='Produktnotiz', Description=NULL, Help=NULL WHERE AD_Element_ID=544232 AND IsCentrallyMaintained='Y'
;

-- 2018-09-07T08:04:29.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,ColumnSQL,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,Name,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin) VALUES (14,2000,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-07 08:04:28','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-07 08:04:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',260,'N','M_Product_DocumentNote',560861,'N','N','N','N','N','N','( select p.DocumentNote from M_Product p where p.M_Product_ID=C_OrderLine.M_Product_ID )','N','N',0,0,'Produktnotiz',544232,'D','N','N')
;

-- 2018-09-07T08:04:29.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560861 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-07T08:06:39.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='This column is used in an SQL-column in C_OrderLine',Updated=TO_TIMESTAMP('2018-09-07 08:06:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3014
;

-- 2018-09-07T08:07:27.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560861,568719,0,187,0,TO_TIMESTAMP('2018-09-07 08:07:27','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Produktnotiz',310,300,0,1,1,TO_TIMESTAMP('2018-09-07 08:07:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T08:07:27.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568719 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-07T08:08:32.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,TooltipIconName,Type,Updated,UpdatedBy) VALUES (0,1117,0,540281,1000034,TO_TIMESTAMP('2018-09-07 08:08:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'text','tooltip',TO_TIMESTAMP('2018-09-07 08:08:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T08:09:31.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementField SET AD_Field_ID=568719,Updated=TO_TIMESTAMP('2018-09-07 08:09:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementField_ID=540281
;

-- 2018-09-07T13:29:16.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560861,568729,0,293,0,TO_TIMESTAMP('2018-09-07 13:29:15','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','N','N','N','N','N','N','N','Produktnotiz',290,150,0,1,1,TO_TIMESTAMP('2018-09-07 13:29:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T13:29:16.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568729 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-07T13:29:51.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,568729,0,540282,547084,TO_TIMESTAMP('2018-09-07 13:29:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-09-07 13:29:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T13:30:38.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementField SET TooltipIconName='text', Type='tooltip',Updated=TO_TIMESTAMP('2018-09-07 13:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementField_ID=540282
;

