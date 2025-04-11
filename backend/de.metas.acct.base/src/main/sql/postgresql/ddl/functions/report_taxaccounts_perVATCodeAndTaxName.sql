DROP FUNCTION IF EXISTS de_metas_acct.report_taxaccounts_perVATCode(p_AD_Org_ID   numeric(10, 0),
                                                                    p_DateFrom    date,
                                                                    p_DateTo      date,
                                                                    p_ad_language character varying)
;


CREATE OR REPLACE FUNCTION de_metas_acct.report_taxaccounts_perVATCode(p_AD_Org_ID   numeric(10, 0),
                                                                       p_DateFrom    date,
                                                                       p_DateTo      date,
                                                                       p_ad_language character varying DEFAULT 'de_DE'::bpchar)
    RETURNS TABLE
            (
                VatCode             varchar,
                TaxName             varchar,
                TaxAmt_SUM          numeric,
                TaxAmt_SUM_PrevYear numeric,
                Currency            varchar,
                NetAmt_SUM          numeric,
                TotalAmt_SUM            numeric,
                Source_Currency     varchar
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
    WITH vat_codes AS (SELECT v.C_VAT_Code_ID, v.vatcode
                       FROM C_VAT_Code v

                       UNION ALL

                       SELECT NULL::numeric AS C_VAT_Code_ID, NULL::varchar AS vatcode)

    SELECT t.vatcode,
           taxname,
           SUM(taxamt) AS TaxAmt_SUM,
           currency,
           source_currency
    FROM de_metas_acct.tax_accounts_details_v t
             INNER JOIN vat_codes ON vat_codes.vatcode IS NOT DISTINCT FROM t.vatcode
    WHERE ad_org_id = p_ad_org_id
      AND (DateAcct >= v_periodInfo.period_PreviousYearStart_StartDate::date AND DateAcct <= v_periodInfo.period_LastYearEnd_EndDate::date)
    GROUP BY t.vatcode, taxname, currency, source_currency;

    CREATE INDEX ON tmp_taxaccounts_details_previous_year (vatcode, currency, source_currency, taxname);

    -- start computing for current period
    DROP TABLE IF EXISTS tmp_taxaccounts_details;
    CREATE TEMPORARY TABLE tmp_taxaccounts_details AS
    WITH vat_codes AS (SELECT v.C_VAT_Code_ID, v.vatcode
                       FROM C_VAT_Code v

                       UNION ALL

                       SELECT NULL::numeric AS C_VAT_Code_ID, NULL::varchar AS vatcode)

    SELECT COALESCE(t.vatcode, v_notax) AS vatcode,
           taxname,
           SUM(taxamt)                  AS TaxAmt_SUM,
           currency,
           SUM(taxbaseamt)              AS NetAmt_SUM,
           (CASE
                WHEN c_currency_id = source_currency_id THEN SUM(taxbaseamt + taxamt)
                                                        ELSE NULL
            END)                        AS TotalAmt_SUM,
           source_currency
    FROM de_metas_acct.tax_accounts_details_v t
             INNER JOIN vat_codes ON vat_codes.vatcode IS NOT DISTINCT FROM t.vatcode
    WHERE ad_org_id = p_ad_org_id
      AND (DateAcct >= p_datefrom AND DateAcct <= p_dateto)
    GROUP BY t.vatcode, taxname, currency, c_currency_id, source_currency, source_currency_id;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Show taxes per vatcode % tmp_taxaccounts_details', v_rowcount;

    CREATE INDEX ON tmp_taxaccounts_details (vatcode, taxname, currency, source_currency);

    -- assemble the result in one temporray table

    <<RESULT_TABLE>>
    BEGIN
        RETURN QUERY
            SELECT vatcode,
                   TaxName,
                   TaxAmt_SUM,
                   (SELECT COALESCE(SUM(TaxAmt_SUM), 0)
                    FROM tmp_taxaccounts_details_previous_year p
                    WHERE vatcode = t.vatcode
                      AND p.source_currency = t.source_currency
                      AND p.Taxname = t.Taxname) AS TaxAmt_SUM_PrevYear,
                   currency::varchar             AS Currency,
                   NetAmt_SUM,
                   TotalAmt_SUM,
                   source_currency::varchar      AS source_currency
            FROM tmp_taxaccounts_details t;
    END RESULT_TABLE;
END;
$$
;