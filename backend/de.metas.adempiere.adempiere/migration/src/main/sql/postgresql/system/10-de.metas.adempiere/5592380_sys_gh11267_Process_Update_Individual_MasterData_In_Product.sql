-- 2021-06-11T10:04:54.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@M_AttributeSetInstance_ID/-1@ > 0',Updated=TO_TIMESTAMP('2021-06-11 13:04:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8417
;

-- 2021-06-11T10:12:39.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000004,545980,TO_TIMESTAMP('2021-06-11 13:12:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','attribute',50,TO_TIMESTAMP('2021-06-11 13:12:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T10:12:58.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6343,0,180,545980,585699,'F',TO_TIMESTAMP('2021-06-11 13:12:58','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals-Satz zum Produkt','Definieren Sie Merkmals-Sätze um einem Produkt zusätzliche Merkmale und Eigenschaften zuzuordnen. Sie müssen einen Merkmals-Satz anlegen, um Serien- und Los-Nummern verfolgen zu können.','Y','N','N','Y','N','N','N',0,'Merkmals-Satz',10,0,0,TO_TIMESTAMP('2021-06-11 13:12:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T10:13:17.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6344,0,180,545980,585700,'F',TO_TIMESTAMP('2021-06-11 13:13:17','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'Merkmale',20,0,0,TO_TIMESTAMP('2021-06-11 13:13:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T10:16:37.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579329,0,TO_TIMESTAMP('2021-06-11 13:16:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produktdaten Schema','Produktdaten Schema',TO_TIMESTAMP('2021-06-11 13:16:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T10:16:37.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579329 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;







-- 2021-06-11T11:15:46.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Masterdata Schema', PrintName='Masterdata Schema',Updated=TO_TIMESTAMP('2021-06-11 14:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579329 AND AD_Language='en_US'
;

-- 2021-06-11T11:15:46.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579329,'en_US') 
;

-- 2021-06-11T11:19:00.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Contains the attributes that the product can have.',Updated=TO_TIMESTAMP('2021-06-11 14:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579329 AND AD_Language='en_US'
;

-- 2021-06-11T11:19:00.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579329,'en_US') 
;

-- 2021-06-11T11:19:48.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=579329, Description=NULL, Help=NULL, Name='Produktdaten Schema',Updated=TO_TIMESTAMP('2021-06-11 14:19:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6343
;

-- 2021-06-11T11:19:48.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579329) 
;

-- 2021-06-11T11:19:48.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=6343
;

-- 2021-06-11T11:19:48.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(6343)
;

-- 2021-06-11T11:34:01.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579330,0,TO_TIMESTAMP('2021-06-11 14:34:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Individuelle Produktdaten','Individuelle Produktdaten',TO_TIMESTAMP('2021-06-11 14:34:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T11:34:01.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579330 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-11T11:34:28.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Individual Masterdata', PrintName='Individual Masterdata',Updated=TO_TIMESTAMP('2021-06-11 14:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579330 AND AD_Language='en_US'
;

-- 2021-06-11T11:34:28.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579330,'en_US') 
;

-- 2021-06-11T11:39:47.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The attribute values of the product',Updated=TO_TIMESTAMP('2021-06-11 14:39:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579330 AND AD_Language='en_US'
;

-- 2021-06-11T11:39:47.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579330,'en_US') 
;

-- 2021-06-11T11:40:34.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=579330, Description=NULL, Help=NULL, Name='Individuelle Produktdaten',Updated=TO_TIMESTAMP('2021-06-11 14:40:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6344
;

-- 2021-06-11T11:40:34.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579330) 
;

-- 2021-06-11T11:40:34.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=6344
;

-- 2021-06-11T11:40:34.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(6344)
;

-- 2021-06-11T11:42:49.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584842,'Y','N',TO_TIMESTAMP('2021-06-11 14:42:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'Empty AttributeSet And AttributeSetInstance','json','N','N','Java',TO_TIMESTAMP('2021-06-11 14:42:49','YYYY-MM-DD HH24:MI:SS'),100,'M_Product_Empty_AttributeSet_And_AttributeSetInstance')
;

-- 2021-06-11T11:42:49.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584842 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-06-11T11:45:40.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Empty Masterdata Schema And Individual Masterdata',Updated=TO_TIMESTAMP('2021-06-11 14:45:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584842
;

-- 2021-06-11T11:55:13.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.product.process.M_Product_Empty_AttributeSet_And_AttributeSetInstance',Updated=TO_TIMESTAMP('2021-06-11 14:55:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584842
;

-- 2021-06-11T12:35:06.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Empty Individual Masterdata', Value='de.metas.product.process.M_Product_Empty_AttributeSetInstance',Updated=TO_TIMESTAMP('2021-06-11 15:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584842
;

-- 2021-06-11T12:35:21.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.product.process.M_Product_Empty_AttributeSetInstance', Value='M_Product_Empty_AttributeSetInstance',Updated=TO_TIMESTAMP('2021-06-11 15:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584842
;

-- 2021-06-11T12:44:14.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584842,208,540943,TO_TIMESTAMP('2021-06-11 15:44:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-06-11 15:44:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-06-11T12:58:59.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=25,Updated=TO_TIMESTAMP('2021-06-11 15:58:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545980
;

-- 2021-06-11T13:58:27.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Enthält die Merkmale, die das Produkt haben kann.',Updated=TO_TIMESTAMP('2021-06-11 16:58:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579329 AND AD_Language='de_DE'
;

-- 2021-06-11T13:58:27.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579329,'de_DE') 
;

-- 2021-06-11T13:58:27.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579329,'de_DE') 
;

-- 2021-06-11T13:58:27.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Produktdaten Schema', Description='Enthält die Merkmale, die das Produkt haben kann.', Help=NULL WHERE AD_Element_ID=579329
;

-- 2021-06-11T13:58:27.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Produktdaten Schema', Description='Enthält die Merkmale, die das Produkt haben kann.', Help=NULL WHERE AD_Element_ID=579329 AND IsCentrallyMaintained='Y'
;

-- 2021-06-11T13:58:27.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktdaten Schema', Description='Enthält die Merkmale, die das Produkt haben kann.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579329) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579329)
;

-- 2021-06-11T13:58:27.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktdaten Schema', Description='Enthält die Merkmale, die das Produkt haben kann.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579329
;

-- 2021-06-11T13:58:27.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktdaten Schema', Description='Enthält die Merkmale, die das Produkt haben kann.', Help=NULL WHERE AD_Element_ID = 579329
;

-- 2021-06-11T13:58:27.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produktdaten Schema', Description = 'Enthält die Merkmale, die das Produkt haben kann.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579329
;

-- 2021-06-11T13:58:31.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Enthält die Merkmale, die das Produkt haben kann.',Updated=TO_TIMESTAMP('2021-06-11 16:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579329 AND AD_Language='de_CH'
;

-- 2021-06-11T13:58:31.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579329,'de_CH') 
;

-- 2021-06-11T13:58:34.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 16:58:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579329 AND AD_Language='de_CH'
;

-- 2021-06-11T13:58:34.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579329,'de_CH') 
;

-- 2021-06-11T13:58:35.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 16:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579329 AND AD_Language='de_DE'
;

-- 2021-06-11T13:58:35.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579329,'de_DE') 
;

-- 2021-06-11T13:58:35.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579329,'de_DE') 
;

-- 2021-06-11T13:59:02.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Master Data Schema', PrintName='Master Data Schema',Updated=TO_TIMESTAMP('2021-06-11 16:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579329 AND AD_Language='en_US'
;

-- 2021-06-11T13:59:02.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579329,'en_US') 
;

-- 2021-06-11T13:59:37.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Individual Master Data', PrintName='Individual Master Data',Updated=TO_TIMESTAMP('2021-06-11 16:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579330 AND AD_Language='en_US'
;

-- 2021-06-11T13:59:37.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579330,'en_US') 
;

-- 2021-06-11T13:59:42.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Die Merkmalswerte des Produktes.',Updated=TO_TIMESTAMP('2021-06-11 16:59:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579330 AND AD_Language='de_DE'
;

-- 2021-06-11T13:59:42.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579330,'de_DE') 
;

-- 2021-06-11T13:59:42.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579330,'de_DE') 
;

-- 2021-06-11T13:59:42.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Individuelle Produktdaten', Description='Die Merkmalswerte des Produktes.', Help=NULL WHERE AD_Element_ID=579330
;

-- 2021-06-11T13:59:42.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Individuelle Produktdaten', Description='Die Merkmalswerte des Produktes.', Help=NULL WHERE AD_Element_ID=579330 AND IsCentrallyMaintained='Y'
;

-- 2021-06-11T13:59:42.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Individuelle Produktdaten', Description='Die Merkmalswerte des Produktes.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579330) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579330)
;

-- 2021-06-11T13:59:42.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Individuelle Produktdaten', Description='Die Merkmalswerte des Produktes.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579330
;

-- 2021-06-11T13:59:42.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Individuelle Produktdaten', Description='Die Merkmalswerte des Produktes.', Help=NULL WHERE AD_Element_ID = 579330
;

-- 2021-06-11T13:59:42.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Individuelle Produktdaten', Description = 'Die Merkmalswerte des Produktes.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579330
;

-- 2021-06-11T13:59:44.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Die Merkmalswerte des Produktes.',Updated=TO_TIMESTAMP('2021-06-11 16:59:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579330 AND AD_Language='de_CH'
;

-- 2021-06-11T13:59:44.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579330,'de_CH') 
;

-- 2021-06-11T13:59:49.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The attribute values of the product.',Updated=TO_TIMESTAMP('2021-06-11 16:59:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579330 AND AD_Language='en_US'
;

-- 2021-06-11T13:59:49.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579330,'en_US') 
;

-- 2021-06-11T14:00:56.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.product.process.M_Product_Clear_AttributeSetInstance', Value='M_Product_Clear_AttributeSetInstance',Updated=TO_TIMESTAMP('2021-06-11 17:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584842
;

-- 2021-06-11T14:01:07.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Individuelle Produktdaten löschen',Updated=TO_TIMESTAMP('2021-06-11 17:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584842
;

-- 2021-06-11T14:01:17.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Individuelle Produktdaten löschen',Updated=TO_TIMESTAMP('2021-06-11 17:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584842
;

-- 2021-06-11T14:01:37.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Clear individual master data',Updated=TO_TIMESTAMP('2021-06-11 17:01:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584842
;

-- 2021-06-11T14:01:43.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 17:01:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584842
;

-- 2021-06-11T14:01:47.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Individuelle Produktdaten löschen',Updated=TO_TIMESTAMP('2021-06-11 17:01:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584842
;

-- 2021-06-11T14:11:49.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2021-06-11 17:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584842
;

