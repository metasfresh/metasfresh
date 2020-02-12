DROP FUNCTION IF EXISTS tbpReport(numeric, numeric, numeric, numeric, date, date);
DROP FUNCTION IF EXISTS tbpReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC, p_c_activity_id numeric, p_c_project_id numeric);

/*
- DateFrom/To - mandatory
- C_AcctSchema_ID - mandatory
- AD_Org_ID - mandatory
- Account_ID - optional
- C_Activity_ID - optional
- C_Project_ID - optional
*/
CREATE OR REPLACE FUNCTION tbpReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC=NULL, p_c_activity_id numeric=NULL, p_c_project_id numeric=NULL)
    RETURNS table
            (
                dateacct         timestamp WITHOUT TIME ZONE,
                documentno       text,
                description      text,
                amtacctdr        numeric,
                amtacctcr        numeric,
                fact_acct_id     numeric,
                c_doctype_id     numeric,
                c_tax_id         numeric,
                c_taxcategory_id numeric,
                beginningBalance numeric,
                previousBalance  numeric
            )
AS
$BODY$
DECLARE
    v_BalanceUntilPreviousDay NUMERIC;
    v_time                    timestamp;
BEGIN
    v_time := clock_timestamp();
    RAISE NOTICE '% start', v_time;

    --
    -- create temporary table for everything we're working on
    DROP TABLE IF EXISTS tbpFilteredFactAcct;
    CREATE TEMPORARY TABLE tbpFilteredFactAcct AS
        (
            SELECT fa.*,
                   tc.c_taxcategory_id,
                   0::numeric previousBalance,
                   0::numeric beginningBalance
            FROM c_elementvalue ev
                     INNER JOIN fact_acct fa ON ev.c_elementvalue_id = fa.account_id -- only search for accounts with at least 1 transaction
                     LEFT JOIN c_tax t ON fa.c_tax_id = t.c_tax_id
                     LEFT JOIN c_taxcategory tc ON t.c_taxcategory_id = tc.c_taxcategory_id
            WHERE TRUE
              AND ev.accounttype = 'A'
              AND fa.c_acctschema_id = p_c_acctschema_id
              -- AND (fa.dateacct >= p_dateFrom AND fa.dateacct <= p_dateTo) -- this shouldn't be here as i want to include fact_acct's which have transactions in the past as well.
              AND (p_account_id IS NULL OR fa.account_id = p_account_id)
              AND (p_c_activity_id IS NULL OR fa.c_activity_id = p_c_activity_id)
              AND (p_c_project_id IS NULL OR fa.c_project_id = p_c_project_id)
        );


    v_time := clock_timestamp();
    RAISE NOTICE '% created temporary table', v_time;

    --
    -- update the temp table with previous balances for all the 'A' ElementValues, which have the balance != 0
    WITH filteredElementValues AS
             (
                 SELECT ev.c_elementvalue_id
                 FROM c_elementvalue ev
                 WHERE ev.accounttype = 'A'
                 ORDER BY ev.c_elementvalue_id
             ),
         previousBalances AS
             (
                 SELECT (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateFrom - INTERVAL '1 day')::date, p_ad_org_id)::de_metas_acct.BalanceAmt).Balance previousDayBalance,
                        ev.c_elementvalue_id
                 FROM filteredElementValues ev
             ),
         nonZeroPreviousBalances AS
             (
                 SELECT *
                 FROM previousBalances
                 WHERE previousDayBalance != 0
             )
    UPDATE tbpFilteredFactAcct tbpffa
    SET beginningBalance = nonZero.previousDayBalance
    FROM nonZeroPreviousBalances nonZero
    WHERE TRUE
      AND tbpffa.account_id = nonZero.c_elementvalue_id
    -- not sure if this is a real optimisation     AND tbpffa.fact_acct_id = (SELECT min(fa.fact_acct_id) FROM fact_acct fa WHERE fa.account_id = tbpffa.account_id);
    ;

    v_time := clock_timestamp();
    RAISE NOTICE '% updated beginningBalance', v_time;

    --
    -- remove all the rows which don't have any transactions
    DELETE FROM tbpFilteredFactAcct t WHERE t.beginningBalance = 0;

    v_time := clock_timestamp();
    RAISE NOTICE '% deleted rows w/o transactions', v_time;

    --
    -- Update the current balance for each row.
    -- This implementation uses a rolling sum over the previous rows
    WITH summed_fa AS
             (
                 SELECT fa.fact_acct_id,
                        (
                                fa.beginningBalance
                                + sum(acctbalance(fa.account_id, fa.amtacctdr, fa.amtacctcr))
                                  OVER
                                      (
                                      PARTITION BY fa.account_id
                                      ORDER BY fa.dateacct, fa.fact_acct_id ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                                      )
                            ) AS rollingBalance
--                         ,
--                         acctbalance(fa.account_id, fa.amtacctdr, fa.amtacctcr) AS currentBalance
                 FROM tbpFilteredFactAcct fa
                 WHERE (fa.dateacct >= p_dateFrom AND fa.dateacct <= p_dateTo)
             )
    UPDATE tbpFilteredFactAcct tbpffa
    SET beginningBalance = summ.rollingBalance
    FROM summed_fa summ
    WHERE tbpffa.fact_acct_id = summ.fact_acct_id;


    v_time := clock_timestamp();
    RAISE NOTICE '% finished calculating rolling sum', v_time;


    RETURN QUERY
        SELECT --
               t.dateacct,
               t.documentno::text,
               t.description::text,
               t.amtacctdr,
               t.amtacctcr,
               t.fact_acct_id,
               t.c_doctype_id,
               t.c_tax_id,
               t.c_taxcategory_id,
               t.beginningBalance,
               t.previousBalance
        FROM tbpFilteredFactAcct t;
END;
$BODY$
    LANGUAGE plpgsql;


--------
SELECT dateacct,
       fact_acct_id,
       documentno,
--        description,
       amtacctdr,
       amtacctcr,
--        c_doctype_id,
--        c_tax_id,
--        c_taxcategory_id,
       beginningBalance,
       previousBalance
-- SELECT count(1)
FROM tbpReport(
        '2018-04-01'::date,
        '2018-05-31'::date,
        1000000,
        1000000
    );
,
        540003,
        NULL,
        NULL
    );


------------------------
WITH previousBalances AS
         (
             SELECT (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, fa.c_acctschema_id, (current_date - INTERVAL '1 day')::date, fa.ad_org_id)::de_metas_acct.BalanceAmt).Balance previousDayBalance,
                    ev.c_elementvalue_id,
                    fa.fact_acct_id
             FROM c_elementvalue ev
                      LEFT JOIN fact_acct fa ON ev.c_elementvalue_id = fa.account_id
         ),
     firstFactAcctPerAccount AS
         (
             SELECT DISTINCT ON (fa.account_id) fa.account_id,
                                                fa.dateacct,
                                                fa.fact_acct_id
             FROM fact_acct fa
             ORDER BY fa.account_id, fa.dateacct, fa.fact_acct_id
         ),
     summed_fa AS
         (
             SELECT fa.*,
                    (
                            prev.previousDayBalance
                            + sum(acctbalance(fa.account_id, fa.amtacctdr, fa.amtacctcr)) OVER (ORDER BY fa.dateacct, fa.fact_acct_id ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
                        )                                                  AS rollingBalance,
                    acctbalance(fa.account_id, fa.amtacctdr, fa.amtacctcr) AS currentBalance
             FROM fact_acct fa
                      INNER JOIN firstFactAcctPerAccount ffa ON fa.fact_acct_id = ffa.fact_acct_id
                      INNER JOIN previousBalances prev ON fa.fact_acct_id = prev.fact_acct_id
         )
SELECT fa.dateacct,
       fa.documentno ::text,
       fa.description::text,
       fa.amtacctdr,
       fa.amtacctcr,
       fa.fact_acct_id,
       fa.c_doctype_id,
       fa.c_tax_id,
       fa.currentBalance,
       fa.rollingBalance
FROM summed_fa fa;



SELECT clock_timestamp(),
       now(),
       pg_sleep(2),
       clock_timestamp(),
       now()
