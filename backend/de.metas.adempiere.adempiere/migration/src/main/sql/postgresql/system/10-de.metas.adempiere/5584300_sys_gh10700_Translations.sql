


-- 2021-03-29T17:17:46.143854300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Sektionswechsel von {0} zu {1} durchgeführt am {2}.',Updated=TO_TIMESTAMP('2021-03-29 20:17:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545028
;

-- 2021-03-29T17:17:57.416054600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Sektionswechsel von {0} zu {1} durchgeführt am {2}.',Updated=TO_TIMESTAMP('2021-03-29 20:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545028
;

-- 2021-03-29T17:18:13.141124400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Org switch from organization {0} to organization {1}  performed on date {2}.',Updated=TO_TIMESTAMP('2021-03-29 20:18:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545028
;

-- 2021-03-29T17:18:30.311124800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Org switch from organization {0} to organization {1}  performed on date {2}.',Updated=TO_TIMESTAMP('2021-03-29 20:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545028
;

















-- 2021-03-29T17:20:23.313986200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Organisation Change',Updated=TO_TIMESTAMP('2021-03-29 20:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584810
;

-- 2021-03-29T17:21:40.097882500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Organisation Change',Updated=TO_TIMESTAMP('2021-03-29 20:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542353
;

-- 2021-03-29T17:23:05.924684200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel-Sektion', PrintName='Ziel-Sektion',Updated=TO_TIMESTAMP('2021-03-29 20:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542976 AND AD_Language='fr_CH'
;

-- 2021-03-29T17:23:05.943253600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542976,'fr_CH') 
;

-- 2021-03-29T17:23:23.436220100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Targer Organization', PrintName='Targer Organization',Updated=TO_TIMESTAMP('2021-03-29 20:23:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542976 AND AD_Language='en_GB'
;

-- 2021-03-29T17:23:23.442221500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542976,'en_GB') 
;

-- 2021-03-29T17:23:36.931838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Target Organization', PrintName='Target Organization',Updated=TO_TIMESTAMP('2021-03-29 20:23:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542976 AND AD_Language='en_GB'
;

-- 2021-03-29T17:23:36.937468800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542976,'en_GB') 
;

-- 2021-03-29T17:23:45.871866800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel-Sektion', PrintName='Ziel-Sektion',Updated=TO_TIMESTAMP('2021-03-29 20:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542976 AND AD_Language='de_CH'
;

-- 2021-03-29T17:23:45.877217600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542976,'de_CH') 
;

-- 2021-03-29T17:23:58.125643300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel-Sektion', PrintName='Ziel-Sektion',Updated=TO_TIMESTAMP('2021-03-29 20:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542976 AND AD_Language='de_DE'
;

-- 2021-03-29T17:23:58.130845200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542976,'de_DE') 
;

-- 2021-03-29T17:23:58.146780300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542976,'de_DE') 
;

-- 2021-03-29T17:23:58.160291400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Org_Target_ID', Name='Ziel-Sektion', Description=NULL, Help=NULL WHERE AD_Element_ID=542976
;

-- 2021-03-29T17:23:58.163138600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_Target_ID', Name='Ziel-Sektion', Description=NULL, Help=NULL, AD_Element_ID=542976 WHERE UPPER(ColumnName)='AD_ORG_TARGET_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-29T17:23:58.166560600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_Target_ID', Name='Ziel-Sektion', Description=NULL, Help=NULL WHERE AD_Element_ID=542976 AND IsCentrallyMaintained='Y'
;

-- 2021-03-29T17:23:58.170693300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ziel-Sektion', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542976) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542976)
;

-- 2021-03-29T17:23:58.183291900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ziel-Sektion', Name='Ziel-Sektion' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542976)
;

-- 2021-03-29T17:23:58.185750900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ziel-Sektion', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542976
;

-- 2021-03-29T17:23:58.189712900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ziel-Sektion', Description=NULL, Help=NULL WHERE AD_Element_ID = 542976
;

-- 2021-03-29T17:23:58.192480800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ziel-Sektion', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542976
;

-- 2021-03-29T17:24:09.120696100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Target Organization', PrintName='Target Organization',Updated=TO_TIMESTAMP('2021-03-29 20:24:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542976 AND AD_Language='en_US'
;

-- 2021-03-29T17:24:09.126264200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542976,'en_US') 
;

-- 2021-03-29T17:25:37.284255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578994,0,'M_Product_Membership_ID',TO_TIMESTAMP('2021-03-29 20:25:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mitgliedschafts-Produkt','Mitgliedschafts-Produkt',TO_TIMESTAMP('2021-03-29 20:25:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-29T17:25:37.292004600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578994 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-29T17:26:20.541555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Membership Product', PrintName='Membership Product',Updated=TO_TIMESTAMP('2021-03-29 20:26:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578994 AND AD_Language='en_US'
;

-- 2021-03-29T17:26:20.548952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578994,'en_US') 
;

-- 2021-03-29T17:26:59.858672100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=578994, AD_Reference_Value_ID=540272, ColumnName='M_Product_Membership_ID', Description=NULL, Help=NULL, Name='Mitgliedschafts-Produkt',Updated=TO_TIMESTAMP('2021-03-29 20:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541947
;

-- 2021-03-29T17:27:27.180974800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wechseldatum', PrintName='Wechseldatum',Updated=TO_TIMESTAMP('2021-03-29 20:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578875 AND AD_Language='de_CH'
;

-- 2021-03-29T17:27:27.186689200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578875,'de_CH') 
;

-- 2021-03-29T17:27:31.320515800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wechseldatum', PrintName='Wechseldatum',Updated=TO_TIMESTAMP('2021-03-29 20:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578875 AND AD_Language='de_DE'
;

-- 2021-03-29T17:27:31.326765500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578875,'de_DE') 
;

-- 2021-03-29T17:27:31.344205400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578875,'de_DE') 
;

-- 2021-03-29T17:27:31.354535200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Date_OrgChange', Name='Wechseldatum', Description=NULL, Help=NULL WHERE AD_Element_ID=578875
;

-- 2021-03-29T17:27:31.357266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Date_OrgChange', Name='Wechseldatum', Description=NULL, Help=NULL, AD_Element_ID=578875 WHERE UPPER(ColumnName)='DATE_ORGCHANGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-29T17:27:31.359970900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Date_OrgChange', Name='Wechseldatum', Description=NULL, Help=NULL WHERE AD_Element_ID=578875 AND IsCentrallyMaintained='Y'
;

-- 2021-03-29T17:27:31.362073600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wechseldatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578875) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578875)
;

-- 2021-03-29T17:27:31.375491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wechseldatum', Name='Wechseldatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578875)
;

-- 2021-03-29T17:27:31.378670500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Wechseldatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578875
;

-- 2021-03-29T17:27:31.381874600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Wechseldatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 578875
;

-- 2021-03-29T17:27:31.384707100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Wechseldatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578875
;

-- 2021-03-29T17:27:46.303733300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Organization Change Date', PrintName='Organization Change Date',Updated=TO_TIMESTAMP('2021-03-29 20:27:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578875 AND AD_Language='en_US'
;

-- 2021-03-29T17:27:46.308710300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578875,'en_US') 
;

-- 2021-03-29T17:29:24.564745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Sektionswechsel Log',Updated=TO_TIMESTAMP('2021-03-29 20:29:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541601
;

-- 2021-03-29T17:29:30.556385900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Sektionswechsel Log',Updated=TO_TIMESTAMP('2021-03-29 20:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541601
;

-- 2021-03-29T17:29:38.804574800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Organization Change History',Updated=TO_TIMESTAMP('2021-03-29 20:29:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541601
;

-- 2021-03-29T17:29:44.921815800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-29 20:29:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541601
;

-- 2021-03-29T17:29:49.795952900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-29 20:29:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541601
;

-- 2021-03-29T17:30:03.446002900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Organization Change History',Updated=TO_TIMESTAMP('2021-03-29 20:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541601
;

-- 2021-03-29T17:30:25.578595500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sektionswechsel Log', PrintName='Sektionswechsel Log',Updated=TO_TIMESTAMP('2021-03-29 20:30:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578832 AND AD_Language='de_CH'
;

-- 2021-03-29T17:30:25.584038100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578832,'de_CH') 
;

-- 2021-03-29T17:30:29.793766300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sektionswechsel Log', PrintName='Sektionswechsel Log',Updated=TO_TIMESTAMP('2021-03-29 20:30:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578832 AND AD_Language='de_DE'
;

-- 2021-03-29T17:30:29.799328600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578832,'de_DE') 
;

-- 2021-03-29T17:30:29.824790800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578832,'de_DE') 
;

-- 2021-03-29T17:30:29.835790900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_OrgChange_History_ID', Name='Sektionswechsel Log', Description=NULL, Help=NULL WHERE AD_Element_ID=578832
;

-- 2021-03-29T17:30:29.838877600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_OrgChange_History_ID', Name='Sektionswechsel Log', Description=NULL, Help=NULL, AD_Element_ID=578832 WHERE UPPER(ColumnName)='AD_ORGCHANGE_HISTORY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-29T17:30:29.841248500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_OrgChange_History_ID', Name='Sektionswechsel Log', Description=NULL, Help=NULL WHERE AD_Element_ID=578832 AND IsCentrallyMaintained='Y'
;

-- 2021-03-29T17:30:29.843343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sektionswechsel Log', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578832) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578832)
;

-- 2021-03-29T17:30:29.857331500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sektionswechsel Log', Name='Sektionswechsel Log' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578832)
;

-- 2021-03-29T17:30:29.860130600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sektionswechsel Log', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578832
;

-- 2021-03-29T17:30:29.862815600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sektionswechsel Log', Description=NULL, Help=NULL WHERE AD_Element_ID = 578832
;

-- 2021-03-29T17:30:29.866562900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sektionswechsel Log', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578832
;

-- 2021-03-29T17:30:43.999427200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Organization Change History', PrintName='Organization Change History',Updated=TO_TIMESTAMP('2021-03-29 20:30:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578832 AND AD_Language='en_US'
;

-- 2021-03-29T17:30:44.005453900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578832,'en_US') 
;

-- 2021-03-29T17:32:00.639377700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sektions-Mapping', PrintName='Sektions-Mapping',Updated=TO_TIMESTAMP('2021-03-29 20:32:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578815 AND AD_Language='de_CH'
;

-- 2021-03-29T17:32:00.645313800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578815,'de_CH') 
;

-- 2021-03-29T17:32:03.818493600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sektions-Mapping', PrintName='Sektions-Mapping',Updated=TO_TIMESTAMP('2021-03-29 20:32:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578815 AND AD_Language='de_DE'
;

-- 2021-03-29T17:32:03.822681200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578815,'de_DE') 
;

-- 2021-03-29T17:32:03.841160700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578815,'de_DE') 
;

-- 2021-03-29T17:32:03.845795300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Org_Mapping_ID', Name='Sektions-Mapping', Description=NULL, Help=NULL WHERE AD_Element_ID=578815
;

-- 2021-03-29T17:32:03.850434900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_Mapping_ID', Name='Sektions-Mapping', Description=NULL, Help=NULL, AD_Element_ID=578815 WHERE UPPER(ColumnName)='AD_ORG_MAPPING_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-29T17:32:03.855118800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_Mapping_ID', Name='Sektions-Mapping', Description=NULL, Help=NULL WHERE AD_Element_ID=578815 AND IsCentrallyMaintained='Y'
;

-- 2021-03-29T17:32:03.858511800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sektions-Mapping', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578815) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578815)
;

-- 2021-03-29T17:32:03.880614300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sektions-Mapping', Name='Sektions-Mapping' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578815)
;

-- 2021-03-29T17:32:03.883692800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sektions-Mapping', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578815
;

-- 2021-03-29T17:32:03.886941300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sektions-Mapping', Description=NULL, Help=NULL WHERE AD_Element_ID = 578815
;

-- 2021-03-29T17:32:03.889147600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sektions-Mapping', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578815
;

-- 2021-03-29T17:32:14.731680600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Organization Mapping', PrintName='Organization Mapping',Updated=TO_TIMESTAMP('2021-03-29 20:32:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578815 AND AD_Language='en_US'
;

-- 2021-03-29T17:32:14.737112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578815,'en_US') 
;

-- 2021-03-29T17:33:13.421454100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Upsprungs-Sektion', PrintName='Upsprungs-Sektion',Updated=TO_TIMESTAMP('2021-03-29 20:33:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578833 AND AD_Language='de_CH'
;

-- 2021-03-29T17:33:13.426975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578833,'de_CH') 
;

-- 2021-03-29T17:33:17.051186900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Upsprungs-Sektion', PrintName='Upsprungs-Sektion',Updated=TO_TIMESTAMP('2021-03-29 20:33:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578833 AND AD_Language='de_DE'
;

-- 2021-03-29T17:33:17.056414700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578833,'de_DE') 
;

-- 2021-03-29T17:33:17.071521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578833,'de_DE') 
;

-- 2021-03-29T17:33:17.081084900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Org_From_ID', Name='Upsprungs-Sektion', Description=NULL, Help=NULL WHERE AD_Element_ID=578833
;

-- 2021-03-29T17:33:17.083861500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_From_ID', Name='Upsprungs-Sektion', Description=NULL, Help=NULL, AD_Element_ID=578833 WHERE UPPER(ColumnName)='AD_ORG_FROM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-29T17:33:17.086558900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_From_ID', Name='Upsprungs-Sektion', Description=NULL, Help=NULL WHERE AD_Element_ID=578833 AND IsCentrallyMaintained='Y'
;

-- 2021-03-29T17:33:17.088851100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Upsprungs-Sektion', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578833) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578833)
;

-- 2021-03-29T17:33:17.103300600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Upsprungs-Sektion', Name='Upsprungs-Sektion' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578833)
;

-- 2021-03-29T17:33:17.106050400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Upsprungs-Sektion', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578833
;

-- 2021-03-29T17:33:17.108760300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Upsprungs-Sektion', Description=NULL, Help=NULL WHERE AD_Element_ID = 578833
;

-- 2021-03-29T17:33:17.111054500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Upsprungs-Sektion', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578833
;

-- 2021-03-29T17:33:35.216168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Organization From', PrintName='Organization From',Updated=TO_TIMESTAMP('2021-03-29 20:33:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578833 AND AD_Language='en_US'
;

-- 2021-03-29T17:33:35.218381500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578833,'en_US') 
;

-- 2021-03-29T17:34:51.008457500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel-Sektion', PrintName='Ziel-Sektion',Updated=TO_TIMESTAMP('2021-03-29 20:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578864 AND AD_Language='de_CH'
;

-- 2021-03-29T17:34:51.013135300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578864,'de_CH') 
;

-- 2021-03-29T17:34:54.949492300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel-Sektion', PrintName='Ziel-Sektion',Updated=TO_TIMESTAMP('2021-03-29 20:34:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578864 AND AD_Language='de_DE'
;

-- 2021-03-29T17:34:54.955073200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578864,'de_DE') 
;

-- 2021-03-29T17:34:54.976436600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578864,'de_DE') 
;

-- 2021-03-29T17:34:54.981567300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Ziel-Sektion', Description=NULL, Help=NULL WHERE AD_Element_ID=578864
;

-- 2021-03-29T17:34:54.985661700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Ziel-Sektion', Description=NULL, Help=NULL WHERE AD_Element_ID=578864 AND IsCentrallyMaintained='Y'
;

-- 2021-03-29T17:34:54.989802200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ziel-Sektion', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578864) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578864)
;

-- 2021-03-29T17:34:55.003816100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ziel-Sektion', Name='Ziel-Sektion' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578864)
;

-- 2021-03-29T17:34:55.006139300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ziel-Sektion', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578864
;

-- 2021-03-29T17:34:55.008905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ziel-Sektion', Description=NULL, Help=NULL WHERE AD_Element_ID = 578864
;

-- 2021-03-29T17:34:55.011574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ziel-Sektion', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578864
;

-- 2021-03-29T17:35:04.718327200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Organization To', PrintName='Organization To',Updated=TO_TIMESTAMP('2021-03-29 20:35:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578864 AND AD_Language='en_US'
;

-- 2021-03-29T17:35:04.723643900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578864,'en_US') 
;

-- 2021-03-29T17:35:10.793775700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-29 20:35:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578864 AND AD_Language='en_US'
;

-- 2021-03-29T17:35:10.798786700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578864,'en_US') 
;

-- 2021-03-29T17:35:51.943273800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Upsprungs-Geschäftspartner', PrintName='Upsprungs-Geschäftspartner',Updated=TO_TIMESTAMP('2021-03-29 20:35:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578873 AND AD_Language='de_CH'
;

-- 2021-03-29T17:35:51.946109400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578873,'de_CH') 
;

-- 2021-03-29T17:35:55.953953500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Upsprungs-Geschäftspartner', PrintName='Upsprungs-Geschäftspartner',Updated=TO_TIMESTAMP('2021-03-29 20:35:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578873 AND AD_Language='de_DE'
;

-- 2021-03-29T17:35:55.958845200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578873,'de_DE') 
;

-- 2021-03-29T17:35:55.980213400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578873,'de_DE') 
;

-- 2021-03-29T17:35:55.984748600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_From_ID', Name='Upsprungs-Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=578873
;

-- 2021-03-29T17:35:55.988744500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_From_ID', Name='Upsprungs-Geschäftspartner', Description=NULL, Help=NULL, AD_Element_ID=578873 WHERE UPPER(ColumnName)='C_BPARTNER_FROM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-29T17:35:55.992061400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_From_ID', Name='Upsprungs-Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=578873 AND IsCentrallyMaintained='Y'
;

-- 2021-03-29T17:35:55.994775200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Upsprungs-Geschäftspartner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578873) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578873)
;

-- 2021-03-29T17:35:56.015558800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Upsprungs-Geschäftspartner', Name='Upsprungs-Geschäftspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578873)
;

-- 2021-03-29T17:35:56.018836900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Upsprungs-Geschäftspartner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578873
;

-- 2021-03-29T17:35:56.022731100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Upsprungs-Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID = 578873
;

-- 2021-03-29T17:35:56.025947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Upsprungs-Geschäftspartner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578873
;

-- 2021-03-29T17:36:26.913808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel-Geschäftspartner', PrintName='Ziel-Geschäftspartner',Updated=TO_TIMESTAMP('2021-03-29 20:36:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578874 AND AD_Language='de_CH'
;

-- 2021-03-29T17:36:26.918003700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578874,'de_CH') 
;

-- 2021-03-29T17:36:31.021774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel-Geschäftspartner', PrintName='Ziel-Geschäftspartner',Updated=TO_TIMESTAMP('2021-03-29 20:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578874 AND AD_Language='de_DE'
;

-- 2021-03-29T17:36:31.026436300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578874,'de_DE') 
;

-- 2021-03-29T17:36:31.044746100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578874,'de_DE') 
;

-- 2021-03-29T17:36:31.051024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_To_ID', Name='Ziel-Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=578874
;

-- 2021-03-29T17:36:31.055674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_To_ID', Name='Ziel-Geschäftspartner', Description=NULL, Help=NULL, AD_Element_ID=578874 WHERE UPPER(ColumnName)='C_BPARTNER_TO_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-29T17:36:31.060339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_To_ID', Name='Ziel-Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=578874 AND IsCentrallyMaintained='Y'
;

-- 2021-03-29T17:36:31.064329700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ziel-Geschäftspartner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578874) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578874)
;

-- 2021-03-29T17:36:31.089595100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ziel-Geschäftspartner', Name='Ziel-Geschäftspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578874)
;

-- 2021-03-29T17:36:31.092738900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ziel-Geschäftspartner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578874
;

-- 2021-03-29T17:36:31.095999800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ziel-Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID = 578874
;

-- 2021-03-29T17:36:31.098131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ziel-Geschäftspartner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578874
;

-- 2021-03-29T17:37:14.771180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.', Help='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.',Updated=TO_TIMESTAMP('2021-03-29 20:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584810
;

-- 2021-03-29T17:37:24.197588100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.', Help='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-29 20:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584810
;

-- 2021-03-29T17:37:29.933626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.', Help='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-29 20:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584810
;

-- 2021-03-29T17:38:00.497052900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process is scheduled to be run each night to deactivate bpartners (and their locations, contacts and bank accounts) that were moved to another organization and are scheduled to be deactivated at a given date via Organization Change History.',Updated=TO_TIMESTAMP('2021-03-29 20:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584810
;

-- 2021-03-29T17:38:05.287692300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Help='This process is scheduled to be run each night to deactivate bpartners (and their locations, contacts and bank accounts) that were moved to another organization and are scheduled to be deactivated at a given date via Organization Change History.',Updated=TO_TIMESTAMP('2021-03-29 20:38:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584810
;

-- 2021-03-29T17:38:44.456822400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='', Help='',Updated=TO_TIMESTAMP('2021-03-29 20:38:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584810
;

-- 2021-03-29T17:38:52.445120500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2021-03-29 20:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584810
;

-- 2021-03-29T17:38:55.442836500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2021-03-29 20:38:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584810
;

-- 2021-03-29T17:39:00.635090700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2021-03-29 20:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584810
;

-- 2021-03-29T17:40:43.850755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.', Help='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.', TechnicalNote='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.',Updated=TO_TIMESTAMP('2021-03-29 20:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584814
;

-- 2021-03-29T17:40:57.615745600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.', Help='Dieser Prozess wird jede Nacht ausgeführt, um Geschäftspartner, die entsprechend dem Sektionswechsel Log an dem jeweiligen Datum deaktivert werden sollen.',Updated=TO_TIMESTAMP('2021-03-29 20:40:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584814
;

-- 2021-03-29T17:41:30.582919300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='This process is scheduled to be run each night to deactivate bpartners (and their locations, contacts and bank accounts) that were moved to another organization and are scheduled to be deactivated at a given date via Organization Change History.', Help='This process is scheduled to be run each night to deactivate bpartners (and their locations, contacts and bank accounts) that were moved to another organization and are scheduled to be deactivated at a given date via Organization Change History.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-29 20:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584814
;

-- 2021-03-29T17:41:46.891214200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-29 20:41:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584814
;

-- 2021-03-29T17:41:50.861441500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-29 20:41:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584814
;








-- 2021-03-29T18:15:16.573523800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Sektionswechsel von {0} zu {1} durchgeführt am {2}.',Updated=TO_TIMESTAMP('2021-03-29 21:15:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545028
;

