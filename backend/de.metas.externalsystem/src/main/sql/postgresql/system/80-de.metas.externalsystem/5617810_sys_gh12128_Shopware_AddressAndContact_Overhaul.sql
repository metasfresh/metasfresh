-- 2021-12-07T12:09:04.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580369,0,'JSONPathEmail',TO_TIMESTAMP('2021-12-07 14:09:03','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path expression that specifies where within a customized Shopware address the email address can be found.','D','Y','Email JSON-Path','Email JSON-Path',TO_TIMESTAMP('2021-12-07 14:09:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-07T12:09:04.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580369 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-12-07T12:10:42.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578887,580369,0,10,541585,'JSONPathEmail',TO_TIMESTAMP('2021-12-07 14:10:42','YYYY-MM-DD HH24:MI:SS'),100,'N','JSON-Path expression that specifies where within a customized Shopware address the email address can be found.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Email JSON-Path','NP',0,0,TO_TIMESTAMP('2021-12-07 14:10:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-07T12:10:42.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578887 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-07T12:10:42.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580369) 
;

-- 2021-12-07T12:11:29.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578887,673876,0,543838,TO_TIMESTAMP('2021-12-07 14:11:29','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path expression that specifies where within a customized Shopware address the email address can be found.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Email JSON-Path',TO_TIMESTAMP('2021-12-07 14:11:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-07T12:11:29.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=673876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-07T12:11:29.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580369) 
;

-- 2021-12-07T12:11:29.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=673876
;

-- 2021-12-07T12:11:29.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(673876)
;

-- 2021-12-07T12:12:03.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=120,Updated=TO_TIMESTAMP('2021-12-07 14:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=673876
;

-- 2021-12-07T12:13:24.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,673876,0,543838,597833,545677,'F',TO_TIMESTAMP('2021-12-07 14:13:24','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path expression that specifies where within a customized Shopware address the email address can be found.','Y','N','N','Y','N','N','N',0,'Email JSON-Path',50,0,0,TO_TIMESTAMP('2021-12-07 14:13:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-07T12:14:24.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN JSONPathEmail VARCHAR(255)')
;

-- 2021-12-07T12:15:44.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545816, SeqNo=40,Updated=TO_TIMESTAMP('2021-12-07 14:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=597833
;

-- 2021-12-07T12:17:53.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578887,673877,0,543435,TO_TIMESTAMP('2021-12-07 14:17:53','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path expression that specifies where within a customized Shopware address the email address can be found.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Email JSON-Path',TO_TIMESTAMP('2021-12-07 14:17:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-07T12:17:53.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=673877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-07T12:17:53.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580369) 
;

-- 2021-12-07T12:17:53.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=673877
;

-- 2021-12-07T12:17:53.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(673877)
;

-- 2021-12-07T12:18:05.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2021-12-07 14:18:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=632170
;

-- 2021-12-07T12:19:42.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,673877,0,543435,597834,545817,'F',TO_TIMESTAMP('2021-12-07 14:19:42','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path expression that specifies where within a customized Shopware address the email address can be found.','Y','N','N','Y','N','N','N',0,'Email JSON-Path',40,0,0,TO_TIMESTAMP('2021-12-07 14:19:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-07T12:22:34.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=NULL,Updated=TO_TIMESTAMP('2021-12-07 14:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=632170
;

-- 2021-12-07T12:22:45.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2021-12-07 14:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=673877
;

-- 2021-12-08T12:58:27.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578889,543350,0,10,540244,'BPartnerName',TO_TIMESTAMP('2021-12-08 14:58:27','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Name Geschäftspartner',0,0,TO_TIMESTAMP('2021-12-08 14:58:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-08T12:58:27.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578889 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-08T12:58:27.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(543350) 
;

-- 2021-12-08T12:59:40.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578890,505,0,10,540244,'Phone',TO_TIMESTAMP('2021-12-08 14:59:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Beschreibt eine Telefon Nummer','de.metas.ordercandidate',0,40,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Telefon',0,0,TO_TIMESTAMP('2021-12-08 14:59:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-08T12:59:40.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578890 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-08T12:59:40.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(505) 
;

-- 2021-12-08T13:00:39.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578891,881,0,10,540244,'EMail',TO_TIMESTAMP('2021-12-08 15:00:39','YYYY-MM-DD HH24:MI:SS'),100,'N','EMail-Adresse','de.metas.ordercandidate',0,200,'The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'eMail',0,0,TO_TIMESTAMP('2021-12-08 15:00:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-08T13:00:39.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578891 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-08T13:00:39.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(881) 
;

-- 2021-12-08T13:00:52.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN EMail VARCHAR(200)')
;

-- 2021-12-08T13:01:09.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN BPartnerName VARCHAR(255)')
;

-- 2021-12-08T13:01:28.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN Phone VARCHAR(40)')
;

-- 2021-12-08T13:25:03.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578892,543350,0,10,259,'BPartnerName',TO_TIMESTAMP('2021-12-08 15:25:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Name Geschäftspartner',0,0,TO_TIMESTAMP('2021-12-08 15:25:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-08T13:25:03.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578892 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-08T13:25:03.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(543350) 
;

-- 2021-12-08T13:25:56.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN BPartnerName VARCHAR(255)')
;

-- 2021-12-08T13:26:34.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578893,881,0,10,259,'EMail',TO_TIMESTAMP('2021-12-08 15:26:34','YYYY-MM-DD HH24:MI:SS'),100,'N','EMail-Adresse','D',0,200,'The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'eMail',0,0,TO_TIMESTAMP('2021-12-08 15:26:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-08T13:26:34.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578893 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-08T13:26:34.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(881) 
;

-- 2021-12-08T13:26:36.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN EMail VARCHAR(200)')
;

-- 2021-12-08T13:27:02.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578894,505,0,10,259,'Phone',TO_TIMESTAMP('2021-12-08 15:27:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Beschreibt eine Telefon Nummer','D',0,40,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Telefon',0,0,TO_TIMESTAMP('2021-12-08 15:27:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-08T13:27:02.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578894 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-08T13:27:02.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(505) 
;

-- 2021-12-08T13:27:04.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN Phone VARCHAR(40)')
;

-- 2021-12-08T13:31:38.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','BPartnerName','VARCHAR(255)',null,null)
;

-- 2021-12-08T13:33:25.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2021-12-08 15:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578892
;

-- 2021-12-08T13:33:30.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order','BPartnerName','VARCHAR(255)',null,null)
;

-- 2021-12-08T18:12:50.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_OLCandAggAndOrder (AD_Client_ID,AD_Column_OLCand_ID,AD_Org_ID,C_OLCandAggAndOrder_ID,C_OLCandProcessor_ID,Created,CreatedBy,GroupBy,IsActive,SplitOrder,Updated,UpdatedBy) VALUES (1000000,544977,1000000,540021,1000003,TO_TIMESTAMP('2021-12-08 20:12:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','N',TO_TIMESTAMP('2021-12-08 20:12:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:12:54.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCandAggAndOrder SET SplitOrder='Y',Updated=TO_TIMESTAMP('2021-12-08 20:12:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540021
;

-- 2021-12-08T18:12:57.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCandAggAndOrder SET OrderBySeqNo=220,Updated=TO_TIMESTAMP('2021-12-08 20:12:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540021
;

-- 2021-12-08T18:16:48.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCandAggAndOrder SET AD_Column_OLCand_ID=578889,Updated=TO_TIMESTAMP('2021-12-08 20:16:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540021
;

-- 2021-12-08T18:17:38.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_OLCandAggAndOrder (AD_Client_ID,AD_Column_OLCand_ID,AD_Org_ID,C_OLCandAggAndOrder_ID,C_OLCandProcessor_ID,Created,CreatedBy,GroupBy,IsActive,SplitOrder,Updated,UpdatedBy) VALUES (1000000,578891,1000000,540022,1000003,TO_TIMESTAMP('2021-12-08 20:17:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','N',TO_TIMESTAMP('2021-12-08 20:17:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:17:40.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCandAggAndOrder SET SplitOrder='Y',Updated=TO_TIMESTAMP('2021-12-08 20:17:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540022
;

-- 2021-12-08T18:17:42.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCandAggAndOrder SET OrderBySeqNo=230,Updated=TO_TIMESTAMP('2021-12-08 20:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540022
;

-- 2021-12-08T18:18:25.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_OLCandAggAndOrder (AD_Client_ID,AD_Column_OLCand_ID,AD_Org_ID,C_OLCandAggAndOrder_ID,C_OLCandProcessor_ID,Created,CreatedBy,GroupBy,IsActive,SplitOrder,Updated,UpdatedBy) VALUES (1000000,578890,1000000,540023,1000003,TO_TIMESTAMP('2021-12-08 20:18:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','N',TO_TIMESTAMP('2021-12-08 20:18:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:18:27.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCandAggAndOrder SET SplitOrder='Y',Updated=TO_TIMESTAMP('2021-12-08 20:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540023
;

-- 2021-12-08T18:18:29.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCandAggAndOrder SET OrderBySeqNo=240,Updated=TO_TIMESTAMP('2021-12-08 20:18:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540023
;

-- 2021-12-08T18:27:18.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Kunden EMail JSON-Path', PrintName='Kunden EMail JSON-Path',Updated=TO_TIMESTAMP('2021-12-08 20:27:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='de_CH'
;

-- 2021-12-08T18:27:18.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'de_CH') 
;

-- 2021-12-08T18:27:31.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Kunden EMail JSON-Path', PrintName='Kunden EMail JSON-Path',Updated=TO_TIMESTAMP('2021-12-08 20:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='de_DE'
;

-- 2021-12-08T18:27:31.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'de_DE') 
;

-- 2021-12-08T18:27:31.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580369,'de_DE') 
;

-- 2021-12-08T18:27:31.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='JSONPathEmail', Name='Kunden EMail JSON-Path', Description='', Help=NULL WHERE AD_Element_ID=580369
;

-- 2021-12-08T18:27:31.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathEmail', Name='Kunden EMail JSON-Path', Description='', Help=NULL, AD_Element_ID=580369 WHERE UPPER(ColumnName)='JSONPATHEMAIL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-08T18:27:31.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='JSONPathEmail', Name='Kunden EMail JSON-Path', Description='', Help=NULL WHERE AD_Element_ID=580369 AND IsCentrallyMaintained='Y'
;

-- 2021-12-08T18:27:31.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kunden EMail JSON-Path', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580369) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580369)
;

-- 2021-12-08T18:27:31.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kunden EMail JSON-Path', Name='Kunden EMail JSON-Path' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580369)
;

-- 2021-12-08T18:27:31.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kunden EMail JSON-Path', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-08T18:27:31.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kunden EMail JSON-Path', Description='', Help=NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-08T18:27:31.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kunden EMail JSON-Path', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580369
;

-- 2021-12-08T18:27:43.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Customer email JSON path', PrintName='Customer email JSON path',Updated=TO_TIMESTAMP('2021-12-08 20:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='en_US'
;

-- 2021-12-08T18:27:43.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'en_US') 
;

-- 2021-12-08T18:27:51.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Kunden EMail JSON-Path', PrintName='Kunden EMail JSON-Path',Updated=TO_TIMESTAMP('2021-12-08 20:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580369 AND AD_Language='nl_NL'
;

-- 2021-12-08T18:27:51.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580369,'nl_NL') 
;

-- 2021-12-08T18:34:57.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578889,675194,0,540282,TO_TIMESTAMP('2021-12-08 20:34:57','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Name Geschäftspartner',TO_TIMESTAMP('2021-12-08 20:34:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:34:57.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675194 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-08T18:34:57.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543350) 
;

-- 2021-12-08T18:34:57.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675194
;

-- 2021-12-08T18:34:57.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675194)
;

-- 2021-12-08T18:35:11.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578891,675195,0,540282,TO_TIMESTAMP('2021-12-08 20:35:11','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',200,'de.metas.ordercandidate','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','N','N','N','N','N','eMail',TO_TIMESTAMP('2021-12-08 20:35:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:35:11.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675195 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-08T18:35:11.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881) 
;

-- 2021-12-08T18:35:11.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675195
;

-- 2021-12-08T18:35:11.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675195)
;

-- 2021-12-08T18:35:21.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578890,675196,0,540282,TO_TIMESTAMP('2021-12-08 20:35:21','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer',40,'de.metas.ordercandidate','','Y','N','N','N','N','N','N','N','Telefon',TO_TIMESTAMP('2021-12-08 20:35:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:35:21.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675196 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-08T18:35:21.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505) 
;

-- 2021-12-08T18:35:21.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675196
;

-- 2021-12-08T18:35:21.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675196)
;

-- 2021-12-08T18:37:15.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675194,0,540282,598398,540962,'F',TO_TIMESTAMP('2021-12-08 20:37:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Name Geschäftspartner',670,0,0,TO_TIMESTAMP('2021-12-08 20:37:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:37:36.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675195,0,540282,598399,540962,'F',TO_TIMESTAMP('2021-12-08 20:37:35','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','Y','N','Y','N','N','N',0,'eMail',680,0,0,TO_TIMESTAMP('2021-12-08 20:37:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:37:49.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675196,0,540282,598400,540962,'F',TO_TIMESTAMP('2021-12-08 20:37:49','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer','Y','Y','N','Y','N','N','N',0,'Telefon',690,0,0,TO_TIMESTAMP('2021-12-08 20:37:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:48:12.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578892,675200,0,186,TO_TIMESTAMP('2021-12-08 20:48:12','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Name Geschäftspartner',TO_TIMESTAMP('2021-12-08 20:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:48:12.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-08T18:48:12.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543350) 
;

-- 2021-12-08T18:48:12.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675200
;

-- 2021-12-08T18:48:12.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675200)
;

-- 2021-12-08T18:48:23.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578893,675201,0,186,TO_TIMESTAMP('2021-12-08 20:48:23','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','N','N','N','N','N','eMail',TO_TIMESTAMP('2021-12-08 20:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:48:23.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-08T18:48:23.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881) 
;

-- 2021-12-08T18:48:23.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675201
;

-- 2021-12-08T18:48:23.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675201)
;

-- 2021-12-08T18:48:34.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578894,675202,0,186,TO_TIMESTAMP('2021-12-08 20:48:34','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer',40,'D','','Y','N','N','N','N','N','N','N','Telefon',TO_TIMESTAMP('2021-12-08 20:48:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:48:34.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-08T18:48:34.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505) 
;

-- 2021-12-08T18:48:34.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675202
;

-- 2021-12-08T18:48:34.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675202)
;

-- 2021-12-08T18:48:56.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675200,0,186,598404,540499,'F',TO_TIMESTAMP('2021-12-08 20:48:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Name Geschäftspartner',440,0,0,TO_TIMESTAMP('2021-12-08 20:48:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:49:08.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675201,0,186,598405,540499,'F',TO_TIMESTAMP('2021-12-08 20:49:08','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','Y','N','Y','N','N','N',0,'eMail',450,0,0,TO_TIMESTAMP('2021-12-08 20:49:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-08T18:49:20.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675202,0,186,598406,540499,'F',TO_TIMESTAMP('2021-12-08 20:49:20','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer','Y','Y','N','Y','N','N','N',0,'Telefon',460,0,0,TO_TIMESTAMP('2021-12-08 20:49:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:02:40.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564643,675215,0,222,TO_TIMESTAMP('2021-12-09 18:02:40','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','N','N','N','N','N','eMail',TO_TIMESTAMP('2021-12-09 18:02:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:02:40.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=675215 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-09T16:02:40.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881)
;

-- 2021-12-09T16:02:40.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=675215
;

-- 2021-12-09T16:02:40.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(675215)
;

-- 2021-12-09T16:03:11.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,675215,0,222,598418,1000034,'F',TO_TIMESTAMP('2021-12-09 18:03:11','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','Y','N','N','N',0,'eMail',160,0,0,TO_TIMESTAMP('2021-12-09 18:03:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:03:27.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,2191,0,222,598419,1000034,'F',TO_TIMESTAMP('2021-12-09 18:03:27','YYYY-MM-DD HH24:MI:SS'),100,'Beschreibt eine Telefon Nummer','Y','N','N','Y','N','N','N',0,'Telefon',170,0,0,TO_TIMESTAMP('2021-12-09 18:03:27','YYYY-MM-DD HH24:MI:SS'),100)
;