-- 2020-02-19T06:28:45.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='SummaryAndBalanceReport', SQLStatement='select * from SummaryAndBalanceReport(''@DateFrom@''::date, ''@DateTo@''::date, @C_AcctSchema_ID@, @AD_Org_ID@, @Account_ID/null@)', Value='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-19T06:28:45.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541437
;

-- 2020-02-19T06:29:12.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584655
;

-- 2020-02-19T06:29:15.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:29:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584655
;

-- 2020-02-19T06:29:17.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584655
;

-- 2020-02-19T06:29:19.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584655
;

-- 2020-02-19T06:30:12.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:30:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577545
;

-- 2020-02-19T06:30:12.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SummaryAndBalanceReport', Name='Summen- und Saldenliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577545
;

-- 2020-02-19T06:30:12.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SummaryAndBalanceReport', Name='Summen- und Saldenliste', Description=NULL, Help=NULL, AD_Element_ID=577545 WHERE UPPER(ColumnName)='SUMMARYANDBALANCEREPORT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-19T06:30:12.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SummaryAndBalanceReport', Name='Summen- und Saldenliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577545 AND IsCentrallyMaintained='Y'
;

-- 2020-02-19T06:30:22.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Summary and Balance Report', PrintName='Summary and Balance Report',Updated=TO_TIMESTAMP('2020-02-19 08:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577545 AND AD_Language='en_US'
;

-- 2020-02-19T06:30:22.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577545,'en_US') 
;

-- 2020-02-19T06:30:45.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='SummaryAndBalanceReport',Updated=TO_TIMESTAMP('2020-02-19 08:30:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541437
;

-- 2020-02-19T08:18:40.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577550,0,'HideEmptyLines',TO_TIMESTAMP('2020-02-19 10:18:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hide Empty Lines','Hide Empty Lines',TO_TIMESTAMP('2020-02-19 10:18:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-19T08:18:40.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577550 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-19T08:19:32.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577550,0,584655,541744,20,'HideEmptyLines',TO_TIMESTAMP('2020-02-19 10:19:32','YYYY-MM-DD HH24:MI:SS'),100,'''Y''','D',0,'Y','N','Y','N','N','N','Hide Empty Lines',60,TO_TIMESTAMP('2020-02-19 10:19:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-19T08:19:32.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541744 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-19T08:28:54.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='RemoveEmptyLines', Name='Remove Empty Lines', PrintName='Remove Empty Lines',Updated=TO_TIMESTAMP('2020-02-19 10:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577550
;

-- 2020-02-19T08:28:54.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RemoveEmptyLines', Name='Remove Empty Lines', Description=NULL, Help=NULL WHERE AD_Element_ID=577550
;

-- 2020-02-19T08:28:54.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemoveEmptyLines', Name='Remove Empty Lines', Description=NULL, Help=NULL, AD_Element_ID=577550 WHERE UPPER(ColumnName)='REMOVEEMPTYLINES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-19T08:28:54.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemoveEmptyLines', Name='Remove Empty Lines', Description=NULL, Help=NULL WHERE AD_Element_ID=577550 AND IsCentrallyMaintained='Y'
;

-- 2020-02-19T08:28:54.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Remove Empty Lines', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577550) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577550)
;

-- 2020-02-19T08:28:54.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Remove Empty Lines', Name='Remove Empty Lines' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577550)
;

-- 2020-02-19T08:28:54.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Remove Empty Lines', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577550
;

-- 2020-02-19T08:28:54.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Remove Empty Lines', Description=NULL, Help=NULL WHERE AD_Element_ID = 577550
;

-- 2020-02-19T08:28:54.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Remove Empty Lines', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577550
;

-- 2020-02-19T08:57:48.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ExcludeEmptyLines', Name='Exclude Empty Lines', PrintName='Exclude Empty Lines',Updated=TO_TIMESTAMP('2020-02-19 10:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577550
;

-- 2020-02-19T08:57:48.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExcludeEmptyLines', Name='Exclude Empty Lines', Description=NULL, Help=NULL WHERE AD_Element_ID=577550
;

-- 2020-02-19T08:57:48.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExcludeEmptyLines', Name='Exclude Empty Lines', Description=NULL, Help=NULL, AD_Element_ID=577550 WHERE UPPER(ColumnName)='EXCLUDEEMPTYLINES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-19T08:57:48.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExcludeEmptyLines', Name='Exclude Empty Lines', Description=NULL, Help=NULL WHERE AD_Element_ID=577550 AND IsCentrallyMaintained='Y'
;

-- 2020-02-19T08:57:48.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Exclude Empty Lines', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577550) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577550)
;

-- 2020-02-19T08:57:48.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Exclude Empty Lines', Name='Exclude Empty Lines' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577550)
;

-- 2020-02-19T08:57:48.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Exclude Empty Lines', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577550
;

-- 2020-02-19T08:57:48.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Exclude Empty Lines', Description=NULL, Help=NULL WHERE AD_Element_ID = 577550
;

-- 2020-02-19T08:57:48.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Exclude Empty Lines', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577550
;

-- 2020-02-19T08:58:06.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Leere Zeilen ausschließen', PrintName='Leere Zeilen ausschließen',Updated=TO_TIMESTAMP('2020-02-19 10:58:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577550 AND AD_Language='de_DE'
;

-- 2020-02-19T08:58:06.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577550,'de_DE')
;

-- 2020-02-19T08:58:06.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577550,'de_DE')
;

-- 2020-02-19T08:58:06.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExcludeEmptyLines', Name='Leere Zeilen ausschließen', Description=NULL, Help=NULL WHERE AD_Element_ID=577550
;

-- 2020-02-19T08:58:06.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExcludeEmptyLines', Name='Leere Zeilen ausschließen', Description=NULL, Help=NULL, AD_Element_ID=577550 WHERE UPPER(ColumnName)='EXCLUDEEMPTYLINES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-19T08:58:06.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExcludeEmptyLines', Name='Leere Zeilen ausschließen', Description=NULL, Help=NULL WHERE AD_Element_ID=577550 AND IsCentrallyMaintained='Y'
;

-- 2020-02-19T08:58:06.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Leere Zeilen ausschließen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577550) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577550)
;

-- 2020-02-19T08:58:06.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Leere Zeilen ausschließen', Name='Leere Zeilen ausschließen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577550)
;

-- 2020-02-19T08:58:06.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Leere Zeilen ausschließen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577550
;

-- 2020-02-19T08:58:06.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Leere Zeilen ausschließen', Description=NULL, Help=NULL WHERE AD_Element_ID = 577550
;

-- 2020-02-19T08:58:06.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Leere Zeilen ausschließen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577550
;

-- 2020-02-19T08:58:29.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Leere Zeilen ausschliessen', PrintName='Leere Zeilen ausschliessen',Updated=TO_TIMESTAMP('2020-02-19 10:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577550 AND AD_Language='de_CH'
;

-- 2020-02-19T08:58:29.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577550,'de_CH')
;

-- 2020-02-19T08:58:36.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Exclude Empty Lines', PrintName='Exclude Empty Lines',Updated=TO_TIMESTAMP('2020-02-19 10:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577550 AND AD_Language='en_US'
;

-- 2020-02-19T08:58:36.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577550,'en_US')
;

-- 2020-02-19T08:58:40.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exclude Empty Lines', PrintName='Exclude Empty Lines',Updated=TO_TIMESTAMP('2020-02-19 10:58:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577550 AND AD_Language='nl_NL'
;

-- 2020-02-19T08:58:40.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577550,'nl_NL')
;

-- 2020-02-19T08:59:24.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='Y',Updated=TO_TIMESTAMP('2020-02-19 10:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541744
;

DROP FUNCTION IF EXISTS SummaryAndBalanceReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC, p_c_activity_id NUMERIC);
DROP FUNCTION IF EXISTS SummaryAndBalanceReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC);
DROP FUNCTION IF EXISTS SummaryAndBalanceReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC, p_ExcludeEmptyLines text);



CREATE OR REPLACE FUNCTION SummaryAndBalanceReport(p_dateFrom date,
                                                   p_dateTo date,
                                                   p_c_acctschema_id NUMERIC,
                                                   p_ad_org_id numeric,
                                                   p_account_id NUMERIC=NULL,
                                                   p_ExcludeEmptyLines text = 'Y')
    RETURNS table
            (
                AccountValue     text,
                AccountName      text,
                beginningBalance numeric,
                debit            numeric,
                credit           numeric,
                endingBalance    numeric
            )
AS
$$
WITH filteredElementValues AS
         (
             SELECT ev.c_elementvalue_id,
                    ev.name  AccountName,
                    ev.value AccountValue
             FROM c_elementvalue ev
             WHERE TRUE
               AND (p_account_id IS NULL OR ev.c_elementvalue_id = p_account_id)
             ORDER BY ev.c_elementvalue_id
         ),
     balances AS
         (
             SELECT --
                    (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateFrom - INTERVAL '1 day')::date, p_ad_org_id)::de_metas_acct.BalanceAmt) begining,
                    (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateTo)::date, p_ad_org_id)::de_metas_acct.BalanceAmt)                      ending,
                    ev.c_elementvalue_id,
                    ev.AccountName,
                    ev.AccountValue::text
             FROM filteredElementValues ev
         ),
     data AS
         (
             SELECT --
                    AccountValue,
                    AccountName,
                    (begining).balance                  beginningBalance,
                    (ending).debit - (begining).debit   debit,
                    (ending).credit - (begining).credit credit,
                    (ending).balance                    endingBalance
             FROM balances
         ),
     dataExcludingEmptyLines AS
         (
             SELECT AccountValue,
                    AccountName,
                    beginningBalance,
                    debit,
                    credit,
                    endingBalance
             FROM data
             WHERE (p_ExcludeEmptyLines = 'N' OR (beginningBalance != 0 AND endingBalance != 0))
         )
SELECT AccountValue,
       AccountName,
       beginningBalance,
       debit,
       credit,
       endingBalance
FROM dataExcludingEmptyLines
ORDER BY AccountValue
$$
    LANGUAGE sql STABLE;
