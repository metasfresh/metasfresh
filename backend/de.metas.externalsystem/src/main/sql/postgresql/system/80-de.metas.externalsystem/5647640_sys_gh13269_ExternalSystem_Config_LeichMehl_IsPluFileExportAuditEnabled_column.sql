-- 2022-07-21T10:24:01.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581154,0,'IsPluFileExportAuditEnabled',TO_TIMESTAMP('2022-07-21 13:24:01','YYYY-MM-DD HH24:MI:SS'),100,'If enabled, then all the changes done during the PLU-file export will be stored also in metasfresh i.e. the replaced keys from the PLU-file','de.metas.externalsystem','Y','IsPluFileExportAuditEnabled','Enable PLU-file export audit',TO_TIMESTAMP('2022-07-21 13:24:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T10:24:01.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581154 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-21T10:24:24.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', Name='PLU-Datei Exportprüfung aktivieren', PrintName='PLU-Datei Exportprüfung aktivieren',Updated=TO_TIMESTAMP('2022-07-21 13:24:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581154 AND AD_Language='de_CH'
;

-- 2022-07-21T10:24:24.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581154,'de_CH') 
;

-- 2022-07-21T10:24:36.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', Name='PLU-Datei Exportprüfung aktivieren', PrintName='PLU-Datei Exportprüfung aktivieren',Updated=TO_TIMESTAMP('2022-07-21 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581154 AND AD_Language='de_DE'
;

-- 2022-07-21T10:24:36.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581154,'de_DE') 
;

-- 2022-07-21T10:24:36.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581154,'de_DE') 
;

-- 2022-07-21T10:24:36.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPluFileExportAuditEnabled', Name='PLU-Datei Exportprüfung aktivieren', Description='Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', Help=NULL WHERE AD_Element_ID=581154
;

-- 2022-07-21T10:24:36.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPluFileExportAuditEnabled', Name='PLU-Datei Exportprüfung aktivieren', Description='Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', Help=NULL, AD_Element_ID=581154 WHERE UPPER(ColumnName)='ISPLUFILEEXPORTAUDITENABLED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-21T10:24:36.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPluFileExportAuditEnabled', Name='PLU-Datei Exportprüfung aktivieren', Description='Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', Help=NULL WHERE AD_Element_ID=581154 AND IsCentrallyMaintained='Y'
;

-- 2022-07-21T10:24:36.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='PLU-Datei Exportprüfung aktivieren', Description='Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581154) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581154)
;

-- 2022-07-21T10:24:36.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='PLU-Datei Exportprüfung aktivieren', Name='PLU-Datei Exportprüfung aktivieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581154)
;

-- 2022-07-21T10:24:36.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='PLU-Datei Exportprüfung aktivieren', Description='Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581154
;

-- 2022-07-21T10:24:36.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='PLU-Datei Exportprüfung aktivieren', Description='Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', Help=NULL WHERE AD_Element_ID = 581154
;

-- 2022-07-21T10:24:36.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'PLU-Datei Exportprüfung aktivieren', Description = 'Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581154
;

-- 2022-07-21T10:24:43.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Enable PLU-file export audit',Updated=TO_TIMESTAMP('2022-07-21 13:24:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581154 AND AD_Language='en_US'
;

-- 2022-07-21T10:24:43.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581154,'en_US') 
;

-- 2022-07-21T10:24:51.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei', Name='PLU-Datei Exportprüfung aktivieren', PrintName='PLU-Datei Exportprüfung aktivieren',Updated=TO_TIMESTAMP('2022-07-21 13:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581154 AND AD_Language='nl_NL'
;

-- 2022-07-21T10:24:51.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581154,'nl_NL') 
;

-- 2022-07-21T10:25:14.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583721,581154,0,20,542129,'IsPluFileExportAuditEnabled',TO_TIMESTAMP('2022-07-21 13:25:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'PLU-Datei Exportprüfung aktivieren',0,0,TO_TIMESTAMP('2022-07-21 13:25:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T10:25:14.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583721 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T10:25:14.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581154) 
;

-- 2022-07-21T10:25:16.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl','ALTER TABLE public.ExternalSystem_Config_LeichMehl ADD COLUMN IsPluFileExportAuditEnabled CHAR(1) DEFAULT ''N'' CHECK (IsPluFileExportAuditEnabled IN (''Y'',''N'')) NOT NULL')
;


-- 2022-07-21T10:26:43.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583721,702124,0,546388,TO_TIMESTAMP('2022-07-21 13:26:43','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','PLU-Datei Exportprüfung aktivieren',TO_TIMESTAMP('2022-07-21 13:26:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T10:26:43.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702124 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T10:26:43.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581154) 
;

-- 2022-07-21T10:26:43.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702124
;

-- 2022-07-21T10:26:43.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(702124)
;

-- 2022-07-21T10:27:20.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546088,549551,TO_TIMESTAMP('2022-07-21 13:27:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','file export audit',7,TO_TIMESTAMP('2022-07-21 13:27:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T10:27:33.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,702124,0,546388,610513,549551,'F',TO_TIMESTAMP('2022-07-21 13:27:33','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei','Y','N','N','Y','N','N','N',0,'PLU-Datei Exportprüfung aktivieren',10,0,0,TO_TIMESTAMP('2022-07-21 13:27:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T10:29:17.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583721,702125,0,546100,TO_TIMESTAMP('2022-07-21 13:29:16','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','PLU-Datei Exportprüfung aktivieren',TO_TIMESTAMP('2022-07-21 13:29:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T10:29:17.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702125 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T10:29:17.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581154) 
;

-- 2022-07-21T10:29:17.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702125
;

-- 2022-07-21T10:29:17.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(702125)
;

-- 2022-07-21T10:30:24.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545747,549552,TO_TIMESTAMP('2022-07-21 13:30:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','file export audit',7,TO_TIMESTAMP('2022-07-21 13:30:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T10:30:33.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,702125,0,546100,610514,549552,'F',TO_TIMESTAMP('2022-07-21 13:30:33','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option aktiviert ist, werden alle Änderungen, die während des PLU-Dateiexports vorgenommen wurden, auch in metasfresh gespeichert, d.h. die ersetzten Schlüssel aus der PLU-Datei','Y','N','N','Y','N','N','N',0,'PLU-Datei Exportprüfung aktivieren',10,0,0,TO_TIMESTAMP('2022-07-21 13:30:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T10:31:10.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-07-21 13:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609628
;

-- 2022-07-21T10:31:10.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-21 13:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609629
;

-- 2022-07-21T10:31:10.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-07-21 13:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609630
;

-- 2022-07-21T10:31:10.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-07-21 13:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610514
;

