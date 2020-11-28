-- 2018-08-25T16:31:24.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,CreatedBy,EntityType,IsOrderByValue,AD_Reference_ID,ValidationType,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'de.metas.vertical.pharma.vendor.gateway.msv3','N',540904,'L',0,'MSV3_Version',100,TO_TIMESTAMP('2018-08-25 16:31:24','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-08-25 16:31:24','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-08-25T16:31:24.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540904 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-08-25T16:31:55.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,CreatedBy,Created,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540904,0,'Y',100,TO_TIMESTAMP('2018-08-25 16:31:55','YYYY-MM-DD HH24:MI:SS'),'1','de.metas.vertical.pharma.vendor.gateway.msv3',541675,'1',0,'Version 1',100,TO_TIMESTAMP('2018-08-25 16:31:55','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-08-25T16:31:55.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541675 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-08-25T16:32:07.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,CreatedBy,Created,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540904,0,'Y',100,TO_TIMESTAMP('2018-08-25 16:32:07','YYYY-MM-DD HH24:MI:SS'),'2','de.metas.vertical.pharma.vendor.gateway.msv3',541676,'2',0,'Version 2',100,TO_TIMESTAMP('2018-08-25 16:32:07','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-08-25T16:32:07.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541676 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-08-25T16:33:36.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (17,'1','N','N','N','N',0,'Y',100,624,'Y','N','N','N','N','N','N','N','Y','N','N','N',540898,'N','The Version indicates the version of this table definition.',540904,'Version',560799,'N','Y','N','N','N','N','Version of the table definition',0,100,'Version','de.metas.vertical.pharma.vendor.gateway.msv3','N',1,0,0,TO_TIMESTAMP('2018-08-25 16:33:36','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-08-25 16:33:36','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-08-25T16:33:36.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560799 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-25T16:33:40.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MSV3_Vendor_Config','ALTER TABLE public.MSV3_Vendor_Config ADD COLUMN Version CHAR(1) DEFAULT ''1'' NOT NULL')
;

-- 2018-08-25T16:33:58.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (540990,'N','N','N','N','N',0,'Y',100,'N','de.metas.vertical.pharma.vendor.gateway.msv3','The Version indicates the version of this table definition.',565707,'N',560799,'Version of the table definition',0,'Version',100,1,TO_TIMESTAMP('2018-08-25 16:33:58','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-08-25 16:33:58','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-08-25T16:33:58.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565707 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-08-25T16:35:34.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (IsAllowFiltering,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,AD_Tab_ID,SeqNo_SideList,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES ('N',552528,565707,'N',100,0,100,'Y',541415,'Y','N','N',540990,0,0,'Version','F',TO_TIMESTAMP('2018-08-25 16:35:34','YYYY-MM-DD HH24:MI:SS'),25,0,TO_TIMESTAMP('2018-08-25 16:35:34','YYYY-MM-DD HH24:MI:SS'))
;

