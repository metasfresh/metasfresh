-- 2021-04-14T11:01:37.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579011,0,'DoNotChangeZeroPrices',TO_TIMESTAMP('2021-04-14 14:01:36','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','DoNotChangeZeroPrices','DoNotChangeZeroPrices',TO_TIMESTAMP('2021-04-14 14:01:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-14T11:01:37.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579011 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-14T11:01:40.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2021-04-14 14:01:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579011
;

-- 2021-04-14T11:02:00.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573350,579011,0,20,475,'DoNotChangeZeroPrices',TO_TIMESTAMP('2021-04-14 14:01:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'DoNotChangeZeroPrices',0,0,TO_TIMESTAMP('2021-04-14 14:01:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-14T11:02:00.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573350 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-14T11:02:00.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579011) 
;

-- 2021-04-14T11:02:01.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_DiscountSchema','ALTER TABLE public.M_DiscountSchema ADD COLUMN DoNotChangeZeroPrices CHAR(1) DEFAULT ''N'' CHECK (DoNotChangeZeroPrices IN (''Y'',''N'')) NOT NULL')
;

-- 2021-04-14T16:54:22.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (404,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-04-14 19:54:21','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-04-14 19:54:21','YYYY-MM-DD HH24:MI:SS'),100,643542,'Y',160,160,1,1,573350,'DoNotChangeZeroPrices',0,'D')
;

-- 2021-04-14T16:54:22.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=643542 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-14T16:54:22.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579011) 
;

-- 2021-04-14T16:54:22.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=643542
;

-- 2021-04-14T16:54:22.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(643542)
;

-- 2021-04-14T16:54:50.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,643542,0,404,540612,583494,'F',TO_TIMESTAMP('2021-04-14 19:54:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DoNotChangeZeroPrices',40,0,0,TO_TIMESTAMP('2021-04-14 19:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;
















-- 2021-04-15T16:27:34.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Retain zero prices', PrintName='Retain zero prices',Updated=TO_TIMESTAMP('2021-04-15 19:27:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579011 AND AD_Language='en_US'
;

-- 2021-04-15T16:27:34.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579011,'en_US') 
;

-- 2021-04-15T16:27:47.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nullpreise beibehalten', PrintName='Nullpreise beibehalten',Updated=TO_TIMESTAMP('2021-04-15 19:27:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579011 AND AD_Language='de_DE'
;

-- 2021-04-15T16:27:47.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579011,'de_DE') 
;

-- 2021-04-15T16:27:47.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579011,'de_DE') 
;

-- 2021-04-15T16:27:47.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DoNotChangeZeroPrices', Name='Nullpreise beibehalten', Description=NULL, Help=NULL WHERE AD_Element_ID=579011
;

-- 2021-04-15T16:27:47.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DoNotChangeZeroPrices', Name='Nullpreise beibehalten', Description=NULL, Help=NULL, AD_Element_ID=579011 WHERE UPPER(ColumnName)='DONOTCHANGEZEROPRICES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-15T16:27:47.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DoNotChangeZeroPrices', Name='Nullpreise beibehalten', Description=NULL, Help=NULL WHERE AD_Element_ID=579011 AND IsCentrallyMaintained='Y'
;

-- 2021-04-15T16:27:47.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nullpreise beibehalten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579011) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579011)
;

-- 2021-04-15T16:27:47.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Nullpreise beibehalten', Name='Nullpreise beibehalten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579011)
;

-- 2021-04-15T16:27:47.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nullpreise beibehalten', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579011
;

-- 2021-04-15T16:27:47.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nullpreise beibehalten', Description=NULL, Help=NULL WHERE AD_Element_ID = 579011
;

-- 2021-04-15T16:27:47.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nullpreise beibehalten', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579011
;

-- 2021-04-15T16:27:52.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nullpreise beibehalten', PrintName='Nullpreise beibehalten',Updated=TO_TIMESTAMP('2021-04-15 19:27:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579011 AND AD_Language='de_CH'
;

-- 2021-04-15T16:27:52.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579011,'de_CH') 
;

-- 2021-04-15T16:28:00.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nullpreise beibehalten', PrintName='Nullpreise beibehalten',Updated=TO_TIMESTAMP('2021-04-15 19:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579011 AND AD_Language='nl_NL'
;

-- 2021-04-15T16:28:00.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579011,'nl_NL') 
;

-- 2021-04-15T16:28:50.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Do not change prices equal to 0 when updating (derivative) price lists.', Help='Do not change prices equal to 0 when updating (derivative) price lists.',Updated=TO_TIMESTAMP('2021-04-15 19:28:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579011 AND AD_Language='en_US'
;

-- 2021-04-15T16:28:50.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579011,'en_US') 
;

-- 2021-04-15T17:14:22.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (675,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-04-15 20:14:21','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-04-15 20:14:21','YYYY-MM-DD HH24:MI:SS'),100,643543,'Y',90,90,1,1,573350,'Nullpreise beibehalten',0,'D')
;

-- 2021-04-15T17:14:22.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=643543 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-15T17:14:22.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579011) 
;

-- 2021-04-15T17:14:22.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=643543
;

-- 2021-04-15T17:14:22.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(643543)
;

-- 2021-04-15T17:14:43.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,643543,0,675,540843,583496,'F',TO_TIMESTAMP('2021-04-15 20:14:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Nullpreise beibehalten',20,0,0,TO_TIMESTAMP('2021-04-15 20:14:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-15T17:33:04.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', Help='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.',Updated=TO_TIMESTAMP('2021-04-15 20:33:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579011 AND AD_Language='de_DE'
;

-- 2021-04-15T17:33:04.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579011,'de_DE') 
;

-- 2021-04-15T17:33:04.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579011,'de_DE') 
;

-- 2021-04-15T17:33:04.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DoNotChangeZeroPrices', Name='Nullpreise beibehalten', Description='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', Help='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.' WHERE AD_Element_ID=579011
;

-- 2021-04-15T17:33:04.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DoNotChangeZeroPrices', Name='Nullpreise beibehalten', Description='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', Help='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', AD_Element_ID=579011 WHERE UPPER(ColumnName)='DONOTCHANGEZEROPRICES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-15T17:33:04.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DoNotChangeZeroPrices', Name='Nullpreise beibehalten', Description='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', Help='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.' WHERE AD_Element_ID=579011 AND IsCentrallyMaintained='Y'
;

-- 2021-04-15T17:33:04.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nullpreise beibehalten', Description='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', Help='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579011) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579011)
;

-- 2021-04-15T17:33:04.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nullpreise beibehalten', Description='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', Help='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', CommitWarning = NULL WHERE AD_Element_ID = 579011
;

-- 2021-04-15T17:33:04.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nullpreise beibehalten', Description='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', Help='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.' WHERE AD_Element_ID = 579011
;

-- 2021-04-15T17:33:04.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nullpreise beibehalten', Description = 'Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579011
;

-- 2021-04-15T17:33:07.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', Help='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.',Updated=TO_TIMESTAMP('2021-04-15 20:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579011 AND AD_Language='de_CH'
;

-- 2021-04-15T17:33:07.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579011,'de_CH') 
;

-- 2021-04-15T17:33:13.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.', Help='Preise gleich 0 werden beim Aktualisieren von (derivativen) Preislisten nicht geändert.',Updated=TO_TIMESTAMP('2021-04-15 20:33:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579011 AND AD_Language='nl_NL'
;

-- 2021-04-15T17:33:13.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579011,'nl_NL') 
;


