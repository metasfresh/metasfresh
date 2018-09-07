-- 2018-09-05T17:11:52.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Help='',Updated=TO_TIMESTAMP('2018-09-05 17:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=600
;

-- 2018-09-05T17:11:52.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Type', Name='Art', Description='', Help='' WHERE AD_Element_ID=600
;

-- 2018-09-05T17:11:52.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Type', Name='Art', Description='', Help='', AD_Element_ID=600 WHERE UPPER(ColumnName)='TYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-05T17:11:52.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Type', Name='Art', Description='', Help='' WHERE AD_Element_ID=600 AND IsCentrallyMaintained='Y'
;

-- 2018-09-05T17:11:52.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Art', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=600) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 600)
;

-- 2018-09-05T17:13:31.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540910,TO_TIMESTAMP('2018-09-05 17:13:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','AD_UI_ElementField',TO_TIMESTAMP('2018-09-05 17:13:31','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-09-05T17:13:31.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540910 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-09-05T17:13:37.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='Type_AD_UI_ElementField',Updated=TO_TIMESTAMP('2018-09-05 17:13:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540910
;

-- 2018-09-05T17:15:28.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540910,541687,TO_TIMESTAMP('2018-09-05 17:15:28','YYYY-MM-DD HH24:MI:SS'),100,'with this type, the additional field is displayed as part of a composite widget (e.g. the C_BPartner widget in the sales order window)','de.metas.ui.web','Y','widget',TO_TIMESTAMP('2018-09-05 17:15:28','YYYY-MM-DD HH24:MI:SS'),100,'widget','widget')
;

-- 2018-09-05T17:15:28.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541687 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-09-05T17:16:33.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540910,541689,TO_TIMESTAMP('2018-09-05 17:16:33','YYYY-MM-DD HH24:MI:SS'),100,'the field''s value is shown as an overlay','de.metas.ui.web','Y','tooltip',TO_TIMESTAMP('2018-09-05 17:16:33','YYYY-MM-DD HH24:MI:SS'),100,'tooltip','tooltip')
;

-- 2018-09-05T17:16:33.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541689 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-09-05T17:16:40.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='the additional field is displayed as part of a composite widget (e.g. the C_BPartner widget in the sales order window)',Updated=TO_TIMESTAMP('2018-09-05 17:16:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541687
;

-- 2018-09-05T17:16:45.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='the additional field''s value is shown as an overlay',Updated=TO_TIMESTAMP('2018-09-05 17:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541689
;

-- 2018-09-05T17:17:19.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560859,600,0,17,540910,540784,'Type',TO_TIMESTAMP('2018-09-05 17:17:19','YYYY-MM-DD HH24:MI:SS'),100,'N','widget','','D',7,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Art',0,0,TO_TIMESTAMP('2018-09-05 17:17:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-09-05T17:17:19.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560859 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-05T17:19:07.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544230,0,'TooltipType',TO_TIMESTAMP('2018-09-05 17:19:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Type of tooltip','Type of tooltip',TO_TIMESTAMP('2018-09-05 17:19:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-05T17:19:07.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544230 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-06T06:59:38.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementField SET IsActive='N',Updated=TO_TIMESTAMP('2018-09-06 06:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementField_ID=1000003
;

-- 2018-09-06T10:17:57.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementField SET IsActive='Y',Updated=TO_TIMESTAMP('2018-09-06 10:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementField_ID=1000003
;

-- 2018-09-06T17:29:04.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544231,0,'TooltipIconName',TO_TIMESTAMP('2018-09-06 17:29:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Tooltip Icon','Tooltip Icon',TO_TIMESTAMP('2018-09-06 17:29:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-06T17:29:04.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544231 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-06T17:29:27.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-06 17:29:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544231 AND AD_Language='de_CH'
;

-- 2018-09-06T17:29:27.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544231,'de_CH') 
;

-- 2018-09-06T17:29:32.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-06 17:29:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544231 AND AD_Language='en_US'
;

-- 2018-09-06T17:29:32.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544231,'en_US') 
;

-- 2018-09-06T17:30:34.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=544230
;

-- 2018-09-06T17:30:34.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=544230
;

-- 2018-09-06T17:32:03.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540912,TO_TIMESTAMP('2018-09-06 17:32:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','TooltipIconName',TO_TIMESTAMP('2018-09-06 17:32:03','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-09-06T17:32:03.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540912 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-09-06T17:33:39.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540912,541690,TO_TIMESTAMP('2018-09-06 17:33:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Text',TO_TIMESTAMP('2018-09-06 17:33:39','YYYY-MM-DD HH24:MI:SS'),100,'text','text')
;

-- 2018-09-06T17:33:39.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541690 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-09-06T17:33:44.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='TooltipIcon',Updated=TO_TIMESTAMP('2018-09-06 17:33:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540912
;

-- 2018-09-06T17:34:20.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560860,544231,0,17,540912,540784,'TooltipIconName',TO_TIMESTAMP('2018-09-06 17:34:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ui.web',50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Tooltip Icon',0,0,TO_TIMESTAMP('2018-09-06 17:34:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-09-06T17:34:20.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560860 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-06T17:34:29.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='TooltipIcon',Updated=TO_TIMESTAMP('2018-09-06 17:34:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544231
;

-- 2018-09-06T17:34:29.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TooltipIcon', Name='Tooltip Icon', Description=NULL, Help=NULL WHERE AD_Element_ID=544231
;

-- 2018-09-06T17:34:29.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TooltipIcon', Name='Tooltip Icon', Description=NULL, Help=NULL, AD_Element_ID=544231 WHERE UPPER(ColumnName)='TOOLTIPICON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-06T17:34:29.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TooltipIcon', Name='Tooltip Icon', Description=NULL, Help=NULL WHERE AD_Element_ID=544231 AND IsCentrallyMaintained='Y'
;

-- 2018-09-06T17:35:13.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type/none@=''tooltip''',Updated=TO_TIMESTAMP('2018-09-06 17:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560860
;

-- 2018-09-06T17:35:52.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='the additional field''s value is shown as a tooltip',Updated=TO_TIMESTAMP('2018-09-06 17:35:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541689
;

-- 2018-09-06T17:36:58.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560859,568717,0,540755,TO_TIMESTAMP('2018-09-06 17:36:58','YYYY-MM-DD HH24:MI:SS'),100,'',7,'D','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2018-09-06 17:36:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-06T17:36:58.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568717 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-06T17:36:58.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560860,568718,0,540755,TO_TIMESTAMP('2018-09-06 17:36:58','YYYY-MM-DD HH24:MI:SS'),100,50,'D','Y','N','N','N','N','N','N','N','Tooltip Icon',TO_TIMESTAMP('2018-09-06 17:36:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-06T17:36:58.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568718 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-06T17:38:07.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type/none@=''tooltip''', EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2018-09-06 17:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568718
;

-- 2018-09-06T17:38:19.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2018-09-06 17:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568717
;

-- 2018-09-06T17:38:36.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2018-09-06 17:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568717
;

-- 2018-09-06T17:38:44.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=60,Updated=TO_TIMESTAMP('2018-09-06 17:38:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568718
;

-- 2018-09-06T17:38:59.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', IsSameLine='Y',Updated=TO_TIMESTAMP('2018-09-06 17:38:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568718
;

-- 2018-09-06T17:39:13.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-09-06 17:39:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568717
;

-- 2018-09-06T17:52:05.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2018-09-06 17:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560860
;

-- 2018-09-07T07:48:09.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='TooltipIconName', Name='Tooltip Icon Name', PrintName='Tooltip Icon Name',Updated=TO_TIMESTAMP('2018-09-07 07:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544231
;

-- 2018-09-07T07:48:09.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TooltipIconName', Name='Tooltip Icon Name', Description=NULL, Help=NULL WHERE AD_Element_ID=544231
;

-- 2018-09-07T07:48:09.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TooltipIconName', Name='Tooltip Icon Name', Description=NULL, Help=NULL, AD_Element_ID=544231 WHERE UPPER(ColumnName)='TOOLTIPICONNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-07T07:48:09.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TooltipIconName', Name='Tooltip Icon Name', Description=NULL, Help=NULL WHERE AD_Element_ID=544231 AND IsCentrallyMaintained='Y'
;

-- 2018-09-07T07:48:09.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Tooltip Icon Name', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544231) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544231)
;

-- 2018-09-07T07:48:09.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Tooltip Icon Name', Name='Tooltip Icon Name' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544231)
;

-- 2018-09-07T07:48:19.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 07:48:19','YYYY-MM-DD HH24:MI:SS'),Name='Tooltip Icon Name',PrintName='Tooltip Icon Name' WHERE AD_Element_ID=544231 AND AD_Language='de_CH'
;

-- 2018-09-07T07:48:19.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544231,'de_CH') 
;

-- 2018-09-07T07:48:30.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 07:48:30','YYYY-MM-DD HH24:MI:SS'),Name='Tooltip icon name',PrintName='Tooltip icon name' WHERE AD_Element_ID=544231 AND AD_Language='en_US'
;

-- 2018-09-07T07:48:30.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544231,'en_US') 
;

-- 2018-09-07T13:05:20.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='TooltipIconName',Updated=TO_TIMESTAMP('2018-09-07 13:05:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540912
;

-- 2018-09-07T13:07:02.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='Important: the names of this list are forwarded to the frontend. When adding a new list item, make sure the frontend can handle it. ',Updated=TO_TIMESTAMP('2018-09-07 13:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540912
;

