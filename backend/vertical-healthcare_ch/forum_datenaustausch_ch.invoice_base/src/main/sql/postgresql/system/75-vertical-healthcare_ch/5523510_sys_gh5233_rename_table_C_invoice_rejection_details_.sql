

-- 2019-06-03T14:55:25.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zurückweisungsdetail', PrintName='Zurückweisungsdetail',Updated=TO_TIMESTAMP('2019-06-03 14:55:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='de_CH'
;

-- 2019-06-03T14:55:25.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'de_CH') 
;

-- 2019-06-03T14:55:27.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zurückweisungsdetail', PrintName='Zurückweisungsdetail',Updated=TO_TIMESTAMP('2019-06-03 14:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='de_DE'
;

-- 2019-06-03T14:55:27.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'de_DE') 
;

-- 2019-06-03T14:55:27.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576783,'de_DE') 
;

-- 2019-06-03T14:55:27.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Rejection_Details_ID', Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=576783
;

-- 2019-06-03T14:55:27.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Rejection_Details_ID', Name='Zurückweisungsdetail', Description=NULL, Help=NULL, AD_Element_ID=576783 WHERE UPPER(ColumnName)='C_INVOICE_REJECTION_DETAILS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-03T14:55:27.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Rejection_Details_ID', Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=576783 AND IsCentrallyMaintained='Y'
;

-- 2019-06-03T14:55:27.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576783)
;

-- 2019-06-03T14:55:27.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zurückweisungsdetail', Name='Zurückweisungsdetail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576783)
;

-- 2019-06-03T14:55:27.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zurückweisungsdetail', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576783
;

-- 2019-06-03T14:55:27.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID = 576783
;

-- 2019-06-03T14:55:27.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zurückweisungsdetail', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576783
;

-- 2019-06-03T14:55:31.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoice rejection detail', PrintName='Invoice rejection detail',Updated=TO_TIMESTAMP('2019-06-03 14:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='en_US'
;

-- 2019-06-03T14:55:31.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'en_US') 
;

-- 2019-06-03T14:55:31.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-03 14:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='de_DE'
;

-- 2019-06-03T14:55:31.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'de_DE') 
;

-- 2019-06-03T14:55:31.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576783,'de_DE') 
;

-- 2019-06-03T14:55:32.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-03 14:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='de_CH'
;

-- 2019-06-03T14:55:32.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'de_CH') 
;

-- 2019-06-03T14:55:39.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_Invoice_Rejection_Detail_ID',Updated=TO_TIMESTAMP('2019-06-03 14:55:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783
;

-- 2019-06-03T14:55:39.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Rejection_Detail_ID', Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=576783
;

-- 2019-06-03T14:55:39.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Rejection_Detail_ID', Name='Zurückweisungsdetail', Description=NULL, Help=NULL, AD_Element_ID=576783 WHERE UPPER(ColumnName)='C_INVOICE_REJECTION_DETAIL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-03T14:55:39.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Rejection_Detail_ID', Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=576783 AND IsCentrallyMaintained='Y'
;


-- 2019-06-03T14:59:28.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_Invoice_Rejection_Detail (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Invoice_ID NUMERIC(10), C_Invoice_Org_ID NUMERIC(10), C_Invoice_Rejection_Detail_ID NUMERIC(10) NOT NULL, Client VARCHAR(255), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, EMail VARCHAR(255), Explanation VARCHAR(255), InvoiceNumber VARCHAR(255), InvoiceRecipient VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsDone CHAR(1) DEFAULT 'N' CHECK (IsDone IN ('Y','N')) NOT NULL, Phone VARCHAR(30), Reason VARCHAR(255), ResponsiblePerson VARCHAR(255), Status VARCHAR(255), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CInvoice_CInvoiceRejectionDetail FOREIGN KEY (C_Invoice_ID) REFERENCES public.C_Invoice DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CInvoiceOrg_CInvoiceRejectionDetail FOREIGN KEY (C_Invoice_Org_ID) REFERENCES public.AD_Org DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Invoice_Rejection_Detail_Key PRIMARY KEY (C_Invoice_Rejection_Detail_ID))
;

-- 2019-06-03T15:00:37.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoice rejection detail', PrintName='Invoice rejection detail',Updated=TO_TIMESTAMP('2019-06-03 15:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='nl_NL'
;

-- 2019-06-03T15:00:37.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'nl_NL') 
;

-- 2019-06-03T15:00:55.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoice rejection detaill',Updated=TO_TIMESTAMP('2019-06-03 15:00:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='en_US'
;

-- 2019-06-03T15:00:55.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'en_US') 
;

-- 2019-06-03T15:00:56.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zurückweisungsdetaill',Updated=TO_TIMESTAMP('2019-06-03 15:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='de_DE'
;

-- 2019-06-03T15:00:56.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'de_DE') 
;

-- 2019-06-03T15:00:56.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576783,'de_DE') 
;

-- 2019-06-03T15:00:56.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Rejection_Detail_ID', Name='Zurückweisungsdetaill', Description=NULL, Help=NULL WHERE AD_Element_ID=576783
;

-- 2019-06-03T15:00:56.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Rejection_Detail_ID', Name='Zurückweisungsdetaill', Description=NULL, Help=NULL, AD_Element_ID=576783 WHERE UPPER(ColumnName)='C_INVOICE_REJECTION_DETAIL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-03T15:00:56.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Rejection_Detail_ID', Name='Zurückweisungsdetaill', Description=NULL, Help=NULL WHERE AD_Element_ID=576783 AND IsCentrallyMaintained='Y'
;

-- 2019-06-03T15:00:56.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zurückweisungsdetaill', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576783)
;

-- 2019-06-03T15:00:56.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zurückweisungsdetail', Name='Zurückweisungsdetaill' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576783)
;

-- 2019-06-03T15:00:56.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zurückweisungsdetaill', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576783
;

-- 2019-06-03T15:00:56.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zurückweisungsdetaill', Description=NULL, Help=NULL WHERE AD_Element_ID = 576783
;

-- 2019-06-03T15:00:56.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zurückweisungsdetaill', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576783
;

-- 2019-06-03T15:01:01.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zurückweisungsdetaill',Updated=TO_TIMESTAMP('2019-06-03 15:01:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='de_CH'
;

-- 2019-06-03T15:01:01.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'de_CH') 
;

-- 2019-06-03T15:01:03.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoice rejection detail',Updated=TO_TIMESTAMP('2019-06-03 15:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='en_US'
;

-- 2019-06-03T15:01:03.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'en_US') 
;

-- 2019-06-03T15:01:05.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zurückweisungsdetail',Updated=TO_TIMESTAMP('2019-06-03 15:01:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='de_DE'
;

-- 2019-06-03T15:01:05.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'de_DE') 
;

-- 2019-06-03T15:01:05.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576783,'de_DE') 
;

-- 2019-06-03T15:01:05.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_Rejection_Detail_ID', Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=576783
;

-- 2019-06-03T15:01:05.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Rejection_Detail_ID', Name='Zurückweisungsdetail', Description=NULL, Help=NULL, AD_Element_ID=576783 WHERE UPPER(ColumnName)='C_INVOICE_REJECTION_DETAIL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-03T15:01:05.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Rejection_Detail_ID', Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=576783 AND IsCentrallyMaintained='Y'
;

-- 2019-06-03T15:01:05.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576783)
;

-- 2019-06-03T15:01:05.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zurückweisungsdetail', Name='Zurückweisungsdetail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576783)
;

-- 2019-06-03T15:01:05.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zurückweisungsdetail', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576783
;

-- 2019-06-03T15:01:05.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zurückweisungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID = 576783
;

-- 2019-06-03T15:01:05.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zurückweisungsdetail', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576783
;

-- 2019-06-03T15:01:07.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zurückweisungsdetail',Updated=TO_TIMESTAMP('2019-06-03 15:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576783 AND AD_Language='de_CH'
;

-- 2019-06-03T15:01:07.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576783,'de_CH') 
;

