-- GENERATE Reference List
-- 2021-05-28T12:26:39.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541326,TO_TIMESTAMP('2021-05-28 15:26:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','GreetingStandardType',TO_TIMESTAMP('2021-05-28 15:26:39','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-05-28T12:26:39.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541326 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-05-28T12:26:51.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Greeting Standard Type',Updated=TO_TIMESTAMP('2021-05-28 15:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541326
;

-- 2021-05-28T12:27:51.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542619,541326,TO_TIMESTAMP('2021-05-28 15:27:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mr.',TO_TIMESTAMP('2021-05-28 15:27:51','YYYY-MM-DD HH24:MI:SS'),100,'MR','MR')
;

-- 2021-05-28T12:27:51.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542619 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-28T12:28:12.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Herr',Updated=TO_TIMESTAMP('2021-05-28 15:28:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542619
;

-- 2021-05-28T12:28:17.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Herr',Updated=TO_TIMESTAMP('2021-05-28 15:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542619
;

-- 2021-05-28T12:28:21.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-28 15:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542619
;

-- 2021-05-28T12:28:25.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Herr',Updated=TO_TIMESTAMP('2021-05-28 15:28:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542619
;

-- 2021-05-28T12:29:33.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542620,541326,TO_TIMESTAMP('2021-05-28 15:29:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mrs.',TO_TIMESTAMP('2021-05-28 15:29:33','YYYY-MM-DD HH24:MI:SS'),100,'MRS','MRS')
;

-- 2021-05-28T12:29:33.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542620 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-28T12:29:52.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Frau',Updated=TO_TIMESTAMP('2021-05-28 15:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542620
;

-- 2021-05-28T12:29:58.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-28 15:29:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542620
;

-- 2021-05-28T12:30:09.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Frau',Updated=TO_TIMESTAMP('2021-05-28 15:30:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542620
;

-- 2021-05-28T12:30:15.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Frau',Updated=TO_TIMESTAMP('2021-05-28 15:30:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542620
;

-- 2021-05-28T12:30:49.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542621,541326,TO_TIMESTAMP('2021-05-28 15:30:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mr. and Mrs.',TO_TIMESTAMP('2021-05-28 15:30:49','YYYY-MM-DD HH24:MI:SS'),100,'MR+MRS','MR+MRS')
;

-- 2021-05-28T12:30:49.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542621 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-28T12:31:38.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Herr und Frau',Updated=TO_TIMESTAMP('2021-05-28 15:31:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542621
;

-- 2021-05-28T12:31:45.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Herr und Frau',Updated=TO_TIMESTAMP('2021-05-28 15:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542621
;

-- 2021-05-28T12:31:49.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-28 15:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542621
;

-- 2021-05-28T12:31:54.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Herr und Frau',Updated=TO_TIMESTAMP('2021-05-28 15:31:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542621
;

-- 2021-05-28T12:32:25.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542622,541326,TO_TIMESTAMP('2021-05-28 15:32:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mrs. and Mr.',TO_TIMESTAMP('2021-05-28 15:32:25','YYYY-MM-DD HH24:MI:SS'),100,'MRS+MR','MRS+MR')
;

-- 2021-05-28T12:32:25.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542622 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-28T12:32:39.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Frau und Herr',Updated=TO_TIMESTAMP('2021-05-28 15:32:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542622
;

-- 2021-05-28T12:32:45.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-28 15:32:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542622
;

-- 2021-05-28T12:32:52.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Frau und Herr',Updated=TO_TIMESTAMP('2021-05-28 15:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542622
;

-- 2021-05-28T12:32:59.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Frau und Herr',Updated=TO_TIMESTAMP('2021-05-28 15:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542622
;

--Generate System Element for column


-- 2021-05-28T12:34:39.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579265,0,'GreetingStandardType',TO_TIMESTAMP('2021-05-28 15:34:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','GreetingStandardType','GreetingStandardType',TO_TIMESTAMP('2021-05-28 15:34:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-28T12:34:39.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579265 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-28T12:34:59.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Greeting Standard Type', PrintName='Greeting Standard Type',Updated=TO_TIMESTAMP('2021-05-28 15:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579265 AND AD_Language='en_US'
;

-- 2021-05-28T12:34:59.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579265,'en_US')
;

-- 2021-05-28T12:34:59.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579265,'en_US')
;

-- 2021-05-28T12:34:59.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='GreetingStandardType', Name='Greeting Standard Type', Description=NULL, Help=NULL WHERE AD_Element_ID=579265
;

-- 2021-05-28T12:34:59.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GreetingStandardType', Name='Greeting Standard Type', Description=NULL, Help=NULL, AD_Element_ID=579265 WHERE UPPER(ColumnName)='GREETINGSTANDARDTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-28T12:34:59.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GreetingStandardType', Name='Greeting Standard Type', Description=NULL, Help=NULL WHERE AD_Element_ID=579265 AND IsCentrallyMaintained='Y'
;

-- 2021-05-28T12:34:59.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Greeting Standard Type', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579265) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579265)
;

-- 2021-05-28T12:34:59.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Greeting Standard Type', Name='Greeting Standard Type' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579265)
;

-- 2021-05-28T12:34:59.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Greeting Standard Type', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579265
;

-- 2021-05-28T12:34:59.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Greeting Standard Type', Description=NULL, Help=NULL WHERE AD_Element_ID = 579265
;

-- 2021-05-28T12:34:59.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Greeting Standard Type', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579265
;

-- Column Creation

-- 2021-05-28T12:37:59.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574116,579265,0,17,541326,346,'GreetingStandardType',TO_TIMESTAMP('2021-05-28 15:37:59','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,20,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Greeting Standard Type',0,0,TO_TIMESTAMP('2021-05-28 15:37:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-28T12:37:59.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574116 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-28T12:37:59.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579265)
;

-- Create index

-- 2021-05-28T12:42:52.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540596,0,346,TO_TIMESTAMP('2021-05-28 15:42:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','GreetingStandardType_UniqueIndex','N',TO_TIMESTAMP('2021-05-28 15:42:52','YYYY-MM-DD HH24:MI:SS'),100,'GreetingStandardType IS NOT NULL')
;

-- 2021-05-28T12:42:52.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540596 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-05-28T12:43:13.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,574116,541107,540596,0,TO_TIMESTAMP('2021-05-28 15:43:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-05-28 15:43:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-28T12:43:23.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX GreetingStandardType_UniqueIndex ON C_Greeting (GreetingStandardType) WHERE GreetingStandardType IS NOT NULL
;


-- Add new column to the window

-- 2021-05-28T12:45:37.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574116,646829,0,282,0,TO_TIMESTAMP('2021-05-28 15:45:37','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Greeting Standard Type',90,90,0,1,1,TO_TIMESTAMP('2021-05-28 15:45:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-28T12:45:37.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-28T12:45:37.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579265)
;

-- 2021-05-28T12:45:37.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646829
;

-- 2021-05-28T12:45:37.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(646829)
;

-- 2021-05-28T12:46:34.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,646829,0,282,585287,540820,'F',TO_TIMESTAMP('2021-05-28 15:46:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Greeting Standard Type',20,0,0,TO_TIMESTAMP('2021-05-28 15:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ADD Trls
-- 2021-05-31T09:01:07.148Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Herr',Updated=TO_TIMESTAMP('2021-05-31 11:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542619
;

-- 2021-05-31T09:01:37.114Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Frau',Updated=TO_TIMESTAMP('2021-05-31 11:01:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542620
;

-- 2021-05-31T09:01:55.441Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Herr und Frau',Updated=TO_TIMESTAMP('2021-05-31 11:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542621
;

-- 2021-05-31T09:02:08.613Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Frau und Herr',Updated=TO_TIMESTAMP('2021-05-31 11:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542622
;

-- 2021-05-31T09:17:39.943Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Standard Anrede', PrintName='Standard Anrede',Updated=TO_TIMESTAMP('2021-05-31 11:17:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579265 AND AD_Language='de_CH'
;

-- 2021-05-31T09:17:39.971Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579265,'de_CH')
;

-- 2021-05-31T09:17:46.793Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Standard Anrede', PrintName='Standard Anrede',Updated=TO_TIMESTAMP('2021-05-31 11:17:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579265 AND AD_Language='de_DE'
;

-- 2021-05-31T09:17:46.794Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579265,'de_DE')
;

-- 2021-05-31T09:17:46.799Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579265,'de_DE')
;

-- 2021-05-31T09:17:46.803Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='GreetingStandardType', Name='Standard Anrede', Description=NULL, Help=NULL WHERE AD_Element_ID=579265
;

-- 2021-05-31T09:17:46.803Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GreetingStandardType', Name='Standard Anrede', Description=NULL, Help=NULL, AD_Element_ID=579265 WHERE UPPER(ColumnName)='GREETINGSTANDARDTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-31T09:17:46.804Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GreetingStandardType', Name='Standard Anrede', Description=NULL, Help=NULL WHERE AD_Element_ID=579265 AND IsCentrallyMaintained='Y'
;

-- 2021-05-31T09:17:46.804Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Standard Anrede', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579265) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579265)
;

-- 2021-05-31T09:17:46.814Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Standard Anrede', Name='Standard Anrede' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579265)
;

-- 2021-05-31T09:17:46.815Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Standard Anrede', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579265
;

-- 2021-05-31T09:17:46.815Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Standard Anrede', Description=NULL, Help=NULL WHERE AD_Element_ID = 579265
;

-- 2021-05-31T09:17:46.816Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Standard Anrede', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579265
;

-- 2021-05-31T09:20:21.775Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.', Name='Standard Anrede', PrintName='Standard Anrede',Updated=TO_TIMESTAMP('2021-05-31 11:20:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579265 AND AD_Language='nl_NL'
;

-- 2021-05-31T09:20:21.776Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579265,'nl_NL')
;

-- 2021-05-31T09:20:27.591Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.',Updated=TO_TIMESTAMP('2021-05-31 11:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579265 AND AD_Language='de_DE'
;

-- 2021-05-31T09:20:27.593Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579265,'de_DE')
;

-- 2021-05-31T09:20:27.619Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579265,'de_DE')
;

-- 2021-05-31T09:20:27.622Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='GreetingStandardType', Name='Standard Anrede', Description='Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.', Help=NULL WHERE AD_Element_ID=579265
;

-- 2021-05-31T09:20:27.625Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GreetingStandardType', Name='Standard Anrede', Description='Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.', Help=NULL, AD_Element_ID=579265 WHERE UPPER(ColumnName)='GREETINGSTANDARDTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-31T09:20:27.628Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='GreetingStandardType', Name='Standard Anrede', Description='Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.', Help=NULL WHERE AD_Element_ID=579265 AND IsCentrallyMaintained='Y'
;

-- 2021-05-31T09:20:27.629Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Standard Anrede', Description='Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579265) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579265)
;

-- 2021-05-31T09:20:27.651Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Standard Anrede', Description='Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579265
;

-- 2021-05-31T09:20:27.652Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Standard Anrede', Description='Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.', Help=NULL WHERE AD_Element_ID = 579265
;

-- 2021-05-31T09:20:27.653Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Standard Anrede', Description = 'Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579265
;

-- 2021-05-31T09:20:31.235Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Dieses Feld definiert die Anrede im Brief / Dokument basierend auf der Art der Mitgliederkontakte.',Updated=TO_TIMESTAMP('2021-05-31 11:20:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579265 AND AD_Language='de_CH'
;

-- 2021-05-31T09:20:31.235Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579265,'de_CH')
;

-- 2021-05-31T09:21:17.181Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='This field defines the salutation in the letter / document based on the type of member contacts.',Updated=TO_TIMESTAMP('2021-05-31 11:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579265 AND AD_Language='en_US'
;

-- 2021-05-31T09:21:17.182Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579265,'en_US')
;




