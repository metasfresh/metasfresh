CREATE FUNCTION getMessage(p_value character varying, p_ad_language  character varying DEFAULT 'de_DE'::character varying) RETURNS character varying
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_msg        varchar;
BEGIN
    SELECT coalesce(mt.msgtext, m.msgtext)
    INTO v_msg
    FROM ad_message m
    LEFT OUTER JOIN AD_Message_trl mt ON m.ad_message_id = mt.ad_message_id and mt.ad_language=p_ad_language
    WHERE m.value=p_value;

    RETURN v_msg;
END;
$$
;


CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_report(p_ad_org_id     numeric,
                                                            p_account_id    numeric,
                                                            p_c_vat_code_id numeric,
                                                            p_datefrom      date,
                                                            p_dateto        date,
                                                            p_isshowdetails character DEFAULT 'N'::bpchar,
                                                            p_ad_language   character varying DEFAULT 'de_DE'::bpchar)
    RETURNS TABLE
            (
                PARAM_AD_Org_ID       varchar,
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
                TaxAmt                numeric
            )
    LANGUAGE plpgsql
AS
$$
    # VARIABLE_CONFLICT USE_COLUMN
DECLARE
    v_rowcount NUMERIC;
    rec        record;
    v_notax    character varying;
BEGIN

    SELECT getmessage('notax', p_ad_language) INTO v_notax;


    DROP TABLE IF EXISTS tmp_taxaccountsonly_details;
    CREATE TEMPORARY TABLE tmp_taxaccountsonly_details AS
    SELECT balance,
           balanceyear,
           taxbaseamt,
           accountno,
           accountname,
           taxname,
           c_tax_id,
           vatcode,
           c_elementvalue_id,
           param_org,
           currency
    FROM de_metas_acct.taxaccountsonly_details(p_ad_org_id := p_ad_org_id,
                                               p_account_id := p_account_id,
                                               p_c_vat_code_id := p_c_vat_code_id,
                                               p_datefrom := p_datefrom,
                                               p_dateto := p_dateto) AS t
    WHERE t.taxname IS NOT NULL
    ORDER BY vatcode, accountno;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Show taxes per vatcode % tmp_taxaccountsonly_details', v_rowcount;
    CREATE INDEX ON tmp_taxaccountsonly_details (vatcode, c_tax_id, c_elementvalue_id);


    -- assemble the result in one temporray table
    DROP TABLE IF EXISTS tmp_final_taxaccounts_report;

    -- insert data for sums per vatcode, currency, param_org
    CREATE TEMPORARY TABLE tmp_final_taxaccounts_report AS
    SELECT (CASE WHEN vatcode IS NULL THEN v_notax ELSE vatcode END)::text AS vatcode,
           NULL::text                                                      AS AccountName,
           NULL::timestamp                                                 AS DateAcct,
           NULL::text                                                      AS DocumentNo,
           NULL::text                                                         BPartnerName,
           NULL::text                                                      AS Taxname,
           NULL::numeric                                                   AS GrandTotal,
           NULL::numeric                                                   AS TotalWithoutVATPerDoc,
           NULL::numeric                                                   AS TaxAmt,
           SUM(taxbaseamt)                                                 AS TotalWithoutVAT,
           SUM(balance)                                                    AS CurrentBalance,
           SUM(balanceyear)                                                AS balance_alltimes,
           currency::varchar                                               AS Currency,
           param_org::varchar                                              AS PARAM_AD_Org_ID
    FROM tmp_taxaccountsonly_details
    GROUP BY vatcode, currency, param_org
    ORDER BY vatcode;

    -- insert data for sums per vatcode, currency, param_org, accountno, accountname
    INSERT INTO tmp_final_taxaccounts_report
    SELECT (CASE WHEN vatcode IS NULL THEN v_notax ELSE vatcode END)::text AS vatcode,
           (accountno || ' ' || accountname)::text                         AS AccountName,
           NULL::timestamp                                                 AS DateAcct,
           NULL::text                                                      AS DocumentNo,
           NULL::text                                                         BPartnerName,
           NULL::text                                                      AS Taxname,
           NULL::numeric                                                   AS GrandTotal,
           NULL::numeric                                                   AS TotalWithoutVATPerDoc,
           NULL::numeric                                                   AS TaxAmt,
           SUM(taxbaseamt)                                                 AS TotalWithoutVAT,
           SUM(balance)                                                    AS CurrentBalance,
           SUM(balanceyear)                                                AS balance_alltimes,
           currency::varchar                                               AS Currency,
           param_org::varchar                                              AS PARAM_AD_Org_ID
    FROM tmp_taxaccountsonly_details
    GROUP BY vatcode, currency, param_org, accountno, accountname
    ORDER BY vatcode, accountno, accountname;


    -- insert data for sums per vatcode, currency, param_org, accountno, accountname, taxname
    INSERT INTO tmp_final_taxaccounts_report
    SELECT (CASE WHEN vatcode IS NULL THEN v_notax ELSE vatcode END)::text AS vatcode,
           (accountno || ' ' || accountname)::text                         AS AccountName,
           NULL::timestamp                                                 AS DateAcct,
           NULL::text                                                      AS DocumentNo,
           NULL::text                                                      AS BPartnerName,
           taxname::text                                                   AS Taxname,
           NULL::numeric                                                   AS GrandTotal,
           NULL::numeric                                                   AS TotalWithoutVATPerDoc,
           NULL::numeric                                                   AS TaxAmt,
           SUM(taxbaseamt)                                                 AS TotalWithoutVAT,
           SUM(balance)                                                    AS CurrentBalance,
           SUM(balanceyear)                                                AS balance_alltimes,
           currency::varchar                                               AS Currency,
           param_org::varchar                                              AS PARAM_AD_Org_ID
    FROM tmp_taxaccountsonly_details
    GROUP BY vatcode, currency, param_org, accountno, accountname, taxname
    ORDER BY vatcode, accountno, accountname, taxname;


    -- add data per documents - level 2

    IF p_isshowdetails = 'Y' THEN

        FOR rec IN SELECT vatcode, c_elementvalue_id, c_tax_id FROM tmp_taxaccountsonly_details ORDER BY vatcode, accountno, taxname
            LOOP

                INSERT INTO tmp_final_taxaccounts_report
                SELECT (CASE WHEN t.vatcode IS NULL THEN v_notax ELSE t.vatcode END)::text AS vatcode,
                       (t.kontono || ' ' || t.kontoname)::text                             AS AccountName,
                       t.dateacct                                                          AS DateAcct,
                       t.documentno                                                        AS DocumentNo,
                       t.bpname                                                            AS BPartnerName,
                       t.taxname                                                           AS Taxname,
                       (t.taxbaseamt + t.taxamt)                                           AS GrandTotal,
                       t.taxbaseamt                                                        AS TotalWithoutVATPerDoc,
                       t.taxamt                                                            AS TaxAmt,
                       NULL::numeric                                                       AS TotalWithoutVAT,
                       NULL::numeric                                                       AS CurrentBalance,
                       NULL::numeric                                                       AS balance_alltimes,
                       currency::varchar                                                   AS Currency,
                       t.param_org::varchar                                                AS PARAM_AD_Org_ID

                FROM report.tax_accounting_report_details(p_datefrom := p_datefrom,
                                                          p_dateto := p_dateto,
                                                          p_vatcode := rec.vatcode,
                                                          p_account_id := rec.c_elementvalue_id,
                                                          p_c_tax_id := rec.c_tax_id,
                                                          p_org_id := p_ad_org_id,
                                                          p_ad_language := p_ad_language) t
                ORDER BY t.vatcode, t.kontono, t.taxname, t.DateAcct;
            END LOOP;


    END IF;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Show taxes per vatcode account and tax: % tmp_final_taxaccounts_report', v_rowcount;

    <<RESULT_TABLE>>
    BEGIN
        RETURN QUERY
            SELECT PARAM_AD_Org_ID,
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
                   TaxAmt
            FROM tmp_final_taxaccounts_report b
            ORDER BY vatcode, AccountName NULLS FIRST, taxname NULLS FIRST, DateAcct NULLS FIRST, DocumentNo, BPartnerName;

    END RESULT_TABLE;
END;
$$
;
