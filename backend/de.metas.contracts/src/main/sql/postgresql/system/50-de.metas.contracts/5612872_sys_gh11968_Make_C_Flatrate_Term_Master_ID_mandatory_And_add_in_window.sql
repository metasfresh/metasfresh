
-- 2021-11-11T08:48:57.201084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,578376,668442,0,540859,0,TO_TIMESTAMP('2021-11-11 10:48:57','YYYY-MM-DD HH24:MI:SS'),100,'Is the ID of the first C_Flatrate_Term from entire hierarchy chain',0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','Flatrate Term Master',0,500,0,1,1,TO_TIMESTAMP('2021-11-11 10:48:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-11T08:48:57.204695600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=668442 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-11-11T08:48:57.206764700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580207) 
;

-- 2021-11-11T08:48:57.230693800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=668442
;

-- 2021-11-11T08:48:57.232146800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(668442)
;

-- 2021-11-11T08:51:45.834979600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Master Flatrate Laufzeit', PrintName='Master Flatrate Laufzeit',Updated=TO_TIMESTAMP('2021-11-11 10:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580207 AND AD_Language='de_CH'
;

-- 2021-11-11T08:51:45.840641300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580207,'de_CH') 
;

-- 2021-11-11T08:51:49.117747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Master Flatrate Laufzeit', PrintName='Master Flatrate Laufzeit',Updated=TO_TIMESTAMP('2021-11-11 10:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580207 AND AD_Language='de_DE'
;

-- 2021-11-11T08:51:49.118782900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580207,'de_DE') 
;

-- 2021-11-11T08:51:49.124516800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580207,'de_DE') 
;

-- 2021-11-11T08:51:49.129028500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Flatrate_Term_Master_ID', Name='Master Flatrate Laufzeit', Description='Is the ID of the first C_Flatrate_Term from entire hierarchy chain', Help=NULL WHERE AD_Element_ID=580207
;

-- 2021-11-11T08:51:49.130170400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Flatrate_Term_Master_ID', Name='Master Flatrate Laufzeit', Description='Is the ID of the first C_Flatrate_Term from entire hierarchy chain', Help=NULL, AD_Element_ID=580207 WHERE UPPER(ColumnName)='C_FLATRATE_TERM_MASTER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-11T08:51:49.131209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Flatrate_Term_Master_ID', Name='Master Flatrate Laufzeit', Description='Is the ID of the first C_Flatrate_Term from entire hierarchy chain', Help=NULL WHERE AD_Element_ID=580207 AND IsCentrallyMaintained='Y'
;

-- 2021-11-11T08:51:49.132244900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Master Flatrate Laufzeit', Description='Is the ID of the first C_Flatrate_Term from entire hierarchy chain', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580207) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580207)
;

-- 2021-11-11T08:51:49.143074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Master Flatrate Laufzeit', Name='Master Flatrate Laufzeit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580207)
;

-- 2021-11-11T08:51:49.144608500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Master Flatrate Laufzeit', Description='Is the ID of the first C_Flatrate_Term from entire hierarchy chain', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580207
;

-- 2021-11-11T08:51:49.145700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Master Flatrate Laufzeit', Description='Is the ID of the first C_Flatrate_Term from entire hierarchy chain', Help=NULL WHERE AD_Element_ID = 580207
;

-- 2021-11-11T08:51:49.146734300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Master Flatrate Laufzeit', Description = 'Is the ID of the first C_Flatrate_Term from entire hierarchy chain', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580207
;

-- 2021-11-11T08:51:56.690947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Master Flatrate Term ', PrintName='Master Flatrate Term ',Updated=TO_TIMESTAMP('2021-11-11 10:51:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580207 AND AD_Language='fr_CH'
;

-- 2021-11-11T08:51:56.691978200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580207,'fr_CH') 
;

-- 2021-11-11T08:52:17.620955100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-11-11 10:52:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578376
;

-- 2021-11-11T08:52:21.455694600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_term','C_Flatrate_Term_Master_ID','NUMERIC(10)',null,null)
;

-- 2021-11-11T08:52:21.459337100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_flatrate_term','C_Flatrate_Term_Master_ID',null,'NOT NULL',null)
;



-- 2021-11-11T09:14:37.983663800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,668442,0,540859,541104,594988,'F',TO_TIMESTAMP('2021-11-11 11:14:37','YYYY-MM-DD HH24:MI:SS'),100,'Is the ID of the first C_Flatrate_Term from entire hierarchy chain','Y','N','N','Y','N','N','N',0,'Master Flatrate Laufzeit',470,0,0,TO_TIMESTAMP('2021-11-11 11:14:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-11T09:14:48.479399200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2021-11-11 11:14:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594988
;

