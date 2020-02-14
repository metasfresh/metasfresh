-- 2020-02-14T10:18:33.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-14 12:18:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574092 AND AD_Language='de_CH'
;

-- 2020-02-14T10:18:33.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574092,'de_CH') 
;

-- 2020-02-14T10:18:39.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-14 12:18:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574092 AND AD_Language='de_DE'
;

-- 2020-02-14T10:18:39.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574092,'de_DE') 
;

-- 2020-02-14T10:18:39.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574092,'de_DE') 
;

-- 2020-02-14T10:18:46.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='TaxRate',Updated=TO_TIMESTAMP('2020-02-14 12:18:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574092
;

-- 2020-02-14T10:18:46.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TaxRate', Name='Steuersatz', Description='Steuern und Steuersätze verwalten', Help='Das Register "Steuersatz" verwaltet die diversen Steuern und Steuersätze zu jeder Steuerkategorie. Z.B. muss eine Verkaufssteuer für jeden Bundesstaat der USA angelegt werden, in dem sie erhoben wird.' WHERE AD_Element_ID=574092
;

-- 2020-02-14T10:18:46.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TaxRate', Name='Steuersatz', Description='Steuern und Steuersätze verwalten', Help='Das Register "Steuersatz" verwaltet die diversen Steuern und Steuersätze zu jeder Steuerkategorie. Z.B. muss eine Verkaufssteuer für jeden Bundesstaat der USA angelegt werden, in dem sie erhoben wird.', AD_Element_ID=574092 WHERE UPPER(ColumnName)='TAXRATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-14T10:18:46.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TaxRate', Name='Steuersatz', Description='Steuern und Steuersätze verwalten', Help='Das Register "Steuersatz" verwaltet die diversen Steuern und Steuersätze zu jeder Steuerkategorie. Z.B. muss eine Verkaufssteuer für jeden Bundesstaat der USA angelegt werden, in dem sie erhoben wird.' WHERE AD_Element_ID=574092 AND IsCentrallyMaintained='Y'
;

-- 2020-02-14T10:21:31.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='TaxCategory',Updated=TO_TIMESTAMP('2020-02-14 12:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574441
;

-- 2020-02-14T10:21:31.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TaxCategory', Name='Steuerkategorie', Description='Steuerkategorien verwalten', Help='Das Fenster "Steuerkategorie" wird verwendet, um Steuerkategorien zu verwalten. Jedes Produkt ist einer Steuerkategorie zugeordnet, was bei Änderungen die Verwaltung der Steuern erleichtert.' WHERE AD_Element_ID=574441
;

-- 2020-02-14T10:21:31.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TaxCategory', Name='Steuerkategorie', Description='Steuerkategorien verwalten', Help='Das Fenster "Steuerkategorie" wird verwendet, um Steuerkategorien zu verwalten. Jedes Produkt ist einer Steuerkategorie zugeordnet, was bei Änderungen die Verwaltung der Steuern erleichtert.', AD_Element_ID=574441 WHERE UPPER(ColumnName)='TAXCATEGORY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-14T10:21:31.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TaxCategory', Name='Steuerkategorie', Description='Steuerkategorien verwalten', Help='Das Fenster "Steuerkategorie" wird verwendet, um Steuerkategorien zu verwalten. Jedes Produkt ist einer Steuerkategorie zugeordnet, was bei Änderungen die Verwaltung der Steuern erleichtert.' WHERE AD_Element_ID=574441 AND IsCentrallyMaintained='Y'
;

-- 2020-02-14T10:21:34.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-14 12:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574441 AND AD_Language='de_CH'
;

-- 2020-02-14T10:21:34.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574441,'de_CH') 
;

-- 2020-02-14T10:21:41.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-14 12:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574441 AND AD_Language='de_DE'
;

-- 2020-02-14T10:21:41.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574441,'de_DE') 
;

-- 2020-02-14T10:21:41.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574441,'de_DE') 
;



DROP FUNCTION IF EXISTS AccountSheetReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC, p_c_activity_id numeric, p_c_project_id numeric);

/*
- DateFrom/To - mandatory
- C_AcctSchema_ID - mandatory
- AD_Org_ID - mandatory
- Account_ID - optional
- C_Activity_ID - optional
- C_Project_ID - optional
*/
CREATE OR REPLACE FUNCTION AccountSheetReport(p_dateFrom date,
                                              p_dateTo date,
                                              p_c_acctschema_id NUMERIC,
                                              p_ad_org_id numeric,
                                              p_account_id NUMERIC=NULL,
                                              p_c_activity_id numeric=NULL,
                                              p_c_project_id numeric=NULL)
    RETURNS table
            (
                dateacct         timestamp WITHOUT TIME ZONE,
                documentno       text,
                description      text,
                amtacctdr        numeric,
                amtacctcr        numeric,
                account_id       numeric,
                AccountName      text,
                AccountValue     text,
                fact_acct_id     numeric,
                docTypeName      text,
                taxRate          text,
                taxCategory      text,
                beginningBalance numeric,
                endingBalance    numeric
            )
AS
$BODY$
DECLARE
    v_time                              timestamp;
    LINE_TYPE_BEGINNINGBALANCE CONSTANT text = 'B';
    LINE_TYPE_TRANSACTION      CONSTANT text = 'T';
    v_temp                              numeric;
BEGIN
    v_time := logDebug('start');

    --
    -- create temporary table for everything we're working on; it has no rows, only the needed columns
    DROP TABLE IF EXISTS TMP_AccountSheetReport;
    CREATE TEMPORARY TABLE TMP_AccountSheetReport
    (
        fact_acct_id     numeric(10),
        documentno       text,
        account_id       numeric(10),
        AccountValue     text,
        AccountName      text,
        dateacct         timestamp,
        amtacctdr        numeric,
        amtacctcr        numeric,
        description      text,
        c_doctype_id     numeric(10),
        c_tax_id         numeric(10),
        c_taxcategory_id numeric(10),
        beginningBalance numeric,
        endingBalance    numeric,
        lineType         text,
        taxName          text,
        taxCategoryName  text,
        docTypeName      text
    );
    v_time := logDebug('created empty temporary table', v_time);


    --
    -- insert into the temp table the beginningBalance for all the available accounts which have the balance != 0
    -- noinspection SqlInsertValues
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
         previousBalances AS
             (
                 SELECT (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateFrom - INTERVAL '1 day')::date, p_ad_org_id)::de_metas_acct.BalanceAmt).Balance previousDayBalance,
                        ev.c_elementvalue_id,
                        ev.AccountName,
                        ev.AccountValue
                 FROM filteredElementValues ev
             ),
         nonZeroPreviousBalances AS
             (
                 SELECT pv.previousDayBalance,
                        pv.c_elementvalue_id,
                        pv.AccountName,
                        pv.AccountValue
                 FROM previousBalances pv
                 WHERE previousDayBalance != 0
             )
    INSERT
    INTO TMP_AccountSheetReport (beginningBalance, endingBalance, lineType, account_id, AccountName, AccountValue)
    SELECT nonZero.previousDayBalance,
           nonZero.previousDayBalance,
           LINE_TYPE_BEGINNINGBALANCE,
           nonZero.c_elementvalue_id,
           nonZero.AccountName,
           nonZero.AccountValue
    FROM nonZeroPreviousBalances nonZero;

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('inserted beginningBalance: ' || v_temp || ' records', v_time);


    --
    -- insert the fact_acct rows into the table
    WITH filteredFactAcct AS
             (
                 SELECT fa.fact_acct_id,
                        fa.documentno,
                        fa.account_id,
                        ev.value                                      AccountValue,
                        ev.name                                       AccountName,
                        fa.dateacct,
                        fa.c_tax_id,
                        t.name                                        taxName,
                        fa.amtacctdr,
                        fa.amtacctcr,
                        fa.description,
                        fa.c_doctype_id,
                        dt.name                                       docTypeName,
                        tc.c_taxcategory_id,
                        tc.name                                       taxCategoryName,
                        coalesce(tmp_fa.beginningBalance::numeric, 0) beginningBalance,
                        coalesce(tmp_fa.endingBalance::numeric, 0)    endingBalance,
                        LINE_TYPE_TRANSACTION                         lineType
                 FROM fact_acct fa
                          INNER JOIN c_elementvalue ev ON fa.account_id = ev.c_elementvalue_id
                          LEFT JOIN TMP_AccountSheetReport tmp_fa ON tmp_fa.account_id = fa.account_id
                          LEFT JOIN c_tax t ON fa.c_tax_id = t.c_tax_id
                          LEFT JOIN c_taxcategory tc ON t.c_taxcategory_id = tc.c_taxcategory_id
                          LEFT JOIN c_doctype dt ON fa.c_doctype_id = dt.c_doctype_id
                 WHERE TRUE
                   AND (fa.amtacctdr != 0 OR fa.amtacctcr != 0)
                   AND fa.postingtype = 'A' -- posting type = 'Actual'
                   AND fa.c_acctschema_id = p_c_acctschema_id
                   AND (fa.dateacct >= p_dateFrom AND fa.dateacct <= p_dateTo)
                   AND (p_account_id IS NULL OR fa.account_id = p_account_id)
                   AND (p_c_activity_id IS NULL OR fa.c_activity_id = p_c_activity_id)
                   AND (p_c_project_id IS NULL OR fa.c_project_id = p_c_project_id)
             )
    INSERT
    INTO TMP_AccountSheetReport(fact_acct_id,
                                documentno,
                                account_id,
                                AccountValue,
                                AccountName,
                                dateacct,
                                amtacctdr,
                                amtacctcr,
                                description,
                                c_doctype_id,
                                c_tax_id,
                                c_taxcategory_id,
                                beginningBalance,
                                endingBalance,
                                lineType,
                                taxName,
                                taxCategoryName,
                                docTypeName)
    SELECT ffa.fact_acct_id,
           ffa.documentno,
           ffa.account_id,
           ffa.AccountValue,
           ffa.AccountName,
           ffa.dateacct,
           ffa.amtacctdr,
           ffa.amtacctcr,
           ffa.description,
           ffa.c_doctype_id,
           ffa.c_tax_id,
           ffa.c_taxcategory_id,
           ffa.beginningBalance,
           ffa.endingBalance,
           ffa.lineType,
           ffa.taxName,
           ffa.taxCategoryName,
           ffa.docTypeName
    FROM filteredFactAcct ffa;

    GET DIAGNOSTICS v_temp = ROW_COUNT;
    v_time := logDebug('inserted:' || v_temp || ' fact_acct', v_time);


    --
    -- Update the current balance for each row.
    -- This implementation uses a rolling sum over the previous rows
    WITH beginningBalance_fa AS
             (
                 SELECT tmp_fa.fact_acct_id,
                        (
                                tmp_fa.endingBalance
                                + sum(acctbalance(tmp_fa.account_id, tmp_fa.amtacctdr, tmp_fa.amtacctcr))
                                  OVER
                                      (
                                      PARTITION BY tmp_fa.account_id
                                      ORDER BY tmp_fa.dateacct, tmp_fa.fact_acct_id ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                                      )
                            ) AS                                                           beginningBalance,
                        acctbalance(tmp_fa.account_id, tmp_fa.amtacctdr, tmp_fa.amtacctcr) transactionBalance
                 FROM TMP_AccountSheetReport tmp_fa
             ),
         final_fa AS
             (
                 SELECT fa.*,
                        fa.beginningBalance - fa.transactionBalance endingBalance
                 FROM beginningBalance_fa fa
             )
    UPDATE TMP_AccountSheetReport tmp_fa
    SET beginningBalance = ffa.endingBalance,
        endingBalance    = ffa.beginningBalance
    FROM final_fa ffa
    WHERE tmp_fa.fact_acct_id = ffa.fact_acct_id;

    v_time := logDebug('finished calculating rolling sum', v_time);


    RETURN QUERY
        SELECT --
               t.dateacct,
               t.documentno::text,
               t.description::text,
               t.amtacctdr,
               t.amtacctcr,
               t.account_id,
               t.AccountName,
               t.AccountValue,
               t.fact_acct_id,
               t.docTypeName,
               t.taxName,
               t.taxCategoryName,
               t.beginningBalance,
               t.endingBalance
        FROM TMP_AccountSheetReport t;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE;

