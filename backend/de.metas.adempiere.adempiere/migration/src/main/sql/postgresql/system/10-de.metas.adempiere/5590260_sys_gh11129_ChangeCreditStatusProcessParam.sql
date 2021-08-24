-- 2021-05-27T06:00:17.619Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540938,542011,17,'SetCreditStatus',TO_TIMESTAMP('2021-05-27 08:00:17','YYYY-MM-DD HH24:MI:SS'),100,'D',1,'Y','N','Y','N','N','N','SetCreditStatus',20,TO_TIMESTAMP('2021-05-27 08:00:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-27T06:00:17.625Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542011 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-27T06:05:41.705Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579259,0,TO_TIMESTAMP('2021-05-27 08:05:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Credit Status','Credit Status',TO_TIMESTAMP('2021-05-27 08:05:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-27T06:05:41.722Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579259 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-27T06:06:13.816Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kreditstatus', PrintName='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579259 AND AD_Language='de_CH'
;

-- 2021-05-27T06:06:13.844Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579259,'de_CH') 
;

-- 2021-05-27T06:06:23.278Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kreditstatus', PrintName='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:06:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579259 AND AD_Language='de_DE'
;

-- 2021-05-27T06:06:23.278Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579259,'de_DE') 
;

-- 2021-05-27T06:06:23.283Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579259,'de_DE') 
;

-- 2021-05-27T06:06:23.287Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Kreditstatus', Description=NULL, Help=NULL WHERE AD_Element_ID=579259
;

-- 2021-05-27T06:06:23.287Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Kreditstatus', Description=NULL, Help=NULL WHERE AD_Element_ID=579259 AND IsCentrallyMaintained='Y'
;

-- 2021-05-27T06:06:23.288Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Kreditstatus', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579259) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579259)
;

-- 2021-05-27T06:06:23.312Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Kreditstatus', Name='Kreditstatus' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579259)
;

-- 2021-05-27T06:06:23.313Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Kreditstatus', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579259
;

-- 2021-05-27T06:06:23.314Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Kreditstatus', Description=NULL, Help=NULL WHERE AD_Element_ID = 579259
;

-- 2021-05-27T06:06:23.315Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Kreditstatus', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579259
;

-- 2021-05-27T06:06:35.760Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-27 08:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579259 AND AD_Language='en_US'
;

-- 2021-05-27T06:06:35.764Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579259,'en_US') 
;

-- 2021-05-27T06:06:51.396Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Kreditstatus', PrintName='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:06:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579259 AND AD_Language='nl_NL'
;

-- 2021-05-27T06:06:51.400Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579259,'nl_NL') 
;

-- 2021-05-27T06:07:37.081Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=2181, ColumnName='SOCreditStatus', Description='Kreditstatus des Geschäftspartners', Help='Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.', Name='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542011
;

-- 2021-05-27T06:16:10.806Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541325,TO_TIMESTAMP('2021-05-27 08:16:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','CreditStatus',TO_TIMESTAMP('2021-05-27 08:16:10','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-05-27T06:16:10.846Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541325 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-05-27T06:16:25.483Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541325
;

-- 2021-05-27T06:16:30.830Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541325
;

-- 2021-05-27T06:16:36.006Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-27 08:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541325
;

-- 2021-05-27T06:16:40.680Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:16:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541325
;

-- 2021-05-27T06:17:44.304Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541325,542616,TO_TIMESTAMP('2021-05-27 08:17:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kredit Stop',TO_TIMESTAMP('2021-05-27 08:17:44','YYYY-MM-DD HH24:MI:SS'),100,'S','CreditStop')
;

-- 2021-05-27T06:17:44.308Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542616 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-27T06:17:54.092Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-27 08:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542616
;

-- 2021-05-27T06:17:58.150Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-27 08:17:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542616
;

-- 2021-05-27T06:18:08.803Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Credit Stop',Updated=TO_TIMESTAMP('2021-05-27 08:18:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542616
;

-- 2021-05-27T06:18:52.576Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541325,542617,TO_TIMESTAMP('2021-05-27 08:18:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kredit OK',TO_TIMESTAMP('2021-05-27 08:18:52','YYYY-MM-DD HH24:MI:SS'),100,'O','KreditOK')
;

-- 2021-05-27T06:18:52.579Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542617 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-27T06:19:03.312Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Credit OK',Updated=TO_TIMESTAMP('2021-05-27 08:19:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542617
;

-- 2021-05-27T06:19:10.387Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-27 08:19:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542617
;

-- 2021-05-27T06:19:15.431Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-27 08:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542617
;

-- 2021-05-27T06:20:21.556Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541325,542618,TO_TIMESTAMP('2021-05-27 08:20:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Calculate',TO_TIMESTAMP('2021-05-27 08:20:21','YYYY-MM-DD HH24:MI:SS'),100,'U','Calculate')
;

-- 2021-05-27T06:20:21.567Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542618 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-27T06:20:51.054Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541325,Updated=TO_TIMESTAMP('2021-05-27 08:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542011
;

-- 2021-05-27T06:23:37.572Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-05-27 08:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542011
;

-- 2021-05-27T06:31:08.970Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2021-05-27 08:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541273
;

-- 2021-05-27T06:50:58.360Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579260,0,'setCreditStatus',TO_TIMESTAMP('2021-05-27 08:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Kreditstatus des Geschäftspartners','D','Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.','Y','CreditStatus','CreditStatus',TO_TIMESTAMP('2021-05-27 08:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-27T06:50:58.369Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579260 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-27T06:51:05.674Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-27 08:51:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579260 AND AD_Language='en_US'
;

-- 2021-05-27T06:51:05.675Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579260,'en_US') 
;

-- 2021-05-27T06:51:17.963Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Kreditstatus', PrintName='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579260 AND AD_Language='nl_NL'
;

-- 2021-05-27T06:51:17.964Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579260,'nl_NL') 
;

-- 2021-05-27T06:51:26.263Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kreditstatus', PrintName='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:51:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579260 AND AD_Language='de_DE'
;

-- 2021-05-27T06:51:26.266Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579260,'de_DE') 
;

-- 2021-05-27T06:51:26.279Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579260,'de_DE') 
;

-- 2021-05-27T06:51:26.289Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='setCreditStatus', Name='Kreditstatus', Description='Kreditstatus des Geschäftspartners', Help='Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.' WHERE AD_Element_ID=579260
;

-- 2021-05-27T06:51:26.290Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='setCreditStatus', Name='Kreditstatus', Description='Kreditstatus des Geschäftspartners', Help='Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.', AD_Element_ID=579260 WHERE UPPER(ColumnName)='SETCREDITSTATUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-27T06:51:26.292Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='setCreditStatus', Name='Kreditstatus', Description='Kreditstatus des Geschäftspartners', Help='Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.' WHERE AD_Element_ID=579260 AND IsCentrallyMaintained='Y'
;

-- 2021-05-27T06:51:26.293Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Kreditstatus', Description='Kreditstatus des Geschäftspartners', Help='Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579260) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579260)
;

-- 2021-05-27T06:51:26.314Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Kreditstatus', Name='Kreditstatus' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579260)
;

-- 2021-05-27T06:51:26.315Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Kreditstatus', Description='Kreditstatus des Geschäftspartners', Help='Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.', CommitWarning = NULL WHERE AD_Element_ID = 579260
;

-- 2021-05-27T06:51:26.317Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Kreditstatus', Description='Kreditstatus des Geschäftspartners', Help='Credit Management is inactive if Credit Status is No Credit Check, Credit Stop or if the Credit Limit is 0.
If active, the status is set automatically set to Credit Hold, if the Total Open Balance (including Vendor activities)  is higher then the Credit Limit. It is set to Credit Watch, if above 90% of the Credit Limit and Credit OK otherwise.' WHERE AD_Element_ID = 579260
;

-- 2021-05-27T06:51:26.317Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Kreditstatus', Description = 'Kreditstatus des Geschäftspartners', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579260
;

-- 2021-05-27T06:51:35.208Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kreditstatus', PrintName='Kreditstatus',Updated=TO_TIMESTAMP('2021-05-27 08:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579260 AND AD_Language='de_CH'
;

-- 2021-05-27T06:51:35.208Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579260,'de_CH') 
;

-- 2021-05-27T06:52:02.509Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=579260, ColumnName='setCreditStatus',Updated=TO_TIMESTAMP('2021-05-27 08:52:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542011
;


-- 2021-05-27T07:11:25.361Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Berechnen lassen',Updated=TO_TIMESTAMP('2021-05-27 09:11:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542618
;

-- 2021-05-27T07:11:35.836Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Berechnen lassen',Updated=TO_TIMESTAMP('2021-05-27 09:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542618
;

-- 2021-05-27T07:11:40.590Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Berechnen lassen',Updated=TO_TIMESTAMP('2021-05-27 09:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542618
;

-- 2021-05-27T07:11:47.095Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-27 09:11:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542618
;

-- 2021-05-27T07:11:52.057Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='Berechnen lassen',Updated=TO_TIMESTAMP('2021-05-27 09:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542618
;
