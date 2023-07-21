DROP FUNCTION IF EXISTS de_metas_acct.taxaccounts_details(p_AD_Org_ID     numeric(10, 0),
                                                          p_Account_ID    numeric,
                                                          p_C_Vat_Code_ID numeric,
                                                          p_DateFrom      date,
                                                          p_DateTo        date)
;

CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_details(p_AD_Org_ID     numeric(10, 0), -- not null
                                                             p_Account_ID    numeric, -- not null
                                                             p_C_Vat_Code_ID numeric,
                                                             p_DateFrom      date, -- not null
                                                             p_DateTo        date) -- not null
    RETURNS TABLE
            (
                Balance           numeric,
                BalanceYear       numeric,
                accountNo         varchar,
                accountName       varchar,
                taxName           varchar,
                C_Tax_ID          numeric,
                vatcode           varchar,
                C_ElementValue_ID numeric,
                param_startdate   date,
                param_enddate     date,
                param_konto       varchar,
                param_vatcode     varchar,
                param_org         varchar
            )
AS
$BODY$
DECLARE
    v_rowcount numeric;
BEGIN
    --
    -- Compute Balance until date_from with details
    DROP TABLE IF EXISTS tmp_dateto_balances;
    CREATE TEMPORARY TABLE tmp_dateto_balances AS
    SELECT p_Account_ID        AS C_ElementValue_ID,
           b.vatcode,
           b.c_tax_id,
           b.accountno,
           b.accountname,
           (b.Balance).Balance AS balance
    FROM de_metas_acct.balanceToDate(p_AD_Org_ID, p_Account_ID, p_DateTo, p_C_Vat_Code_ID) AS b;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Computed date_to balances for % account', p_Account_ID;
    CREATE UNIQUE INDEX ON tmp_dateto_balances (C_ElementValue_ID, c_tax_id, vatcode);

    --
    -- Compute Balance until date_from with details
    DROP TABLE IF EXISTS tmp_datefrom_balances;
    CREATE TEMPORARY TABLE tmp_datefrom_balances AS
    SELECT p_Account_ID        AS C_ElementValue_ID,
           b.vatcode,
           b.c_tax_id,
           b.accountno,
           b.accountname,
           (b.Balance).Balance AS balance
    FROM de_metas_acct.balanceToDate(p_AD_Org_ID, p_Account_ID, (p_DateFrom - INTERVAL '1 day')::date, p_C_Vat_Code_ID) AS b;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Computed date_from balances for % account', p_Account_ID;
    CREATE UNIQUE INDEX ON tmp_datefrom_balances (C_ElementValue_ID, c_tax_id, vatcode);


    --
    -- Compute Balance until yearBegining with details
    DROP TABLE IF EXISTS tmp_yearBegining_balances;
    CREATE TEMPORARY TABLE tmp_yearBegining_balances AS
    SELECT p_Account_ID        AS C_ElementValue_ID,
           b.vatcode,
           b.c_tax_id,
           b.accountno,
           b.accountname,
           (b.Balance).Balance AS balance
    FROM de_metas_acct.balanceToDate(p_AD_Org_ID, p_Account_ID, (DATE_TRUNC('year', p_DateTo::DATE) - INTERVAL '1 day')::DATE, p_C_Vat_Code_ID) AS b;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Compute begining year balances for % account', p_Account_ID;
    CREATE UNIQUE INDEX ON tmp_yearBegining_balances (C_ElementValue_ID, c_tax_id, vatcode);

    <<RESULT_TABLE>>
    BEGIN
        RETURN QUERY
            WITH balance AS
                     (
                         SELECT (COALESCE(b2.Balance, 0) - COALESCE(b1.Balance, 0)) AS Balance,
                                (COALESCE(b2.Balance, 0) - COALESCE(BY.Balance, 0)) AS YearBalance,
                                b2.accountno,
                                b2.accountname,
                                b2.C_Tax_ID,
                                b2.vatcode,
                                b2.C_ElementValue_ID
                         FROM tmp_dateto_balances b2
                                  LEFT JOIN tmp_datefrom_balances b1 ON b1.c_elementvalue_id = b2.c_elementvalue_id
                             AND b1.accountno = b2.accountno
                             AND b1.accountname = b2.accountname
                             AND b1.vatcode IS NOT DISTINCT FROM b2.vatcode
                             AND b1.c_tax_id IS NOT DISTINCT FROM b2.c_tax_id

                                  LEFT JOIN tmp_yearBegining_balances BY ON BY.c_elementvalue_id = b2.c_elementvalue_id
                             AND BY.accountno = b2.accountno
                             AND BY.accountname = b2.accountname
                             AND BY.vatcode IS NOT DISTINCT FROM b2.vatcode
                             AND BY.c_tax_id IS NOT DISTINCT FROM b2.c_tax_id
                     )
            SELECT b.Balance,
                   b.YearBalance,
                   b.accountno,
                   b.accountname,
                   t.Name          AS taxName,
                   b.C_Tax_ID,
                   b.vatcode,
                   b.C_ElementValue_ID,
                   p_DateFrom      AS param_startdate,
                   p_DateTo        AS param_enddate,
                   (CASE
                        WHEN p_Account_ID IS NULL
                            THEN NULL
                            ELSE (SELECT ev.VALUE || ' - ' || ev.NAME
                                  FROM C_ElementValue ev
                                  WHERE ev.C_ElementValue_ID = p_Account_ID
                            )
                    END)::varchar  AS param_konto,
                   (CASE
                        WHEN p_C_Vat_Code_ID IS NULL
                            THEN NULL
                            ELSE (SELECT vc.vatcode
                                  FROM C_Vat_Code vc
                                  WHERE vc.C_Vat_Code_ID = p_C_Vat_Code_ID
                            )
                    END) ::varchar AS param_vatcode,
                   (CASE
                        WHEN p_AD_Org_ID IS NULL
                            THEN NULL
                            ELSE (SELECT o.NAME
                                  FROM ad_org o
                                  WHERE o.ad_org_id = p_AD_Org_ID
                            )
                    END)::varchar  AS param_org

            FROM balance AS b
                     LEFT OUTER JOIN C_Tax t ON t.C_tax_ID = b.C_tax_ID
            ORDER BY b.vatcode, b.accountno;
    END RESULT_TABLE;

END;
$BODY$
    LANGUAGE plpgsql VOLATILE
;
