/**
  p_level meaning:
  * Level '1' : data summed per vatcode; only sum amount are filled up
  * Level '2' : data summed per vatcode, accounto; only sum amount are filled up
  * Level '3' : data summed per vatcode, accounto, taxname; only sum amount are filled up
  * Level '4' : detailed data; only data per document are filled up
  * Level 'ReCap' : data summed per vatcode and taxname; only sum amount are filled up
  * null value implies level '4'
 */

CREATE OR REPLACE FUNCTION de_metas_acct.report_taxaccounts(p_ad_org_id     numeric,
                                                            p_account_id    numeric,
                                                            p_c_vat_code_id numeric,
                                                            p_datefrom      date,
                                                            p_dateto        date,
                                                            p_isshowdetails character DEFAULT 'N'::bpchar,
                                                            p_level         character DEFAULT NULL::bpchar,
                                                            p_ad_language   character varying DEFAULT 'de_DE'::bpchar)
    RETURNS TABLE
            (
                Level               varchar,
                vatcode             varchar,
                AccountName         text,
                Taxname             text,
                NetAmt_SUM          numeric,
                TaxAmt_SUM          numeric,
                TaxAmt_SUM_PrevYear numeric,
                Currency            varchar,
                DateAcct            timestamp,
                DocumentNo          text,
                BPartnerName        text,
                TotalAmt            numeric,
                NetAmt              numeric,
                source_currency     varchar,
                TaxAmt              numeric,
                param_startdate     date,
                param_enddate       date,
                param_konto         varchar,
                param_vatcode       varchar,
                param_org           varchar
            )
    LANGUAGE plpgsql
AS
$$
    # VARIABLE_CONFLICT USE_COLUMN
DECLARE
    v_rowcount       NUMERIC;
    v_notax          character varying;
    v_AcctSchemaInfo record;
    v_periodInfo     record;
BEGIN

    SELECT getmessage('notax', p_ad_language) INTO v_notax;

    -- Get Accounting Schema Info
    SELECT acs.c_acctschema_id,
           acs.c_currency_id,
           acs.ad_client_id,
           ci.c_calendar_id,
           cr.iso_code
    INTO v_AcctSchemaInfo
    FROM AD_Client c
             INNER JOIN AD_ClientInfo ci ON ci.AD_Client_ID = c.ad_client_id
             INNER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
             INNER JOIN C_Currency cr ON acs.C_Currency_ID = cr.C_Currency_ID AND cr.isActive = 'Y'
    WHERE c.AD_Client_ID = 1000000;

    -- get period info
    SELECT period_LastYearEnd.EndDate::date         AS period_LastYearEnd_EndDate,
           period_CurrentYearStart.StartDate::date  AS period_CurrentYearStart_StartDate,
           period_PreviousYearStart.StartDate::date AS period_PreviousYearStart_StartDate,
           p.EndDate::date                          AS EndDate,
           p.StartDate::date                        AS StartDate
    INTO v_periodInfo
    FROM C_Period p
             -- Get last period of previous year
             LEFT OUTER JOIN C_Period period_LastYearEnd ON (period_LastYearEnd.C_Period_ID = report.Get_Predecessor_Period_Recursive(p.C_Period_ID, p.PeriodNo::int)) AND period_LastYearEnd.isActive = 'Y'
             LEFT OUTER JOIN C_Period period_CurrentYearStart ON (period_CurrentYearStart.C_Period_ID = report.Get_Predecessor_Period_Recursive(p.C_Period_ID, (p.PeriodNo - 1)::int)) AND period_CurrentYearStart.isActive = 'Y'
             LEFT OUTER JOIN C_Period period_PreviousYearStart ON (period_PreviousYearStart.C_Period_ID = report.Get_Predecessor_Period_Recursive(period_LastYearEnd.C_Period_ID, period_LastYearEnd.PeriodNo::int-1)) AND period_PreviousYearStart.isActive = 'Y'
    WHERE TRUE
      -- Period: determine it by DateAcct
      AND p.C_Period_ID = report.Get_Period(v_AcctSchemaInfo.C_Calendar_ID, p_datefrom);


    DROP TABLE IF EXISTS tmp_taxaccounts_details_previous_year;
    CREATE TEMPORARY TABLE tmp_taxaccounts_details_previous_year AS
    WITH vat_codes AS (SELECT DISTINCT v.vatcode
                       FROM C_VAT_Code v
                       WHERE p_c_vat_code_id IS NULL
                          OR v.C_VAT_Code_ID = p_c_vat_code_id

                       UNION ALL

                       SELECT  NULL::varchar AS vatcode
                       WHERE p_c_vat_code_id IS NULL -- include NULL vatcode if no filter is applied
    )

    SELECT COALESCE(t.vatcode, v_notax) as vatcode,
           (accountno || ' ' || accountname)::text AS AccountName,
           taxname,
           SUM(taxamt)                             AS taxamt,
           currency,
           SUM(taxbaseamt)                         AS taxbaseamt,
           source_currency,
           (CASE
                WHEN p_AD_Org_ID IS NULL
                    THEN NULL
                    ELSE (SELECT o.NAME
                          FROM ad_org o
                          WHERE o.ad_org_id = p_AD_Org_ID)
            END)::varchar                          AS param_org
    FROM de_metas_acct.tax_accounts_details_v t
             INNER JOIN vat_codes ON vat_codes.vatcode IS NOT DISTINCT FROM t.vatcode
    WHERE ad_org_id = p_ad_org_id
      AND (p_account_id IS NULL OR p_account_id = account_id)
      AND (DateAcct >= v_periodInfo.period_PreviousYearStart_StartDate::date AND DateAcct <= v_periodInfo.period_LastYearEnd_EndDate::date)
    GROUP BY t.vatcode, accountno, accountname, taxname, currency, source_currency;

    CREATE INDEX ON tmp_taxaccounts_details_previous_year (vatcode, currency, source_currency, param_org);
    CREATE INDEX ON tmp_taxaccounts_details_previous_year (vatcode, currency, source_currency, param_org, accountname, taxname);

    -- start computing for current period

    DROP TABLE IF EXISTS tmp_taxaccounts_details;
    CREATE TEMPORARY TABLE tmp_taxaccounts_details AS
    WITH vat_codes AS (SELECT DISTINCT v.vatcode
                       FROM C_VAT_Code v
                       WHERE p_c_vat_code_id IS NULL
                          OR v.C_VAT_Code_ID = p_c_vat_code_id

                       UNION ALL

                       SELECT NULL::varchar AS vatcode
                       WHERE p_c_vat_code_id IS NULL -- include NULL vatcode if no filter is applied
    )

    SELECT COALESCE(t.vatcode, v_notax)            AS vatcode,
           (accountno || ' ' || accountname)::text AS AccountName,
           dateacct,
           documentno,
           taxname,
           taxrate,
           bpName,
           taxamt,
           currency,
           taxbaseamt,
           (CASE
                WHEN c_currency_id = source_currency_id THEN (taxbaseamt + taxamt)
                                                        ELSE NULL
            END)                                   AS TotalAmt,
           source_currency,
           c_tax_id,
           source_currency_id,
           c_currency_id,
           account_id                              AS c_elementvalue_id,
           p_DateFrom                              AS param_startdate,
           p_DateTo                                AS param_enddate,
           (CASE
                WHEN p_Account_ID IS NULL
                    THEN NULL
                    ELSE (SELECT ev.VALUE || ' - ' || ev.NAME
                          FROM C_ElementValue ev
                          WHERE ev.C_ElementValue_ID = p_Account_ID)
            END)::varchar                          AS param_konto,
           (CASE
                WHEN p_C_Vat_Code_ID IS NULL
                    THEN NULL
                    ELSE (SELECT vc.vatcode
                          FROM C_Vat_Code vc
                          WHERE vc.C_Vat_Code_ID = p_C_Vat_Code_ID)
            END) ::varchar                         AS param_vatcode,
           (CASE
                WHEN p_AD_Org_ID IS NULL
                    THEN NULL
                    ELSE (SELECT o.NAME
                          FROM ad_org o
                          WHERE o.ad_org_id = p_AD_Org_ID)
            END)::varchar                          AS param_org
    FROM de_metas_acct.tax_accounts_details_v t
             INNER JOIN vat_codes ON vat_codes.vatcode IS NOT DISTINCT FROM t.vatcode
    WHERE ad_org_id = p_ad_org_id
      AND (p_account_id IS NULL OR p_account_id = account_id)
      AND (DateAcct >= p_datefrom AND DateAcct <= p_dateto);

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Show all taxes  % tmp_taxaccounts_details', v_rowcount;

    CREATE INDEX ON tmp_taxaccounts_details (vatcode, currency, source_currency, param_org);
    CREATE INDEX ON tmp_taxaccounts_details (vatcode, currency, source_currency, param_org, accountname, taxname);


    -- assemble the result in one temporray table
    DROP TABLE IF EXISTS tmp_final_taxaccounts_report;

    CREATE TEMP TABLE tmp_final_taxaccounts_report
    (
        Level               varchar,
        vatcode             varchar,
        AccountName         text,
        DateAcct            timestamp,
        DocumentNo          text,
        BPartnerName        text,
        Taxname             text,
        TotalAmt            numeric,
        NetAmt              numeric,
        TaxAmt              numeric,
        NetAmt_SUM          numeric,
        source_currency     varchar,
        TaxAmt_SUM          numeric,
        TaxAmt_SUM_PrevYear numeric,
        Currency            varchar,
        param_startdate     date,
        param_enddate       date,
        param_konto         varchar,
        param_vatcode       varchar,
        param_org           varchar
    );

    -- insert data for sums per vatcode, currency, source_currency, param_org
    -- Level 1
    IF p_level IS NULL OR p_level = '1' THEN
        INSERT INTO tmp_final_taxaccounts_report
        SELECT '1'::varchar             AS level,
               vatcode,
               NULL::text               AS AccountName,
               NULL::timestamp          AS DateAcct,
               NULL::text               AS DocumentNo,
               NULL::text               AS BPartnerName,
               NULL::text               AS Taxname,
               NULL::numeric            AS TotalAmt,
               NULL::numeric            AS NetAmt,
               NULL::numeric            AS TaxAmt,
               SUM(taxbaseamt)          AS NetAmt_SUM,
               source_currency::varchar AS source_currency,
               SUM(taxamt)              AS TaxAmt_SUM,
               0::numeric               AS TaxAmt_SUM_PrevYear,
               currency::varchar        AS Currency,
               param_startdate::date,
               param_enddate::date,
               param_konto ::varchar,
               param_vatcode::varchar,
               param_org ::varchar
        FROM tmp_taxaccounts_details
        GROUP BY vatcode, currency, source_currency, param_org, param_startdate, param_enddate, param_konto, param_vatcode;
    END IF;

    -- insert data for sums per vatcode, currency, param_org, accountno, accountname
    -- Level 2
    IF p_level IS NULL OR p_level = '2' THEN
        INSERT INTO tmp_final_taxaccounts_report
        SELECT '2'::varchar             AS level,
               vatcode,
               AccountName,
               NULL::timestamp          AS DateAcct,
               NULL::text               AS DocumentNo,
               NULL::text               AS BPartnerName,
               NULL::text               AS Taxname,
               NULL::numeric            AS TotalAmt,
               NULL::numeric            AS NetAmt,
               NULL::numeric            AS TaxAmt,
               SUM(taxbaseamt)          AS NetAmt_SUM,
               source_currency::varchar AS source_currency,
               SUM(taxamt)              AS TaxAmt_SUM,
               0::numeric               AS TaxAmt_SUM_PrevYear,
               currency::varchar        AS Currency,
               param_startdate::date,
               param_enddate::date,
               param_konto ::varchar,
               param_vatcode::varchar,
               param_org ::varchar
        FROM tmp_taxaccounts_details
        GROUP BY vatcode, currency, source_currency, accountname, param_org, param_startdate, param_enddate, param_konto, param_vatcode;
    END IF;

    -- insert data for sums per vatcode, currency, source_currency, param_org, accountno, accountname, taxname
    -- Level 3
    IF p_level IS NULL OR p_level = '3' THEN
        INSERT INTO tmp_final_taxaccounts_report
        SELECT '3' ::varchar            AS level,
               vatcode,
               AccountName,
               NULL::timestamp          AS DateAcct,
               NULL::text               AS DocumentNo,
               NULL::text               AS BPartnerName,
               taxname::text            AS Taxname,
               NULL::numeric            AS TotalAmt,
               NULL::numeric            AS TaxAmt,
               NULL::numeric            AS TaxAmt,
               SUM(taxbaseamt)          AS NetAmt_SUM,
               source_currency::varchar AS source_currency,
               SUM(taxamt)              AS TaxAmt_SUM,
               0::numeric               AS TaxAmt_SUM_PrevYear,
               currency::varchar        AS Currency,
               param_startdate::date,
               param_enddate::date,
               param_konto ::varchar,
               param_vatcode::varchar,
               param_org ::varchar
        FROM tmp_taxaccounts_details
        GROUP BY vatcode, currency, source_currency, accountname, taxname, param_org, param_startdate, param_enddate, param_konto, param_vatcode;
    END IF;

    -- add data per documents - level 4
    IF (p_level IS NULL OR p_level = '4') AND p_isshowdetails = 'Y' THEN
        INSERT INTO tmp_final_taxaccounts_report
        SELECT '4' ::varchar            AS level,
               vatcode,
               AccountName,
               t.dateacct               AS DateAcct,
               t.documentno             AS DocumentNo,
               t.bpname                 AS BPartnerName,
               t.taxname                AS Taxname,
               t.TotalAmt               AS TotalAmt,
               t.taxbaseamt             AS NetAmt,
               t.taxamt                 AS TaxAmt,
               NULL::numeric            AS NetAmt_SUM,
               source_currency::varchar AS source_currency,
               NULL::numeric            AS TaxAmt_SUM,
               NULL::numeric            AS TaxAmt_SUM_PrevYear,
               currency::varchar        AS Currency,
               param_startdate::date,
               param_enddate::date,
               param_konto ::varchar,
               param_vatcode::varchar,
               param_org ::varchar
        FROM tmp_taxaccounts_details t;
    END IF;

    IF p_level = 'ReCap' THEN
        INSERT INTO tmp_final_taxaccounts_report
        SELECT 'ReCap'::varchar         AS level,
               vatcode,
               NULL::text               AS AccountName,
               NULL::timestamp          AS DateAcct,
               NULL::text               AS DocumentNo,
               NULL::text               AS BPartnerName,
               TaxName                  AS Taxname,
               NULL::numeric            AS TotalAmt,
               NULL::numeric            AS NetAmt,
               NULL::numeric            AS TaxAmt,
               SUM(taxbaseamt)          AS NetAmt_SUM,
               source_currency::varchar AS source_currency,
               SUM(taxamt)              AS TaxAmt_SUM,
               0::numeric               AS TaxAmt_SUM_PrevYear,
               currency::varchar        AS Currency,
               param_startdate::date,
               param_enddate::date,
               param_konto ::varchar,
               param_vatcode::varchar,
               param_org ::varchar
        FROM tmp_taxaccounts_details
        GROUP BY vatcode, taxname, currency, source_currency, param_org, param_startdate, param_enddate, param_konto, param_vatcode;
    END IF;

    --- update balance for the previous year
    ---data for sums per vatcode, currency, source_currency, param_org
    IF p_level IS NULL OR p_level = '1' THEN
        UPDATE tmp_final_taxaccounts_report t
        SET TaxAmt_SUM_PrevYear = (SELECT COALESCE(SUM(taxamt), 0)
                                   FROM tmp_taxaccounts_details_previous_year p
                                   WHERE COALESCE(p.vatcode, v_notax)  = t.vatcode
                                     AND p.source_currency = t.source_currency)
        WHERE t.Level = '1';
    END IF;

    ---data for sums per vatcode, currency, param_org, accountno, accountname
    IF p_level IS NULL OR p_level = '2' THEN
        UPDATE tmp_final_taxaccounts_report t
        SET TaxAmt_SUM_PrevYear = (SELECT COALESCE(SUM(taxamt), 0)
                                   FROM tmp_taxaccounts_details_previous_year p
                                   WHERE COALESCE(p.vatcode, v_notax)  = t.vatcode
                                     AND p.source_currency = t.source_currency
                                     AND p.AccountName = t.AccountName)
        WHERE t.Level = '2';
    END IF;

    ---data for sums per vatcode, currency, source_currency, param_org, accountno, accountname, taxname
    IF p_level IS NULL OR p_level = '3' THEN
        UPDATE tmp_final_taxaccounts_report t
        SET TaxAmt_SUM_PrevYear = (SELECT COALESCE(SUM(taxamt), 0)
                                   FROM tmp_taxaccounts_details_previous_year p
                                   WHERE COALESCE(p.vatcode, v_notax)  = t.vatcode
                                     AND p.source_currency = t.source_currency
                                     AND p.AccountName = t.AccountName
                                     AND p.Taxname = t.Taxname)
        WHERE t.Level = '3';
    END IF;

    --- update balance for the previous year
    ---data for sums per vatcode, currency, source_currency, param_org
    IF p_level = 'ReCap' THEN
        UPDATE tmp_final_taxaccounts_report t
        SET TaxAmt_SUM_PrevYear = (SELECT COALESCE(SUM(taxamt), 0)
                                   FROM tmp_taxaccounts_details_previous_year p
                                   WHERE COALESCE(p.vatcode, v_notax)   = t.vatcode
                                     AND p.Taxname = t.Taxname
                                     AND p.source_currency = t.source_currency)
        WHERE t.Level = 'ReCap';
    END IF;


    <<RESULT_TABLE>>
    BEGIN
        RETURN QUERY
            SELECT Level,
                   vatcode,
                   AccountName,
                   Taxname,
                   NetAmt_SUM,
                   TaxAmt_SUM,
                   TaxAmt_SUM_PrevYear,
                   Currency,
                   DateAcct,
                   DocumentNo,
                   BPartnerName,
                   TotalAmt,
                   NetAmt,
                   source_currency,
                   TaxAmt,
                   param_startdate,
                   param_enddate,
                   param_konto,
                   param_vatcode,
                   param_org
            FROM tmp_final_taxaccounts_report b
            ORDER BY vatcode, AccountName NULLS FIRST, taxname NULLS FIRST, DateAcct NULLS FIRST, DocumentNo, BPartnerName;

    END RESULT_TABLE;
END;
$$
;


/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
-- DROP VIEW IF EXISTS de_metas_acct.tax_accounts_details_v
-- ;

CREATE OR REPLACE VIEW de_metas_acct.tax_accounts_details_v
AS

WITH tax_accounts AS (SELECT DISTINCT vc.Account_ID AS C_ElementValue_ID
                      FROM C_Tax_Acct ta
                               INNER JOIN C_ValidCombination vc
                                          ON vc.C_ValidCombination_ID IN (ta.T_Due_Acct, ta.T_Credit_Acct)
                      UNION

                        SELECT C_ElementValue_ID from C_ElementValue
                        WHERE AccountConceptualName IN ('T_Due_Acct', 'T_Credit_Acct')

                      )
SELECT fa.vatcode,
       fa.accountno,
       fa.accountname,
       fa.dateacct,
       fa.documentno,
       fa.taxname,
       fa.taxrate,
       fa.bpName,
       fa.taxamt,
       fa.currency,
       (CASE
            WHEN DocBaseType IN ('APC', 'ARI') THEN -TaxBaseAmt
            WHEN DocBaseType IN ('ARC', 'API') THEN  TaxBaseAmt
                                               ELSE SIGN(TaxAmt) * ABS(TaxBaseAmt) -- we need the absolut value in order to be able to enforce tax sign
        END) AS TaxBaseAmt,
       fa.source_currency,
       fa.C_Tax_ID,
       fa.account_id,
       fa.postingtype,
       fa.accountconceptualname,
       fa.c_currency_id,
       fa.source_currency_id,
       fa.ad_table_id,
       fa.record_id,
       fa.ad_org_id
FROM (SELECT fa.vatcode                    AS vatcode,
             ev.value                      AS accountno,
             ev.name                       AS accountname,
             fa.dateacct,
             fa.documentno,

             tax.name                      AS taxname,
             tax.rate                      AS taxrate,

             bp.name                       AS bpName,

             (fa.AmtAcctDr - fa.AmtAcctCr) AS taxamt,
             (CASE
                  WHEN fa.ad_table_id = get_Table_Id('C_Invoice')       THEN inv_tax.taxbaseamt
                  WHEN fa.ad_table_id = get_Table_Id('C_AllocationHdr') THEN
                      (CASE
                           WHEN al.c_allocationline_id IS NULL THEN NULL
                           WHEN tax.IsWholeTax = 'Y'           THEN 0
                           WHEN tax.Rate = 0                   THEN 0
                                                               ELSE ROUND((fa.AmtAcctDr - fa.AmtAcctCr) * 100 / tax.Rate, 2)
                       END)
                  WHEN fa.ad_table_id = get_Table_Id('GL_Journal')      THEN
                      (CASE
                           WHEN gll.dr_autotaxaccount = 'Y'
                               THEN gll.dr_taxbaseamt
                           WHEN cr_autotaxaccount = 'Y'
                               THEN gll.cr_taxbaseamt::numeric
                       END)
                  WHEN fa.ad_table_id = get_Table_Id('SAP_GLJournal')   THEN
                      (SELECT amtacct
                       FROM SAP_GLJournalLine gll
                       WHERE gll.sap_gljournalline_id = sap_gll.parent_id
                         AND sap_gll.c_tax_id = gll.c_tax_id)::numeric
              END)                         AS TaxBaseAmt,
             c.iso_code                    AS source_currency,
             cr.iso_code                   AS currency,
             fa.DocBaseType,
             fa.C_Tax_ID,
             fa.account_id,
             fa.postingtype,
             fa.accountconceptualname,
             fa.c_currency_id              AS source_currency_id,
             cr.c_currency_id,
             fa.ad_table_id,
             fa.record_id,
             fa.ad_org_id,
             fa.ad_client_id
      FROM fact_acct fa
               INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
               INNER JOIN c_tax tax ON fa.c_tax_id = tax.c_tax_id
               INNER JOIN C_Currency c ON fa.c_Currency_ID = c.C_Currency_ID
               LEFT OUTER JOIN c_bpartner bp ON fa.c_bpartner_id = bp.c_bpartner_id
               LEFT OUTER JOIN C_InvoiceTax inv_tax ON (fa.record_id = inv_tax.c_invoice_id AND fa.ad_table_id = get_Table_Id('C_Invoice') AND inv_tax.c_tax_id = fa.c_tax_id)
               LEFT OUTER JOIN GL_JournalLine gll ON (fa.record_id = gll.gl_journal_id AND fa.ad_table_id = get_Table_Id('GL_Journal') AND gll.gl_journalline_id = fa.line_id AND COALESCE(gll.dr_tax_id, gll.cr_tax_id) = fa.c_tax_id)
               LEFT OUTER JOIN SAP_GLJournalLine sap_gll ON (fa.record_id = sap_gll.SAP_GLJournal_ID AND fa.ad_table_id = get_Table_Id('SAP_GLJournal') AND
                                                             sap_gll.SAP_GLJournalLine_ID = fa.line_id AND sap_gll.C_tax_id = fa.c_tax_id)
               LEFT OUTER JOIN c_allocationline al ON (al.c_allocationline_id = fa.line_id AND al.c_allocationhdr_id = fa.record_id AND fa.ad_table_id = get_Table_Id('C_AllocationHdr'))
               INNER JOIN AD_ClientInfo ci ON ci.AD_Client_ID = fa.ad_client_id
               INNER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
               INNER JOIN C_Currency cr ON acs.C_Currency_ID = cr.C_Currency_ID) AS fa
    WHERE (exists (select 1 from tax_accounts where tax_accounts.C_ElementValue_ID = fa.account_id) )
   OR (fa.ad_table_id IN (get_Table_Id('C_Invoice'), get_Table_Id('C_AllocationHdr')) AND fa.accountconceptualname IN ('T_Due_Acct', 'T_Credit_Acct'))
;



