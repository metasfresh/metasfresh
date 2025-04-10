CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_report(p_ad_org_id     numeric,
                                                            p_account_id    numeric,
                                                            p_c_vat_code_id numeric,
                                                            p_datefrom      date,
                                                            p_dateto        date,
                                                            p_isshowdetails character DEFAULT 'N'::bpchar,
                                                            p_ad_language   character varying DEFAULT 'de_DE'::bpchar)
    RETURNS TABLE
            (
                Level                 varchar,
                vatcode               text,
                AccountName           text,
                Taxname               text,
                TotalWithoutVAT       numeric,
                CurrentBalance        numeric,
                balance_one_year_ago  numeric,
                Currency              varchar,
                DateAcct              timestamp,
                DocumentNo            text,
                BPartnerName          text,
                GrandTotal            numeric,
                TotalWithoutVATPerDoc numeric,
                source_currency       varchar,
                TaxAmt                numeric,
                param_startdate       date,
                param_enddate         date,
                param_konto           varchar,
                param_vatcode         varchar,
                param_org             varchar
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
             LEFT OUTER JOIN C_Period period_PreviousYearStart ON (period_PreviousYearStart.C_Period_ID = report.Get_Predecessor_Period_Recursive(period_LastYearEnd.C_Period_ID, period_LastYearEnd.PeriodNo::int)) AND period_PreviousYearStart.isActive = 'Y'
    WHERE TRUE
      -- Period: determine it by DateAcct
      AND p.C_Period_ID = report.Get_Period(v_AcctSchemaInfo.C_Calendar_ID, p_datefrom);


    DROP TABLE IF EXISTS tmp_taxaccounts_details_previous_year;
    CREATE TEMPORARY TABLE tmp_taxaccounts_details_previous_year AS
    WITH vcodes AS (SELECT v.C_VAT_Code_ID, v.vatcode
                    FROM C_VAT_Code v
                    WHERE p_c_vat_code_id IS NULL
                       OR v.C_VAT_Code_ID = p_c_vat_code_id

                    UNION ALL

                    SELECT NULL::numeric AS C_VAT_Code_ID, NULL::varchar AS vatcode
                    WHERE p_c_vat_code_id IS NULL -- include NULL vatcode if no filter is applied
    )

    SELECT t.vatcode,
           accountno,
           accountname,
           dateacct,
           documentno,
           taxname,
           taxrate,
           bpName,
           taxamt,
           currency,
           baseamt        AS taxbaseamt,
           source_currency,
           c_tax_id,
           account_id     AS c_elementvalue_id,
           (CASE
                WHEN p_AD_Org_ID IS NULL
                    THEN NULL
                    ELSE (SELECT o.NAME
                          FROM ad_org o
                          WHERE o.ad_org_id = p_AD_Org_ID)
            END)::varchar AS param_org
    FROM de_metas_acct.tax_accounts_details_v t
             JOIN vcodes v ON v.VatCode = t.vatcode
    WHERE ad_org_id = p_ad_org_id
      AND (p_account_id IS NULL OR p_account_id = account_id)
      AND (DateAcct >= v_periodInfo.period_PreviousYearStart_StartDate::date AND DateAcct <= v_periodInfo.period_LastYearEnd_EndDate::date)
    ORDER BY vatcode, accountno;


    -- start computing for current period

    DROP TABLE IF EXISTS tmp_taxaccounts_details;
    CREATE TEMPORARY TABLE tmp_taxaccounts_details AS
    WITH vcodes AS (SELECT v.C_VAT_Code_ID, v.vatcode
                    FROM C_VAT_Code v
                    WHERE p_c_vat_code_id IS NULL
                       OR v.C_VAT_Code_ID = p_c_vat_code_id

                    UNION ALL

                    SELECT NULL::numeric AS C_VAT_Code_ID, NULL::varchar AS vatcode
                    WHERE p_c_vat_code_id IS NULL -- include NULL vatcode if no filter is applied
    )

    SELECT t.vatcode,
           accountno,
           accountname,
           dateacct,
           documentno,
           taxname,
           taxrate,
           bpName,
           taxamt,
           currency,
           baseamt        AS taxbaseamt,
           source_currency,
           c_tax_id,
           source_currency_id,
           c_currency_id,
           account_id     AS c_elementvalue_id,
           (CASE
                WHEN p_AD_Org_ID IS NULL
                    THEN NULL
                    ELSE (SELECT o.NAME
                          FROM ad_org o
                          WHERE o.ad_org_id = p_AD_Org_ID)
            END)::varchar AS param_org
    FROM de_metas_acct.tax_accounts_details_v t
             JOIN vcodes v ON v.VatCode = t.vatcode
    WHERE ad_org_id = p_ad_org_id
      AND (p_account_id IS NULL OR p_account_id = account_id)
      AND (DateAcct >= p_datefrom AND DateAcct <= p_dateto)
    ORDER BY vatcode, accountno;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Show taxes per vatcode % tmp_taxaccounts_details', v_rowcount;

    CREATE INDEX ON tmp_taxaccounts_details (vatcode, currency, source_currency, param_org);
    CREATE INDEX ON tmp_taxaccounts_details (vatcode, currency, source_currency, param_org, accountno, accountname, taxname);


    -- assemble the result in one temporray table
    DROP TABLE IF EXISTS tmp_final_taxaccounts_report;

    -- insert data for sums per vatcode, currency, source_currency, param_org
    -- Level 1
    CREATE TEMPORARY TABLE tmp_final_taxaccounts_report AS
    SELECT '1'::varchar                                                    AS level,
           (CASE WHEN vatcode IS NULL THEN v_notax ELSE vatcode END)::text AS vatcode,
           NULL::text                                                      AS AccountName,
           NULL::timestamp                                                 AS DateAcct,
           NULL::text                                                      AS DocumentNo,
           NULL::text                                                         BPartnerName,
           NULL::text                                                      AS Taxname,
           NULL::numeric                                                   AS GrandTotal,
           NULL::numeric                                                   AS TotalWithoutVATPerDoc,
           NULL::numeric                                                   AS TaxAmt,
           SUM(taxbaseamt)                                                 AS TotalWithoutVAT,
           source_currency::varchar                                        AS source_currency,
           SUM(taxamt)                                                     AS CurrentBalance,
           0::numeric                                                      AS balance_alltimes,
           currency::varchar                                               AS Currency,
           param_org::varchar                                              AS PARAM_AD_Org_ID
    FROM tmp_taxaccounts_details
    GROUP BY vatcode, currency, source_currency, param_org
    ORDER BY vatcode;

    -- insert data for sums per vatcode, currency, param_org, accountno, accountname
    -- Level 2
    INSERT INTO tmp_final_taxaccounts_report
    SELECT '2'::varchar                                                    AS level,
           (CASE WHEN vatcode IS NULL THEN v_notax ELSE vatcode END)::text AS vatcode,
           (accountno || ' ' || accountname)::text                         AS AccountName,
           NULL::timestamp                                                 AS DateAcct,
           NULL::text                                                      AS DocumentNo,
           NULL::text                                                         BPartnerName,
           NULL::text                                                      AS Taxname,
           NULL::numeric                                                   AS GrandTotal,
           NULL::numeric                                                   AS TotalWithoutVATPerDoc,
           NULL::numeric                                                   AS TaxAmt,
           SUM(taxbaseamt)                                                 AS TotalWithoutVAT,
           source_currency::varchar                                        AS source_currency,
           SUM(taxamt)                                                     AS CurrentBalance,
           0::numeric                                                      AS balance_alltimes,
           currency::varchar                                               AS Currency,
           param_org::varchar                                              AS PARAM_AD_Org_ID
    FROM tmp_taxaccounts_details
    GROUP BY vatcode, currency, source_currency, param_org, accountno, accountname
    ORDER BY vatcode, accountno, accountname;


    -- insert data for sums per vatcode, currency, source_currency, param_org, accountno, accountname, taxname
    -- Level 3
    INSERT INTO tmp_final_taxaccounts_report
    SELECT '3' ::varchar                                                   AS level,
           (CASE WHEN vatcode IS NULL THEN v_notax ELSE vatcode END)::text AS vatcode,
           (accountno || ' ' || accountname)::text                         AS AccountName,
           NULL::timestamp                                                 AS DateAcct,
           NULL::text                                                      AS DocumentNo,
           NULL::text                                                      AS BPartnerName,
           taxname::text                                                   AS Taxname,
           NULL::numeric                                                   AS GrandTotal,
           NULL::numeric                                                   AS TotalWithoutVATPerDoc,
           NULL::numeric                                                   AS TaxAmt,
           SUM(taxbaseamt)                                                 AS TotalWithoutVAT,
           source_currency::varchar                                        AS source_currency,
           SUM(taxamt)                                                     AS CurrentBalance,
           0::numeric                                                      AS balance_alltimes,
           currency::varchar                                               AS Currency,
           param_org::varchar                                              AS PARAM_AD_Org_ID
    FROM tmp_taxaccounts_details
    GROUP BY vatcode, currency, source_currency, param_org, accountno, accountname, taxname
    ORDER BY vatcode, accountno, accountname, taxname;


    --- update balance for the previous year
    ---data for sums per vatcode, currency, source_currency, param_org
    UPDATE tmp_final_taxaccounts_report
    SET balance_alltimes = COALESCE(y.CurrentBalance, 0)
    FROM (SELECT '1' ::varchar                                                   AS level,
                 (CASE WHEN vatcode IS NULL THEN v_notax ELSE vatcode END)::text AS vatcode,
                 NULL::text                                                      AS AccountName,
                 NULL::timestamp                                                 AS DateAcct,
                 NULL::text                                                      AS DocumentNo,
                 NULL::text                                                         BPartnerName,
                 NULL::text                                                      AS Taxname,
                 SUM(taxamt)                                                     AS CurrentBalance,
                 currency                                                        AS Currency,
                 source_currency,
                 param_org::varchar                                              AS PARAM_AD_Org_ID
          FROM tmp_taxaccounts_details_previous_year
          GROUP BY vatcode, currency, source_currency, param_org
          ORDER BY vatcode) y
    WHERE tmp_final_taxaccounts_report.vatcode = y.vatcode
      AND tmp_final_taxaccounts_report.source_currency = y.source_currency
      AND tmp_final_taxaccounts_report.level = '1'
      AND y.level = '1';

    ---data for sums per vatcode, currency, param_org, accountno, accountname
    UPDATE tmp_final_taxaccounts_report
    SET balance_alltimes = COALESCE(y.CurrentBalance, 0)
    FROM (SELECT '2' ::varchar                                                   AS level,
                 (CASE WHEN vatcode IS NULL THEN v_notax ELSE vatcode END)::text AS vatcode,
                 NULL::text                                                      AS AccountName,
                 NULL::timestamp                                                 AS DateAcct,
                 NULL::text                                                      AS DocumentNo,
                 NULL::text                                                         BPartnerName,
                 NULL::text                                                      AS Taxname,
                 SUM(taxamt)                                                     AS CurrentBalance,
                 currency                                                        AS Currency,
                 source_currency,
                 param_org::varchar                                              AS PARAM_AD_Org_ID
          FROM tmp_taxaccounts_details_previous_year
          GROUP BY vatcode, currency, source_currency, param_org, accountno, accountname
          ORDER BY vatcode) y
    WHERE tmp_final_taxaccounts_report.vatcode = y.vatcode
      AND tmp_final_taxaccounts_report.AccountName = y.AccountName
      AND tmp_final_taxaccounts_report.source_currency = y.source_currency
      AND tmp_final_taxaccounts_report.level = '2'
      AND y.level = '2';


    ---data for sums per vatcode, currency, source_currency, param_org, accountno, accountname, taxname
    UPDATE tmp_final_taxaccounts_report
    SET balance_alltimes = COALESCE(y.CurrentBalance, 0)
    FROM (SELECT '3' ::varchar                                                   AS level,
                 (CASE WHEN vatcode IS NULL THEN v_notax ELSE vatcode END)::text AS vatcode,
                 NULL::text                                                      AS AccountName,
                 NULL::timestamp                                                 AS DateAcct,
                 NULL::text                                                      AS DocumentNo,
                 NULL::text                                                         BPartnerName,
                 NULL::text                                                      AS Taxname,
                 SUM(taxamt)                                                     AS CurrentBalance,
                 currency::varchar                                               AS Currency,
                 source_currency,
                 param_org::varchar                                              AS PARAM_AD_Org_ID
          FROM tmp_taxaccounts_details_previous_year
          GROUP BY vatcode, currency, source_currency, param_org, accountno, accountname, taxname
          ORDER BY vatcode) y
    WHERE tmp_final_taxaccounts_report.vatcode = y.vatcode
      AND tmp_final_taxaccounts_report.AccountName = y.AccountName
      AND tmp_final_taxaccounts_report.Taxname = y.Taxname
      AND tmp_final_taxaccounts_report.source_currency = y.source_currency
      AND tmp_final_taxaccounts_report.level = '3'
      AND y.level = '3';

    -- add data per documents - level 4
    IF p_isshowdetails = 'Y' THEN

        INSERT INTO tmp_final_taxaccounts_report
        SELECT '4' ::varchar                                                       AS level,
               (CASE WHEN t.vatcode IS NULL THEN v_notax ELSE t.vatcode END)::text AS vatcode,
               (t.accountno || ' ' || t.accountname)::text                         AS AccountName,
               t.dateacct                                                          AS DateAcct,
               t.documentno                                                        AS DocumentNo,
               t.bpname                                                            AS BPartnerName,
               t.taxname                                                           AS Taxname,
               (CASE
                    WHEN c_currency_id = source_currency_id THEN (taxbaseamt + taxamt)
                                                            ELSE NULL
                END)                                                               AS GrandTotal,
               t.taxbaseamt                                                        AS TotalWithoutVATPerDoc,
               t.taxamt                                                            AS TaxAmt,
               NULL::numeric                                                       AS TotalWithoutVAT,
               source_currency::varchar                                            AS source_currency,
               NULL::numeric                                                       AS CurrentBalance,
               NULL::numeric                                                       AS balance_alltimes,
               currency::varchar                                                   AS Currency,
               t.param_org::varchar                                                AS PARAM_AD_Org_ID

        FROM tmp_taxaccounts_details t
        ORDER BY t.vatcode, t.accountno, t.taxname, t.DateAcct;

    END IF;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Show taxes per vatcode account and tax: % tmp_final_taxaccounts_report', v_rowcount;

    <<RESULT_TABLE>>
    BEGIN
        RETURN QUERY
            SELECT Level,
                   vatcode,
                   AccountName,
                   Taxname,
                   TotalWithoutVAT,
                   CurrentBalance,
                   balance_alltimes,
                   Currency,
                   DateAcct,
                   DocumentNo,
                   BPartnerName,
                   GrandTotal,
                   TotalWithoutVATPerDoc,
                   source_currency,
                   TaxAmt,
                   p_DateFrom      AS param_startdate,
                   p_DateTo        AS param_enddate,
                   (CASE
                        WHEN p_Account_ID IS NULL
                            THEN NULL
                            ELSE (SELECT ev.VALUE || ' - ' || ev.NAME
                                  FROM C_ElementValue ev
                                  WHERE ev.C_ElementValue_ID = p_Account_ID)
                    END)::varchar  AS param_konto,
                   (CASE
                        WHEN p_C_Vat_Code_ID IS NULL
                            THEN NULL
                            ELSE (SELECT vc.vatcode
                                  FROM C_Vat_Code vc
                                  WHERE vc.C_Vat_Code_ID = p_C_Vat_Code_ID)
                    END) ::varchar AS param_vatcode,
                   (CASE
                        WHEN p_AD_Org_ID IS NULL
                            THEN NULL
                            ELSE (SELECT o.NAME
                                  FROM ad_org o
                                  WHERE o.ad_org_id = p_AD_Org_ID)
                    END)::varchar  AS param_org
            FROM tmp_final_taxaccounts_report b
            ORDER BY vatcode, AccountName NULLS FIRST, taxname NULLS FIRST, DateAcct NULLS FIRST, DocumentNo, BPartnerName;

    END RESULT_TABLE;
END;
$$
;
