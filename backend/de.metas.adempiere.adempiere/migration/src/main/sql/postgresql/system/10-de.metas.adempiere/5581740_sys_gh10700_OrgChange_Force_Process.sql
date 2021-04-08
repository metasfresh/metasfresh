-- 2021-03-09T17:53:33.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,IsNotifyUserAfterExecution,PostgrestResponseFormat,AD_Org_ID,Name,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2021-03-09 19:53:33','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-03-09 19:53:33','YYYY-MM-DD HH24:MI:SS'),'N','N','3','N','N','N',100,584810,'C_BPartner_MoveToAnotherOrg','Y','Y','N','N','N',0,'Java','Y','N','json',0,'Sektionswechsel','de.metas.bpartner.process.C_BPartner_MoveToAnotherOrg','D')
;

-- 2021-03-09T17:53:33.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584810 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-03-09T17:53:50.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584810,291,540918,TO_TIMESTAMP('2021-03-09 19:53:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-03-09 19:53:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-03-09T17:56:27.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541275,TO_TIMESTAMP('2021-03-09 19:56:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_Org different from current and 0',TO_TIMESTAMP('2021-03-09 19:56:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-03-09T17:56:27.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541275 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-09T17:57:39.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,528,528,0,541275,155,TO_TIMESTAMP('2021-03-09 19:57:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','N',TO_TIMESTAMP('2021-03-09 19:57:39','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org.AD_Org_ID NOT IN ( @AD_Org_ID/-1@ , 0)')
;

-- 2021-03-09T17:58:07.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542976,0,584810,541946,30,541275,'AD_Org_Target_ID',TO_TIMESTAMP('2021-03-09 19:58:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product',0,'Y','N','Y','N','N','N','AD_Org_Target_ID',10,TO_TIMESTAMP('2021-03-09 19:58:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-09T17:58:07.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541946 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-03-09T17:58:15.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='AD_Org Different from Current and 0',Updated=TO_TIMESTAMP('2021-03-09 19:58:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541275
;

-- 2021-03-09T17:58:37.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-03-09 19:58:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541946
;

-- 2021-03-09T17:59:39.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,584810,541947,30,'M_Product_ID',TO_TIMESTAMP('2021-03-09 19:59:39','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','D',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',20,TO_TIMESTAMP('2021-03-09 19:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-09T17:59:39.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541947 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-03-09T18:00:17.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540535,TO_TIMESTAMP('2021-03-09 20:00:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Product from M_Product_Cagetory = Membership','S',TO_TIMESTAMP('2021-03-09 20:00:17','YYYY-MM-DD HH24:MI:SS'),100)
;





-- 2021-03-09T18:08:56.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID = 0 AND M_Product.M_Product_Category_ID = (Select pc.M_Product_Category_ID from M_Product_Category pc where pc.AD_Org_ID = 0 and pc.Value = ''Membership'')',Updated=TO_TIMESTAMP('2021-03-09 20:08:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540535
;

-- 2021-03-09T18:09:24.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540535,Updated=TO_TIMESTAMP('2021-03-09 20:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541947
;

-- 2021-03-09T18:14:25.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,584810,541948,15,'DateFrom',TO_TIMESTAMP('2021-03-09 20:14:24','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@  + INTERVAL ''1 day''','Startdatum eines Abschnittes','D',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','N','N','Datum von',30,TO_TIMESTAMP('2021-03-09 20:14:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-09T18:14:25.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541948 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-03-09T18:33:02.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578813,0,'showMembershipParameter',TO_TIMESTAMP('2021-03-09 20:33:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','showMembershipParameter','showMembershipParameter',TO_TIMESTAMP('2021-03-09 20:33:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-09T18:33:02.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578813 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-09T18:33:28.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsShowMembershipParameter', Name='IsShowMembershipParameter', PrintName='IsShowMembershipParameter',Updated=TO_TIMESTAMP('2021-03-09 20:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578813
;

-- 2021-03-09T18:33:28.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsShowMembershipParameter', Name='IsShowMembershipParameter', Description=NULL, Help=NULL WHERE AD_Element_ID=578813
;

-- 2021-03-09T18:33:28.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsShowMembershipParameter', Name='IsShowMembershipParameter', Description=NULL, Help=NULL, AD_Element_ID=578813 WHERE UPPER(ColumnName)='ISSHOWMEMBERSHIPPARAMETER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-09T18:33:28.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsShowMembershipParameter', Name='IsShowMembershipParameter', Description=NULL, Help=NULL WHERE AD_Element_ID=578813 AND IsCentrallyMaintained='Y'
;

-- 2021-03-09T18:33:28.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='IsShowMembershipParameter', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578813) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578813)
;

-- 2021-03-09T18:33:28.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='IsShowMembershipParameter', Name='IsShowMembershipParameter' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578813)
;

-- 2021-03-09T18:33:28.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='IsShowMembershipParameter', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578813
;

-- 2021-03-09T18:33:28.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='IsShowMembershipParameter', Description=NULL, Help=NULL WHERE AD_Element_ID = 578813
;

-- 2021-03-09T18:33:28.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'IsShowMembershipParameter', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578813
;

-- 2021-03-09T18:33:32.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='IsShowMembershipParameter', PrintName='IsShowMembershipParameter',Updated=TO_TIMESTAMP('2021-03-09 20:33:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578813 AND AD_Language='de_CH'
;

-- 2021-03-09T18:33:32.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578813,'de_CH') 
;

-- 2021-03-09T18:33:36.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='IsShowMembershipParameter', PrintName='IsShowMembershipParameter',Updated=TO_TIMESTAMP('2021-03-09 20:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578813 AND AD_Language='de_DE'
;

-- 2021-03-09T18:33:36.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578813,'de_DE') 
;

-- 2021-03-09T18:33:36.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578813,'de_DE') 
;

-- 2021-03-09T18:33:36.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsShowMembershipParameter', Name='IsShowMembershipParameter', Description=NULL, Help=NULL WHERE AD_Element_ID=578813
;

-- 2021-03-09T18:33:36.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsShowMembershipParameter', Name='IsShowMembershipParameter', Description=NULL, Help=NULL, AD_Element_ID=578813 WHERE UPPER(ColumnName)='ISSHOWMEMBERSHIPPARAMETER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-09T18:33:36.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsShowMembershipParameter', Name='IsShowMembershipParameter', Description=NULL, Help=NULL WHERE AD_Element_ID=578813 AND IsCentrallyMaintained='Y'
;

-- 2021-03-09T18:33:36.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='IsShowMembershipParameter', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578813) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578813)
;

-- 2021-03-09T18:33:36.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='IsShowMembershipParameter', Name='IsShowMembershipParameter' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578813)
;

-- 2021-03-09T18:33:36.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='IsShowMembershipParameter', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578813
;

-- 2021-03-09T18:33:36.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='IsShowMembershipParameter', Description=NULL, Help=NULL WHERE AD_Element_ID = 578813
;

-- 2021-03-09T18:33:36.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'IsShowMembershipParameter', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578813
;

-- 2021-03-09T18:33:40.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='IsShowMembershipParameter', PrintName='IsShowMembershipParameter',Updated=TO_TIMESTAMP('2021-03-09 20:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578813 AND AD_Language='en_US'
;

-- 2021-03-09T18:33:40.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578813,'en_US') 
;

-- 2021-03-09T18:33:44.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='IsShowMembershipParameter', PrintName='IsShowMembershipParameter',Updated=TO_TIMESTAMP('2021-03-09 20:33:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578813 AND AD_Language='nl_NL'
;

-- 2021-03-09T18:33:44.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578813,'nl_NL') 
;

-- 2021-03-09T18:34:26.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578813,0,584810,541949,20,'showMembershipParameter',TO_TIMESTAMP('2021-03-09 20:34:26','YYYY-MM-DD HH24:MI:SS'),100,'N','1=0','D',0,'Y','N','Y','N','N','N','showMembershipParameter',40,TO_TIMESTAMP('2021-03-09 20:34:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-09T18:34:26.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541949 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-03-09T18:34:28.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-03-09 20:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541949
;

-- 2021-03-09T18:34:51.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsShowMembershipParameter', EntityType='de.metas.printing', Name='IsShowMembershipParameter',Updated=TO_TIMESTAMP('2021-03-09 20:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541949
;

-- 2021-03-09T18:35:06.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2021-03-09 20:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541949
;

-- 2021-03-09T18:35:16.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=578813,Updated=TO_TIMESTAMP('2021-03-09 20:35:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541949
;

-- 2021-03-09T18:35:26.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2021-03-09 20:35:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541949
;

-- 2021-03-09T18:35:32.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=578813,Updated=TO_TIMESTAMP('2021-03-09 20:35:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541949
;

-- 2021-03-09T19:22:13.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.contracts.bpartner.process.C_BPartner_MoveToAnotherOrg',Updated=TO_TIMESTAMP('2021-03-09 21:22:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584810
;








-- 2021-03-11T12:18:13.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID = 0
  AND M_Product.M_Product_Category_ID = (SELECT pc.M_Product_Category_ID
                                         FROM M_Product_Category pc
                                         WHERE pc.AD_Org_ID = 0
                                           AND pc.Value = ''Membership'')
  AND M_Product.M_Product_Mapping_ID IN (SELECT p2.M_Product_Mapping_ID
                                         FROM M_Product p2
                                         WHERE p2.M_Product_Category_ID = M_Product.M_Product_cagetory_ID
                                           AND p2.AD_Org_ID = @AD_Org_Target_ID / -1@)',Updated=TO_TIMESTAMP('2021-03-11 14:18:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540535
;



-- 2021-03-16T13:22:01.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID = 0 AND M_Product.M_Product_Category_ID = (SELECT pc.M_Product_Category_ID FROM M_Product_Category pc WHERE pc.AD_Org_ID = 0 AND pc.Value = ''Membership'') AND M_Product.M_Product_Mapping_ID IN (SELECT p2.M_Product_Mapping_ID FROM M_Product p2 WHERE p2.M_Product_Category_ID = M_Product.M_Product_category_ID AND p2.AD_Org_ID = @AD_Org_Target_ID / -1@)',Updated=TO_TIMESTAMP('2021-03-16 15:22:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540535
;

-- 2021-03-16T13:24:13.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@IsShowMembershipParameter@ = ''Y''',Updated=TO_TIMESTAMP('2021-03-16 15:24:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541947
;


-- 2021-03-16T14:56:15.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@#Date@::Date  + INTERVAL ''1 day''',Updated=TO_TIMESTAMP('2021-03-16 16:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541948
;

-- 2021-03-16T15:01:59.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='(@#Date@  + INTERVAL ''1 day'')::date',Updated=TO_TIMESTAMP('2021-03-16 17:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541948
;

-- None. Deal with it in java 


-- 2021-03-16T15:21:31.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='',Updated=TO_TIMESTAMP('2021-03-16 17:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541948
;


-- 2021-03-16T15:46:25.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-03-16 17:46:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541948
;






-- 2021-03-18T10:02:12.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578875,0,'Date_OrgChange',TO_TIMESTAMP('2021-03-18 12:02:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Org Change Date','Org Change Date',TO_TIMESTAMP('2021-03-18 12:02:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-18T10:02:12.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578875 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-18T10:02:34.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=578875, ColumnName='Date_OrgChange', Description=NULL, Help=NULL, Name='Org Change Date',Updated=TO_TIMESTAMP('2021-03-18 12:02:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541948
;







