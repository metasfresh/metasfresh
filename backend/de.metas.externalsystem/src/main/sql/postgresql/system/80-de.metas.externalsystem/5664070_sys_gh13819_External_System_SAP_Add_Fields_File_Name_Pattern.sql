-- 2022-11-11T17:15:13.992Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581667,0,'SFTP_Product_FileName_Pattern',TO_TIMESTAMP('2022-11-11 19:15:13','YYYY-MM-DD HH24:MI:SS'),100,'The pattern used for ','U','Y','SFTP Product FIle Name Pattern','SFTP Product FIle Name Pattern',TO_TIMESTAMP('2022-11-11 19:15:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-11T17:15:14.004Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581667 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-11T17:19:52.337Z
UPDATE AD_Element_Trl SET Description='Das Muster, das für die Produktdateinamen verwendet wird, wenn mehrere Dateitypen vom gleichen Speicherort abgerufen werden.', Name='SFTP-Produkt Dateinamen-Muster', PrintName='SFTP-Produkt Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-11 19:19:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_CH'
;

-- 2022-11-11T17:19:52.368Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_CH') 
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-11T17:20:04.600Z
UPDATE AD_Element_Trl SET Name='SFTP-Produkt Dateinamen-Muster', PrintName='SFTP-Produkt Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-11 19:20:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_DE'
;

-- 2022-11-11T17:20:04.603Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581667,'de_DE') 
;

-- 2022-11-11T17:20:04.604Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_DE') 
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-11T17:20:12.857Z
UPDATE AD_Element_Trl SET Description='Das Muster, das für die Produktdateinamen verwendet wird, wenn mehrere Dateitypen vom gleichen Speicherort abgerufen werden.',Updated=TO_TIMESTAMP('2022-11-11 19:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_DE'
;

-- 2022-11-11T17:20:12.859Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581667,'de_DE') 
;

-- 2022-11-11T17:20:12.863Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_DE') 
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-11T17:20:28.386Z
UPDATE AD_Element_Trl SET Description='The pattern used for the product file names when fetching multiple types of files from the same location.',Updated=TO_TIMESTAMP('2022-11-11 19:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='en_US'
;

-- 2022-11-11T17:20:28.388Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'en_US') 
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-11T17:20:32.537Z
UPDATE AD_Element_Trl SET Description='The pattern used for the product file names when fetching multiple types of files from the same location.',Updated=TO_TIMESTAMP('2022-11-11 19:20:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='nl_NL'
;

-- 2022-11-11T17:20:32.539Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'nl_NL') 
;

-- 2022-11-11T17:20:45.602Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-11 19:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667
;

-- 2022-11-11T17:20:45.604Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_DE') 
;

-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-11T17:22:18.208Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584935,581667,0,10,542238,'SFTP_Product_FileName_Pattern',TO_TIMESTAMP('2022-11-11 19:22:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Das Muster, das für die Produktdateinamen verwendet wird, wenn mehrere Dateitypen vom gleichen Speicherort abgerufen werden.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SFTP-Produkt Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-11 19:22:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-11T17:22:18.216Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584935 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-11T17:22:18.224Z
/* DDL */  select update_Column_Translation_From_AD_Element(581667) 
;

-- 2022-11-11T17:24:30.331Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581668,0,'SFTP_BPartner_FileName_Pattern',TO_TIMESTAMP('2022-11-11 19:24:30','YYYY-MM-DD HH24:MI:SS'),100,'Das Muster, das für die Dateinamen der Geschäftspartner verwendet wird, wenn mehrere Dateitypen vom gleichen Speicherort abgerufen werden.','D','Y','SFTP-Geschäftspartner Dateinamen-Muster','SFTP-Geschäftspartner Dateinamen-Muster',TO_TIMESTAMP('2022-11-11 19:24:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-11T17:24:30.333Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581668 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-11T17:24:48.762Z
UPDATE AD_Element_Trl SET Name='SFTP Business Partner File Name Pattern', PrintName='SFTP Business Partner File Name Pattern',Updated=TO_TIMESTAMP('2022-11-11 19:24:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='en_US'
;

-- 2022-11-11T17:24:48.764Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'en_US') 
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-11T17:25:02.625Z
UPDATE AD_Element_Trl SET Description='The pattern used for the business partner file names when fetching multiple types of files from the same location.', Name='SFTP Business Partner File Name Pattern', PrintName='SFTP Business Partner File Name Pattern',Updated=TO_TIMESTAMP('2022-11-11 19:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='nl_NL'
;

-- 2022-11-11T17:25:02.627Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'nl_NL') 
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-11T17:25:06.433Z
UPDATE AD_Element_Trl SET Description='The pattern used for the business partner file names when fetching multiple types of files from the same location.',Updated=TO_TIMESTAMP('2022-11-11 19:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='en_US'
;

-- 2022-11-11T17:25:06.435Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'en_US') 
;

-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_FileName_Pattern
-- 2022-11-11T17:25:32.041Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584936,581668,0,10,542238,'SFTP_BPartner_FileName_Pattern',TO_TIMESTAMP('2022-11-11 19:25:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Das Muster, das für die Dateinamen der Geschäftspartner verwendet wird, wenn mehrere Dateitypen vom gleichen Speicherort abgerufen werden.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SFTP-Geschäftspartner Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-11 19:25:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-11T17:25:32.047Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584936 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-11T17:25:32.053Z
/* DDL */  select update_Column_Translation_From_AD_Element(581668) 
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-11T17:27:15.940Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584935,708028,0,546647,0,TO_TIMESTAMP('2022-11-11 19:27:15','YYYY-MM-DD HH24:MI:SS'),100,'Das Muster, das für die Produktdateinamen verwendet wird, wenn mehrere Dateitypen vom gleichen Speicherort abgerufen werden.',0,'U',0,'Y','Y','Y','N','N','N','N','N','SFTP-Produkt Dateinamen-Muster',0,110,0,1,1,TO_TIMESTAMP('2022-11-11 19:27:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-11T17:27:15.948Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-11T17:27:15.954Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581667) 
;

-- 2022-11-11T17:27:15.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708028
;

-- 2022-11-11T17:27:15.976Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708028)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-11T17:27:51.270Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem', IsDisplayed='N',Updated=TO_TIMESTAMP('2022-11-11 19:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708028
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-11T17:27:59.856Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-11-11 19:27:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708028
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_FileName_Pattern
-- 2022-11-11T17:28:35.266Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584936,708029,0,546647,0,TO_TIMESTAMP('2022-11-11 19:28:35','YYYY-MM-DD HH24:MI:SS'),100,'Das Muster, das für die Dateinamen der Geschäftspartner verwendet wird, wenn mehrere Dateitypen vom gleichen Speicherort abgerufen werden.',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','SFTP-Geschäftspartner Dateinamen-Muster',0,120,0,1,1,TO_TIMESTAMP('2022-11-11 19:28:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-11T17:28:35.269Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-11T17:28:35.271Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581668) 
;

-- 2022-11-11T17:28:35.275Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708029
;

-- 2022-11-11T17:28:35.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708029)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-11T17:29:29.520Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708028,0,546647,613442,549954,'F',TO_TIMESTAMP('2022-11-11 19:29:29','YYYY-MM-DD HH24:MI:SS'),100,'Das Muster, das für die Produktdateinamen verwendet wird, wenn mehrere Dateitypen vom gleichen Speicherort abgerufen werden.','Y','N','N','Y','N','N','N',0,'SFTP-Produkt Dateinamen-Muster',100,0,0,TO_TIMESTAMP('2022-11-11 19:29:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_FileName_Pattern
-- 2022-11-11T17:29:45.713Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708029,0,546647,613443,549954,'F',TO_TIMESTAMP('2022-11-11 19:29:45','YYYY-MM-DD HH24:MI:SS'),100,'Das Muster, das für die Dateinamen der Geschäftspartner verwendet wird, wenn mehrere Dateitypen vom gleichen Speicherort abgerufen werden.','Y','N','N','Y','N','N','N',0,'SFTP-Geschäftspartner Dateinamen-Muster',110,0,0,TO_TIMESTAMP('2022-11-11 19:29:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-11T18:18:20.225Z
UPDATE AD_Element_Trl SET Name='SFTP Product File Name Pattern', PrintName='SFTP Product File Name Pattern',Updated=TO_TIMESTAMP('2022-11-11 20:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='en_US'
;

-- 2022-11-11T18:18:20.227Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'en_US') 
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-11T18:18:32.900Z
UPDATE AD_Element_Trl SET Name='SFTP Product File Name Pattern', PrintName='SFTP Product File Name Pattern',Updated=TO_TIMESTAMP('2022-11-11 20:18:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='nl_NL'
;

-- 2022-11-11T18:18:32.902Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'nl_NL') 
;

-- 2022-11-11T18:30:08.664Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN SFTP_Product_FileName_Pattern VARCHAR(255)')
;

-- 2022-11-11T18:34:02.078Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN SFTP_BPartner_FileName_Pattern VARCHAR(255)')
;

-- 2022-11-11T18:34:16.306Z
INSERT INTO t_alter_column values('externalsystem_config_sap','SFTP_Product_FileName_Pattern','VARCHAR(255)',null,null)
;

-- 2022-11-11T18:36:28.828Z
INSERT INTO t_alter_column values('externalsystem_config_sap','SFTP_BPartner_FileName_Pattern','VARCHAR(255)',null,null)
;

-- UI Column: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10
-- UI Element Group: product
-- 2022-11-14T16:55:04.327Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546411,550019,TO_TIMESTAMP('2022-11-14 18:55:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','product',20,TO_TIMESTAMP('2022-11-14 18:55:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10
-- UI Element Group: partner
-- 2022-11-14T16:55:11.232Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546411,550020,TO_TIMESTAMP('2022-11-14 18:55:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','partner',30,TO_TIMESTAMP('2022-11-14 18:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10
-- UI Element Group: main
-- 2022-11-14T16:55:18.031Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-11-14 18:55:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549954
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> product.SFTP-Produkt-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_Product_TargetDirectory
-- 2022-11-14T16:55:40.772Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550019, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-14 18:55:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613230
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> product.SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-14T16:55:58.174Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550019, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-14 18:55:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613442
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> partner.SFTP-Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_TargetDirectory
-- 2022-11-14T16:56:16.199Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550020, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-14 18:56:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613302
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> partner.SFTP-Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_FileName_Pattern
-- 2022-11-14T16:56:27.265Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550020, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-14 18:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613443
;

-- Element: SFTP_HostName
-- 2022-11-14T16:58:04.221Z
UPDATE AD_Element_Trl SET Description='Hostname of the sftp server.',Updated=TO_TIMESTAMP('2022-11-14 18:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='en_US'
;

-- 2022-11-14T16:58:04.256Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'en_US')
;

-- Element: SFTP_HostName
-- 2022-11-14T16:59:07.194Z
UPDATE AD_Element_Trl SET Description='Hostname des sftp-Servers.',Updated=TO_TIMESTAMP('2022-11-14 18:59:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='de_CH'
;

-- 2022-11-14T16:59:07.195Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'de_CH')
;

-- Element: SFTP_HostName
-- 2022-11-14T16:59:09.211Z
UPDATE AD_Element_Trl SET Description='Hostname des sftp-Servers.',Updated=TO_TIMESTAMP('2022-11-14 18:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='de_DE'
;

-- 2022-11-14T16:59:09.212Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581553,'de_DE')
;

-- 2022-11-14T16:59:09.226Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'de_DE')
;

-- Element: SFTP_HostName
-- 2022-11-14T16:59:11.883Z
UPDATE AD_Element_Trl SET Description='Hostname des sftp-Servers.',Updated=TO_TIMESTAMP('2022-11-14 18:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='nl_NL'
;

-- 2022-11-14T16:59:11.884Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'nl_NL')
;

-- Element: SFTP_Password
-- 2022-11-14T17:01:26.877Z
UPDATE AD_Element_Trl SET Description='The password used for sftp server authentcation.',Updated=TO_TIMESTAMP('2022-11-14 19:01:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='en_US'
;

-- 2022-11-14T17:01:26.878Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'en_US')
;

-- Element: SFTP_Password
-- 2022-11-14T17:01:27.341Z
UPDATE AD_Element_Trl SET Description='Das Passwort, das für die Authentifizierung des sftp-Servers verwendet wird.',Updated=TO_TIMESTAMP('2022-11-14 19:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='de_CH'
;

-- 2022-11-14T17:01:27.346Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'de_CH')
;

-- Element: SFTP_Password
-- 2022-11-14T17:01:29.285Z
UPDATE AD_Element_Trl SET Description='Das Passwort, das für die Authentifizierung des sftp-Servers verwendet wird.',Updated=TO_TIMESTAMP('2022-11-14 19:01:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='de_DE'
;

-- 2022-11-14T17:01:29.286Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581554,'de_DE')
;

-- 2022-11-14T17:01:29.288Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'de_DE')
;

-- Element: SFTP_Password
-- 2022-11-14T17:01:35.208Z
UPDATE AD_Element_Trl SET Description='Das Passwort, das für die Authentifizierung des sftp-Servers verwendet wird.',Updated=TO_TIMESTAMP('2022-11-14 19:01:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:01:35.210Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'nl_NL')
;

-- Element: SFTP_Port
-- 2022-11-14T17:02:34.539Z
UPDATE AD_Element_Trl SET Description='SFTP-Port',Updated=TO_TIMESTAMP('2022-11-14 19:02:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='de_CH'
;

-- 2022-11-14T17:02:34.541Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'de_CH')
;

-- Element: SFTP_Port
-- 2022-11-14T17:02:36.944Z
UPDATE AD_Element_Trl SET Description='SFTP-Port',Updated=TO_TIMESTAMP('2022-11-14 19:02:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='de_DE'
;

-- 2022-11-14T17:02:36.946Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581555,'de_DE')
;

-- 2022-11-14T17:02:36.947Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'de_DE')
;

-- Element: SFTP_Port
-- 2022-11-14T17:02:40.034Z
UPDATE AD_Element_Trl SET Description='SFTP-Port',Updated=TO_TIMESTAMP('2022-11-14 19:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='en_US'
;

-- 2022-11-14T17:02:40.036Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'en_US')
;

-- Element: SFTP_Port
-- 2022-11-14T17:02:43.600Z
UPDATE AD_Element_Trl SET Description='SFTP-Port',Updated=TO_TIMESTAMP('2022-11-14 19:02:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:02:43.601Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'nl_NL')
;

-- Element: SFTP_Username
-- 2022-11-14T17:04:16.664Z
UPDATE AD_Element_Trl SET Description='Der für die Authentifizierung am SFTP-Server verwendete Benutzername.',Updated=TO_TIMESTAMP('2022-11-14 19:04:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='de_CH'
;

-- 2022-11-14T17:04:16.665Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'de_CH')
;

-- Element: SFTP_Username
-- 2022-11-14T17:04:20.513Z
UPDATE AD_Element_Trl SET Description='Der für die Authentifizierung am SFTP-Server verwendete Benutzername.',Updated=TO_TIMESTAMP('2022-11-14 19:04:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='en_US'
;

-- 2022-11-14T17:04:20.515Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'en_US')
;

-- Element: SFTP_Username
-- 2022-11-14T17:04:42.128Z
UPDATE AD_Element_Trl SET Description='Der für die Authentifizierung am SFTP-Server verwendete Benutzername.',Updated=TO_TIMESTAMP('2022-11-14 19:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='de_DE'
;

-- 2022-11-14T17:04:42.129Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581556,'de_DE')
;

-- 2022-11-14T17:04:42.130Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'de_DE')
;

-- Element: SFTP_Username
-- 2022-11-14T17:05:56.190Z
UPDATE AD_Element_Trl SET Description='Username used for sftp server authentcation.',Updated=TO_TIMESTAMP('2022-11-14 19:05:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='en_US'
;

-- 2022-11-14T17:05:56.192Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'en_US')
;

-- Element: SFTP_Username
-- 2022-11-14T17:05:57.485Z
UPDATE AD_Element_Trl SET Description='Benutzername, der für die Authentifizierung am sftp-Server verwendet wird.',Updated=TO_TIMESTAMP('2022-11-14 19:05:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='de_CH'
;

-- 2022-11-14T17:05:57.486Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'de_CH')
;

-- Element: SFTP_Username
-- 2022-11-14T17:05:59.031Z
UPDATE AD_Element_Trl SET Description='Benutzername, der für die Authentifizierung am sftp-Server verwendet wird.',Updated=TO_TIMESTAMP('2022-11-14 19:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='de_DE'
;

-- 2022-11-14T17:05:59.032Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581556,'de_DE')
;

-- 2022-11-14T17:05:59.033Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'de_DE')
;

-- Element: SFTP_Username
-- 2022-11-14T17:06:02.163Z
UPDATE AD_Element_Trl SET Description='Benutzername, der für die Authentifizierung am sftp-Server verwendet wird.',Updated=TO_TIMESTAMP('2022-11-14 19:06:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:06:02.164Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'nl_NL')
;

-- Element: ProcessedDirectory
-- 2022-11-14T17:08:37.015Z
UPDATE AD_Element_Trl SET Description='Defines where files should be moved after being successfully processed. (The path should be relative to the current sftp target location)',Updated=TO_TIMESTAMP('2022-11-14 19:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='en_US'
;

-- 2022-11-14T17:08:37.017Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'en_US')
;

-- Element: ErroredDirectory
-- 2022-11-14T17:09:48.892Z
UPDATE AD_Element_Trl SET Description='Defines where files should be moved after attempting to process them with error. ( The path should be relative to the current sftp target location )',Updated=TO_TIMESTAMP('2022-11-14 19:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='en_US'
;

-- 2022-11-14T17:09:48.893Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'en_US')
;

-- Element: ErroredDirectory
-- 2022-11-14T17:11:04.321Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. ( Der Pfad sollte relativ zum aktuellen sftp-Zielort sein )',Updated=TO_TIMESTAMP('2022-11-14 19:11:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_CH'
;

-- 2022-11-14T17:11:04.323Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_CH')
;

-- Element: ErroredDirectory
-- 2022-11-14T17:11:31.764Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. ( Der Pfad sollte relativ zum aktuellen sftp-Zielort sein )',Updated=TO_TIMESTAMP('2022-11-14 19:11:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='en_US'
;

-- 2022-11-14T17:11:31.767Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'en_US')
;

-- Element: ErroredDirectory
-- 2022-11-14T17:11:40.757Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. ( Der Pfad sollte relativ zum aktuellen sftp-Zielort sein )',Updated=TO_TIMESTAMP('2022-11-14 19:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:11:40.758Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'nl_NL')
;

-- Element: ErroredDirectory
-- 2022-11-14T17:11:46.246Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. ( Der Pfad sollte relativ zum aktuellen sftp-Zielort sein )',Updated=TO_TIMESTAMP('2022-11-14 19:11:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_DE'
;

-- 2022-11-14T17:11:46.247Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581584,'de_DE')
;

-- 2022-11-14T17:11:46.248Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_DE')
;

-- Element: ProcessedDirectory
-- 2022-11-14T17:12:41.338Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen sftp-Zielort sein)',Updated=TO_TIMESTAMP('2022-11-14 19:12:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:12:41.339Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'nl_NL')
;

-- Element: ProcessedDirectory
-- 2022-11-14T17:12:46.302Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen sftp-Zielort sein)',Updated=TO_TIMESTAMP('2022-11-14 19:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='de_DE'
;

-- 2022-11-14T17:12:46.303Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581583,'de_DE')
;

-- 2022-11-14T17:12:46.313Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'de_DE')
;

-- Element: ProcessedDirectory
-- 2022-11-14T17:12:49.819Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen sftp-Zielort sein)',Updated=TO_TIMESTAMP('2022-11-14 19:12:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='de_CH'
;

-- 2022-11-14T17:12:49.820Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'de_CH')
;

-- Element: SFTP_Password
-- 2022-11-14T17:14:15.141Z
UPDATE AD_Element_Trl SET Description='Password used for sftp server authentcation.',Updated=TO_TIMESTAMP('2022-11-14 19:14:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='en_US'
;

-- 2022-11-14T17:14:15.143Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'en_US')
;

-- Element: ErroredDirectory
-- 2022-11-14T17:17:40.986Z
UPDATE AD_Element_Trl SET Description='Defines where files should be moved after being processed with error. (The path should be relative to the current sftp target location) ( Der Pfad sollte relativ zum aktuellen sftp-Zielort sein )',Updated=TO_TIMESTAMP('2022-11-14 19:17:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='en_US'
;

-- 2022-11-14T17:17:40.987Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'en_US')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:20:36.541Z
UPDATE AD_Element_Trl SET Description='Directory used to pull business partners from the sftp server. (If not set, the files will be pulled from the root location of the sftp server.)',Updated=TO_TIMESTAMP('2022-11-14 19:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='en_US'
;

-- 2022-11-14T17:20:36.542Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'en_US')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:20:39.150Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Wenn nicht festgelegt, werden die Dateien vom Stammverzeichnis des sftp-Servers abgerufen).',Updated=TO_TIMESTAMP('2022-11-14 19:20:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='de_DE'
;

-- 2022-11-14T17:20:39.151Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581604,'de_DE')
;

-- 2022-11-14T17:20:39.152Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_DE')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:21:00.167Z
UPDATE AD_Element_Trl SET Description='Das Verzeichnis, das verwendet wird, um Geschäftspartner vom sftp-Server zu holen. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',Updated=TO_TIMESTAMP('2022-11-14 19:21:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:21:00.168Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'nl_NL')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:21:29.101Z
UPDATE AD_Element_Trl SET Description='Directory used to pull business partners from the sftp server. (If not set, the files will be pulled from the root location of the sftp connection.)',Updated=TO_TIMESTAMP('2022-11-14 19:21:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='en_US'
;

-- 2022-11-14T17:21:29.102Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'en_US')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:21:47.207Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen).',Updated=TO_TIMESTAMP('2022-11-14 19:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='de_CH'
;

-- 2022-11-14T17:21:47.208Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_CH')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:21:48.646Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen).',Updated=TO_TIMESTAMP('2022-11-14 19:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='de_DE'
;

-- 2022-11-14T17:21:48.647Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581604,'de_DE')
;

-- 2022-11-14T17:21:48.648Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_DE')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:21:50.091Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen).',Updated=TO_TIMESTAMP('2022-11-14 19:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='en_US'
;

-- 2022-11-14T17:21:50.092Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'en_US')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:22:05.374Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)',Updated=TO_TIMESTAMP('2022-11-14 19:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:22:05.375Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'nl_NL')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:22:21.240Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)',Updated=TO_TIMESTAMP('2022-11-14 19:22:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='de_DE'
;

-- 2022-11-14T17:22:21.242Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581604,'de_DE')
;

-- 2022-11-14T17:22:21.243Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_DE')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:22:26.358Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)',Updated=TO_TIMESTAMP('2022-11-14 19:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='de_CH'
;

-- 2022-11-14T17:22:26.359Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_CH')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:22:29.095Z
UPDATE AD_Element_Trl SET Description='Directory used to pull business partners from the sftp server. (If not set, the files will be pulled from the root location of the sftp server.)',Updated=TO_TIMESTAMP('2022-11-14 19:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='en_US'
;

-- 2022-11-14T17:22:29.096Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'en_US')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-14T17:24:25.029Z
UPDATE AD_Element_Trl SET Description='Pattern used to identiffy business partner files on common the sftp server.',Updated=TO_TIMESTAMP('2022-11-14 19:24:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='de_CH'
;

-- 2022-11-14T17:24:25.031Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_CH')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-14T17:24:41.153Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem gemeinsamen sftp-Server verwendet wird.',Updated=TO_TIMESTAMP('2022-11-14 19:24:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='de_DE'
;

-- 2022-11-14T17:24:41.154Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581603,'de_DE')
;

-- 2022-11-14T17:24:41.154Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_DE')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-14T17:25:10.477Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem gemeinsamen sftp-Server verwendet wird.',Updated=TO_TIMESTAMP('2022-11-14 19:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='de_CH'
;

-- 2022-11-14T17:25:10.478Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_CH')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-14T17:25:25.322Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem gemeinsamen sftp-Server verwendet wird.',Updated=TO_TIMESTAMP('2022-11-14 19:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:25:25.323Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'nl_NL')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-14T17:25:55.180Z
UPDATE AD_Element_Trl SET Description='Pattern used to identiffy business partner files on common the sftp server.',Updated=TO_TIMESTAMP('2022-11-14 19:25:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_CH'
;

-- 2022-11-14T17:25:55.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_CH')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-14T17:26:22.896Z
UPDATE AD_Element_Trl SET Description='Pattern used to identiffy product files on the sftp server.',Updated=TO_TIMESTAMP('2022-11-14 19:26:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_DE'
;

-- 2022-11-14T17:26:22.897Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581667,'de_DE')
;

-- 2022-11-14T17:26:22.897Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_DE')
;

-- Element: SFTP_Username
-- 2022-11-14T17:31:44.257Z
UPDATE AD_Element_Trl SET Description='Username used for sftp server authentication.',Updated=TO_TIMESTAMP('2022-11-14 19:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='en_US'
;

-- 2022-11-14T17:31:44.258Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'en_US')
;

-- Element: SFTP_Username
-- 2022-11-14T17:31:58.218Z
UPDATE AD_Element_Trl SET Description='Username used for SFTP server authentication.',Updated=TO_TIMESTAMP('2022-11-14 19:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='en_US'
;

-- 2022-11-14T17:31:58.219Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'en_US')
;

-- Element: SFTP_Username
-- 2022-11-14T17:32:12.178Z
UPDATE AD_Element_Trl SET Description='Username used for SFTP-server authentication.',Updated=TO_TIMESTAMP('2022-11-14 19:32:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='en_US'
;

-- 2022-11-14T17:32:12.179Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'en_US')
;

-- Element: SFTP_Password
-- 2022-11-14T17:33:41.641Z
UPDATE AD_Element_Trl SET Description='Password used for SFTP-server authentication.',Updated=TO_TIMESTAMP('2022-11-14 19:33:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='en_US'
;

-- 2022-11-14T17:33:41.642Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'en_US')
;

-- Element: ErroredDirectory
-- 2022-11-14T17:34:49.176Z
UPDATE AD_Element_Trl SET Description='Defines where files should be moved after being processed with error. (The path should be relative to the current sftp target location)',Updated=TO_TIMESTAMP('2022-11-14 19:34:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='en_US'
;

-- 2022-11-14T17:34:49.177Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'en_US')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-14T17:37:24.556Z
UPDATE AD_Element_Trl SET Description='Directory used to pull products from the sftp server. (If not set, the files will be pulled from the root location of the SFTP-server.)',Updated=TO_TIMESTAMP('2022-11-14 19:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='en_US'
;

-- 2022-11-14T17:37:24.557Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'en_US')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-14T17:39:01.617Z
UPDATE AD_Element_Trl SET Description='Directory used to pull products from the SFTP-server. (If not set, the files will be pulled from the root location of the SFTP-server)',Updated=TO_TIMESTAMP('2022-11-14 19:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='en_US'
;

-- 2022-11-14T17:39:01.618Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'en_US')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-14T17:40:27.083Z
UPDATE AD_Element_Trl SET Description='Pattern used to identify product files on the SFTP-Server. (If not set, all files are considered)',Updated=TO_TIMESTAMP('2022-11-14 19:40:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_CH'
;

-- 2022-11-14T17:40:27.084Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_CH')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-14T17:40:32.689Z
UPDATE AD_Element_Trl SET Description='Pattern used to identify product files on the SFTP-Server. (If not set, all files are considered)',Updated=TO_TIMESTAMP('2022-11-14 19:40:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='en_US'
;

-- 2022-11-14T17:40:32.691Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'en_US')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-14T17:41:04.499Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Produktdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2022-11-14 19:41:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:41:04.500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'nl_NL')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-14T17:41:11.034Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Produktdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2022-11-14 19:41:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_DE'
;

-- 2022-11-14T17:41:11.035Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581667,'de_DE')
;

-- 2022-11-14T17:41:11.036Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_DE')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-14T17:41:14.643Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Produktdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2022-11-14 19:41:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_CH'
;

-- 2022-11-14T17:41:14.645Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_CH')
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-14T17:41:55.828Z
UPDATE AD_Element_Trl SET Description='Pattern used to identify business partner files on the SFTP-Server. (If not set, all files are considered)',Updated=TO_TIMESTAMP('2022-11-14 19:41:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='en_US'
;

-- 2022-11-14T17:41:55.830Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'en_US')
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-14T17:42:12.296Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2022-11-14 19:42:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='de_DE'
;

-- 2022-11-14T17:42:12.298Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581668,'de_DE')
;

-- 2022-11-14T17:42:12.298Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'de_DE')
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-14T17:42:17.438Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2022-11-14 19:42:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='de_CH'
;

-- 2022-11-14T17:42:17.439Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'de_CH')
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-14T17:42:21.910Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2022-11-14 19:42:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:42:21.912Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'nl_NL')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-14T17:44:22.022Z
UPDATE AD_Element_Trl SET Description='Directory used to pull business partners from the sftp server. (If not set, the files will be pulled from the root location of the sftp server)',Updated=TO_TIMESTAMP('2022-11-14 19:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='en_US'
;

-- 2022-11-14T17:44:22.023Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'en_US')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-14T17:45:35.346Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify product files on the SFTP-Server. (If not set, all files are considered)',Updated=TO_TIMESTAMP('2022-11-14 19:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='en_US'
;

-- 2022-11-14T17:45:35.346Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'en_US')
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-14T17:46:44.565Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify business partner files on the SFTP-Server. (If not set, all files are considered)',Updated=TO_TIMESTAMP('2022-11-14 19:46:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='en_US'
;

-- 2022-11-14T17:46:44.566Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'en_US')
;

-- Element: SFTP_HostName
-- 2022-11-14T17:48:29.658Z
UPDATE AD_Element_Trl SET Description='SFTP-Server hostname',Updated=TO_TIMESTAMP('2022-11-14 19:48:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='de_CH'
;

-- 2022-11-14T17:48:29.659Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'de_CH')
;

-- Element: SFTP_HostName
-- 2022-11-14T17:48:44.550Z
UPDATE AD_Element_Trl SET Description='SFTP-Server-Hostname',Updated=TO_TIMESTAMP('2022-11-14 19:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='en_US'
;

-- 2022-11-14T17:48:44.551Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'en_US')
;

-- Element: SFTP_HostName
-- 2022-11-14T17:48:47.225Z
UPDATE AD_Element_Trl SET Description='SFTP-Server-Hostname',Updated=TO_TIMESTAMP('2022-11-14 19:48:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='nl_NL'
;

-- 2022-11-14T17:48:47.226Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'nl_NL')
;

-- Element: SFTP_HostName
-- 2022-11-14T17:48:50.141Z
UPDATE AD_Element_Trl SET Description='SFTP-Server-Hostname',Updated=TO_TIMESTAMP('2022-11-14 19:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='de_DE'
;

-- 2022-11-14T17:48:50.142Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581553,'de_DE')
;

-- 2022-11-14T17:48:50.142Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'de_DE')
;

-- Element: SFTP_HostName
-- 2022-11-14T17:48:53.338Z
UPDATE AD_Element_Trl SET Description='SFTP-Server-Hostname',Updated=TO_TIMESTAMP('2022-11-14 19:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='de_CH'
;

-- 2022-11-14T17:48:53.339Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'de_CH')
;


