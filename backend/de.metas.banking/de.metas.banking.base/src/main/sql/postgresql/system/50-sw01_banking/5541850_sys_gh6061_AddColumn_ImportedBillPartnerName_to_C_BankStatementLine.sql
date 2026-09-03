-- 2020-01-17T07:35:26.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577480,0,'ImportedBillPartnerName',TO_TIMESTAMP('2020-01-17 09:35:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Imported Bill Partner Name','Imported Bill Partner Name',TO_TIMESTAMP('2020-01-17 09:35:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-17T07:35:26.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577480 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-01-17T07:36:05.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569882,577480,0,10,393,'ImportedBillPartnerName',TO_TIMESTAMP('2020-01-17 09:36:05','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Imported Bill Partner Name',0,0,TO_TIMESTAMP('2020-01-17 09:36:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-01-17T07:36:05.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569882 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-01-17T07:36:05.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577480) 
;

-- 2020-01-17T07:37:13.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569882,595742,0,329,0,TO_TIMESTAMP('2020-01-17 09:37:13','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Imported Bill Partner Name',330,330,0,1,1,TO_TIMESTAMP('2020-01-17 09:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-17T07:37:13.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=595742 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-01-17T07:37:13.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577480) 
;

-- 2020-01-17T07:37:13.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=595742
;

-- 2020-01-17T07:37:13.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(595742)
;

-- 2020-01-17T07:40:21.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,595742,0,329,540272,565086,'F',TO_TIMESTAMP('2020-01-17 09:40:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Imported Bill Partner Name',115,0,0,TO_TIMESTAMP('2020-01-17 09:40:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-17T07:41:54.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Importierter Rechnungspartner Name', PrintName='Importierter Rechnungspartner Name',Updated=TO_TIMESTAMP('2020-01-17 09:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577480 AND AD_Language='de_CH'
;

-- 2020-01-17T07:41:54.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577480,'de_CH') 
;

-- 2020-01-17T07:41:59.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Importierter Rechnungspartner Name', PrintName='Importierter Rechnungspartner Name',Updated=TO_TIMESTAMP('2020-01-17 09:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577480 AND AD_Language='de_DE'
;

-- 2020-01-17T07:41:59.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577480,'de_DE') 
;

-- 2020-01-17T07:41:59.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577480,'de_DE') 
;

-- 2020-01-17T07:41:59.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ImportedBillPartnerName', Name='Importierter Rechnungspartner Name', Description=NULL, Help=NULL WHERE AD_Element_ID=577480
;

-- 2020-01-17T07:41:59.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ImportedBillPartnerName', Name='Importierter Rechnungspartner Name', Description=NULL, Help=NULL, AD_Element_ID=577480 WHERE UPPER(ColumnName)='IMPORTEDBILLPARTNERNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-17T07:41:59.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ImportedBillPartnerName', Name='Importierter Rechnungspartner Name', Description=NULL, Help=NULL WHERE AD_Element_ID=577480 AND IsCentrallyMaintained='Y'
;

-- 2020-01-17T07:41:59.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Importierter Rechnungspartner Name', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577480) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577480)
;

-- 2020-01-17T07:41:59.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Importierter Rechnungspartner Name', Name='Importierter Rechnungspartner Name' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577480)
;

-- 2020-01-17T07:41:59.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Importierter Rechnungspartner Name', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577480
;

-- 2020-01-17T07:41:59.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Importierter Rechnungspartner Name', Description=NULL, Help=NULL WHERE AD_Element_ID = 577480
;

-- 2020-01-17T07:41:59.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Importierter Rechnungspartner Name', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577480
;

-- 2020-01-17T07:42:01.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-17 09:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577480 AND AD_Language='en_US'
;

-- 2020-01-17T07:42:01.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577480,'en_US') 
;

-- 2020-01-17T07:43:18.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BankStatementLine','ALTER TABLE public.C_BankStatementLine ADD COLUMN ImportedBillPartnerName VARCHAR(255)')
;

-- 2020-01-17T07:44:18.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Name of the Bill Partner as appears in the import file',Updated=TO_TIMESTAMP('2020-01-17 09:44:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577480
;

-- 2020-01-17T07:44:18.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ImportedBillPartnerName', Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL WHERE AD_Element_ID=577480
;

-- 2020-01-17T07:44:18.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ImportedBillPartnerName', Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL, AD_Element_ID=577480 WHERE UPPER(ColumnName)='IMPORTEDBILLPARTNERNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-17T07:44:18.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ImportedBillPartnerName', Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL WHERE AD_Element_ID=577480 AND IsCentrallyMaintained='Y'
;

-- 2020-01-17T07:44:18.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577480) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577480)
;

-- 2020-01-17T07:44:18.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577480
;

-- 2020-01-17T07:44:18.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL WHERE AD_Element_ID = 577480
;

-- 2020-01-17T07:44:18.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Importierter Rechnungspartner Name', Description = 'Name of the Bill Partner as appears in the import file', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577480
;

-- 2020-01-17T07:44:22.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name of the Bill Partner as appears in the import file',Updated=TO_TIMESTAMP('2020-01-17 09:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577480 AND AD_Language='de_CH'
;

-- 2020-01-17T07:44:22.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577480,'de_CH') 
;

-- 2020-01-17T07:44:24.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name of the Bill Partner as appears in the import file',Updated=TO_TIMESTAMP('2020-01-17 09:44:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577480 AND AD_Language='de_DE'
;

-- 2020-01-17T07:44:24.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577480,'de_DE') 
;

-- 2020-01-17T07:44:24.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577480,'de_DE') 
;

-- 2020-01-17T07:44:24.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ImportedBillPartnerName', Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL WHERE AD_Element_ID=577480
;

-- 2020-01-17T07:44:24.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ImportedBillPartnerName', Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL, AD_Element_ID=577480 WHERE UPPER(ColumnName)='IMPORTEDBILLPARTNERNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-17T07:44:24.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ImportedBillPartnerName', Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL WHERE AD_Element_ID=577480 AND IsCentrallyMaintained='Y'
;

-- 2020-01-17T07:44:24.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577480) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577480)
;

-- 2020-01-17T07:44:24.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577480
;

-- 2020-01-17T07:44:24.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Importierter Rechnungspartner Name', Description='Name of the Bill Partner as appears in the import file', Help=NULL WHERE AD_Element_ID = 577480
;

-- 2020-01-17T07:44:24.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Importierter Rechnungspartner Name', Description = 'Name of the Bill Partner as appears in the import file', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577480
;

-- 2020-01-17T07:44:25.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name of the Bill Partner as appears in the import file',Updated=TO_TIMESTAMP('2020-01-17 09:44:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577480 AND AD_Language='en_US'
;

-- 2020-01-17T07:44:25.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577480,'en_US') 
;

-- 2020-01-17T07:44:28.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name of the Bill Partner as appears in the import file',Updated=TO_TIMESTAMP('2020-01-17 09:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577480 AND AD_Language='nl_NL'
;

-- 2020-01-17T07:44:28.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577480,'nl_NL') 
;

