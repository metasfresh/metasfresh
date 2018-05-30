-- 2018-05-29T18:18:59.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544096,0,'IsOnlyIfInProductAttributeSet',TO_TIMESTAMP('2018-05-29 18:18:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','OnlyIfInProductAttributeSet','OnlyIfInProductAttributeSet',TO_TIMESTAMP('2018-05-29 18:18:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-29T18:18:59.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544096 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-29T18:19:39.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='If this flag is true, the attribute will not be displayed in the HU Editor if it is not present in Product''s attribute set.',Updated=TO_TIMESTAMP('2018-05-29 18:19:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544096
;

-- 2018-05-29T18:19:39.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOnlyIfInProductAttributeSet', Name='OnlyIfInProductAttributeSet', Description='If this flag is true, the attribute will not be displayed in the HU Editor if it is not present in Product''s attribute set.', Help=NULL WHERE AD_Element_ID=544096
;

-- 2018-05-29T18:19:39.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyIfInProductAttributeSet', Name='OnlyIfInProductAttributeSet', Description='If this flag is true, the attribute will not be displayed in the HU Editor if it is not present in Product''s attribute set.', Help=NULL, AD_Element_ID=544096 WHERE UPPER(ColumnName)='ISONLYIFINPRODUCTATTRIBUTESET' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-29T18:19:39.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyIfInProductAttributeSet', Name='OnlyIfInProductAttributeSet', Description='If this flag is true, the attribute will not be displayed in the HU Editor if it is not present in Product''s attribute set.', Help=NULL WHERE AD_Element_ID=544096 AND IsCentrallyMaintained='Y'
;

-- 2018-05-29T18:19:39.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='OnlyIfInProductAttributeSet', Description='If this flag is true, the attribute will not be displayed in the HU Editor if it is not present in Product''s attribute set.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544096) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544096)
;

-- 2018-05-29T18:19:46.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='',Updated=TO_TIMESTAMP('2018-05-29 18:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544096
;

-- 2018-05-29T18:19:46.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOnlyIfInProductAttributeSet', Name='OnlyIfInProductAttributeSet', Description='', Help=NULL WHERE AD_Element_ID=544096
;

-- 2018-05-29T18:19:46.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyIfInProductAttributeSet', Name='OnlyIfInProductAttributeSet', Description='', Help=NULL, AD_Element_ID=544096 WHERE UPPER(ColumnName)='ISONLYIFINPRODUCTATTRIBUTESET' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-29T18:19:46.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyIfInProductAttributeSet', Name='OnlyIfInProductAttributeSet', Description='', Help=NULL WHERE AD_Element_ID=544096 AND IsCentrallyMaintained='Y'
;

-- 2018-05-29T18:19:46.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='OnlyIfInProductAttributeSet', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544096) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544096)
;

-- 2018-05-29T18:20:09.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560200,544096,0,20,540507,'N','IsOnlyIfInProductAttributeSet',TO_TIMESTAMP('2018-05-29 18:20:09','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','de.metas.handlingunits',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','OnlyIfInProductAttributeSet',0,0,TO_TIMESTAMP('2018-05-29 18:20:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-05-29T18:20:09.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560200 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;




-- 2018-05-29T18:23:01.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560200,564378,0,540509,0,TO_TIMESTAMP('2018-05-29 18:23:01','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.handlingunits',0,'Y','Y','Y','N','N','N','N','N','OnlyIfInProductAttributeSet',150,150,0,1,1,TO_TIMESTAMP('2018-05-29 18:23:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-29T18:23:01.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564378 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-05-30T17:38:11.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560200,564379,0,540825,0,TO_TIMESTAMP('2018-05-30 17:38:11','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.handlingunits',0,'Y','Y','Y','N','N','N','N','N','OnlyIfInProductAttributeSet',150,150,0,1,1,TO_TIMESTAMP('2018-05-30 17:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-30T17:38:11.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564379 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-05-30T17:38:47.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,564379,0,540825,552135,540437,'F',TO_TIMESTAMP('2018-05-30 17:38:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','OnlyIfInProductAttributeSet',10,0,0,TO_TIMESTAMP('2018-05-30 17:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

