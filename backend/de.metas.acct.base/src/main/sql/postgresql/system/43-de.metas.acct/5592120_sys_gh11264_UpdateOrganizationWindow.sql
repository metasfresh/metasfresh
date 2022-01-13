-- Create element for IsEUOneStopShop
-- 2021-06-10T12:18:58.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579327,0,'IsEUOneStopShop',TO_TIMESTAMP('2021-06-10 15:18:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','One-Stop Shop (OSS)','One-Stop Shop (OSS)',TO_TIMESTAMP('2021-06-10 15:18:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T12:18:58.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579327 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-10T12:19:24.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 15:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579327 AND AD_Language='de_CH'
;

-- 2021-06-10T12:19:24.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579327,'de_CH')
;

-- 2021-06-10T12:21:10.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 15:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579327 AND AD_Language='nl_NL'
;

-- 2021-06-10T12:21:10.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579327,'nl_NL')
;

-- 2021-06-10T12:21:26.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 15:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579327 AND AD_Language='de_DE'
;

-- 2021-06-10T12:21:26.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579327,'de_DE')
;

-- 2021-06-10T12:21:26.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579327,'de_DE')
;

-- 2021-06-10T12:21:26.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsEUOneStopShop', Name='One-Stop Shop (OSS)', Description='Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', Help=NULL WHERE AD_Element_ID=579327
;

-- 2021-06-10T12:21:26.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEUOneStopShop', Name='One-Stop Shop (OSS)', Description='Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', Help=NULL, AD_Element_ID=579327 WHERE UPPER(ColumnName)='ISEUONESTOPSHOP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-10T12:21:26.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEUOneStopShop', Name='One-Stop Shop (OSS)', Description='Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', Help=NULL WHERE AD_Element_ID=579327 AND IsCentrallyMaintained='Y'
;

-- 2021-06-10T12:21:26.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='One-Stop Shop (OSS)', Description='Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579327) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579327)
;

-- 2021-06-10T12:21:26.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='One-Stop Shop (OSS)', Description='Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579327
;

-- 2021-06-10T12:21:26.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='One-Stop Shop (OSS)', Description='Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', Help=NULL WHERE AD_Element_ID = 579327
;

-- 2021-06-10T12:21:26.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'One-Stop Shop (OSS)', Description = 'Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579327
;

-- 2021-06-10T12:21:42.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Needs to be ticked if the deliveries to foreign EU countries since the begin of 2020 exceed a volumn of 10000 euro', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-10 15:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579327 AND AD_Language='en_US'
;

-- 2021-06-10T12:21:42.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579327,'en_US')
;


-- Create column for IsEUOneStopShop
-- 2021-06-10T12:23:03.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574321,579327,0,20,155,'IsEUOneStopShop',TO_TIMESTAMP('2021-06-10 15:23:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'One-Stop Shop (OSS)',0,0,TO_TIMESTAMP('2021-06-10 15:23:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-10T12:23:03.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574321 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */  select update_Column_Translation_From_AD_Element(579327)
;

-- Organization window changes
-- Add field to window


-- 2021-06-10T12:25:19.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574321,647482,0,143,0,TO_TIMESTAMP('2021-06-10 15:25:18','YYYY-MM-DD HH24:MI:SS'),100,'Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde',0,'D',0,'Y','Y','Y','N','N','N','N','N','One-Stop Shop (OSS)',80,80,0,1,1,TO_TIMESTAMP('2021-06-10 15:25:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-10T12:25:19.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647482 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-10T12:25:19.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579327)
;

-- 2021-06-10T12:25:19.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647482
;

-- 2021-06-10T12:25:19.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647482)
;


--Display IsEUOneStopShop field in Organization window
-- 2021-06-10T12:26:35.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647482,0,143,540467,585694,'F',TO_TIMESTAMP('2021-06-10 15:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Muss angehakt werden, wenn bei Lieferungen ins EU-Ausland seit 2020 die Lieferschwelle von 10.000 euro überschritten wurde','Y','N','N','Y','N','N','N',0,'One-Stop Shop (OSS)',30,0,0,TO_TIMESTAMP('2021-06-10 15:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Un display Summary Level field
-- 2021-06-10T12:27:24.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-06-10 15:27:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544536
;


-- Un display Org_Id from Fiscal Representation Tab
-- 2021-06-10T12:29:39.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-06-10 15:29:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584649
;

-- 2021-06-10T12:30:55.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-06-10 15:30:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584649
;

-- Un display Client_ID from Fiscal Representation Tab Single view
-- 2021-06-11T07:37:14.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-06-11 10:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584648
;


