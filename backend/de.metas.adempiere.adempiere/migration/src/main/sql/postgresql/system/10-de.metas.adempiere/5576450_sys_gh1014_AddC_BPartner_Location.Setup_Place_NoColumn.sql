-- 2021-01-14T15:34:41.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578643,0,'Setup_Place_No',TO_TIMESTAMP('2021-01-14 17:34:41','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Rüstplatz beim Logistik-Partner oder im eigenen Lager','D','Y','Rüstplatz-Nr.','Rüstplatz-Nr.',TO_TIMESTAMP('2021-01-14 17:34:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-14T15:34:41.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578643 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-01-14T15:35:53.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Setup Place No.', PrintName='Setup Place No.',Updated=TO_TIMESTAMP('2021-01-14 17:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578643 AND AD_Language='en_US'
;

-- 2021-01-14T15:35:53.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578643,'en_US') 
;

-- 2021-01-14T15:35:53.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578643,'en_US') 
;

-- 2021-01-14T15:35:53.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Setup_Place_No', Name='Setup Place No.', Description='', Help=NULL WHERE AD_Element_ID=578643
;

-- 2021-01-14T15:35:53.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Setup_Place_No', Name='Setup Place No.', Description='', Help=NULL, AD_Element_ID=578643 WHERE UPPER(ColumnName)='SETUP_PLACE_NO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-01-14T15:35:53.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Setup_Place_No', Name='Setup Place No.', Description='', Help=NULL WHERE AD_Element_ID=578643 AND IsCentrallyMaintained='Y'
;

-- 2021-01-14T15:35:53.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Setup Place No.', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578643) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578643)
;

-- 2021-01-14T15:35:53.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Setup Place No.', Name='Setup Place No.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578643)
;

-- 2021-01-14T15:35:53.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Setup Place No.', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578643
;

-- 2021-01-14T15:35:53.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Setup Place No.', Description='', Help=NULL WHERE AD_Element_ID = 578643
;

-- 2021-01-14T15:35:53.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Setup Place No.', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578643
;

-- 2021-01-15T06:58:56.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID,EntityType) VALUES (10,250,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-01-15 08:58:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-01-15 08:58:55','YYYY-MM-DD HH24:MI:SS'),100,'N','N',293,'N',572436,'N','N','N','N','N','N','N',0,'N','N','Setup_Place_No','N','','N',0,0,578643,'N','Setup Place No.',0,'D')
;

-- 2021-01-15T06:58:56.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572436 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-01-15T06:58:56.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578643) 
;

-- 2021-01-15T07:04:43.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN Setup_Place_No VARCHAR(250)')
;

-- 2021-01-15T07:22:11.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Description,Name,AD_Org_ID,EntityType) VALUES (222,'Y',250,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-01-15 09:22:10','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-01-15 09:22:10','YYYY-MM-DD HH24:MI:SS'),100,628616,'Y',190,170,1,1,572436,'','Setup Place No.',0,'D')
;

-- 2021-01-15T07:22:11.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=628616 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-01-15T07:22:11.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578643) 
;

-- 2021-01-15T07:22:11.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=628616
;

-- 2021-01-15T07:22:11.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(628616)
;

-- 2021-01-15T07:23:34.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,628616,0,222,576299,1000034,'F',TO_TIMESTAMP('2021-01-15 09:23:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Setup Place No.',160,0,0,TO_TIMESTAMP('2021-01-15 09:23:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-15T07:24:08.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=145,Updated=TO_TIMESTAMP('2021-01-15 09:24:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576299
;

-- 2021-01-15T07:25:58.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=135,Updated=TO_TIMESTAMP('2021-01-15 09:25:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576299
;

-- 2021-01-15T08:26:13.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,572436,0,TO_TIMESTAMP('2021-01-15 10:26:12','YYYY-MM-DD HH24:MI:SS'),100,'U',540396,550320,'Y','N','N','Setup_Place_No',300,'E',TO_TIMESTAMP('2021-01-15 10:26:12','YYYY-MM-DD HH24:MI:SS'),100,'Setup_Place_No')
;

-- 2021-01-15T08:35:04.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET EntityType='de.metas.esb.edi',Updated=TO_TIMESTAMP('2021-01-15 10:35:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550320
;

