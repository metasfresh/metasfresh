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
                account_id       numeric,
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
    v_time                              timestamp;
    LINE_TYPE_BEGINNINGBALANCE CONSTANT text = 'B';
    LINE_TYPE_TRANSACTION      CONSTANT text = 'T';
    v_temp                              numeric;
BEGIN
    v_time := logDbg('start');

    --
    -- create temporary table for everything we're working on; it has no rows, only the needed columns
    DROP TABLE IF EXISTS tbpFilteredFactAcct;
    CREATE TEMPORARY TABLE tbpFilteredFactAcct AS
        (
            SELECT fa.*,
                   tc.c_taxcategory_id,
                   0::numeric beginningBalance,
                   0::numeric previousBalance,
                   NULL::text lineType
            FROM c_elementvalue ev
                     INNER JOIN fact_acct fa ON ev.c_elementvalue_id = fa.account_id -- only search for accounts with at least 1 transaction
                     LEFT JOIN c_tax t ON fa.c_tax_id = t.c_tax_id
                     LEFT JOIN c_taxcategory tc ON t.c_taxcategory_id = tc.c_taxcategory_id
            WHERE FALSE
        );
    v_time := logDbg('created temporary table', v_time);


    --
    -- insert into the temp table the beginningBalance for all the available accounts which have the balance != 0
    -- noinspection SqlInsertValues
    WITH filteredElementValues AS
             (
                 SELECT ev.c_elementvalue_id
                 FROM c_elementvalue ev
                 WHERE TRUE
                   AND ev.accounttype = 'A'
                   AND (p_account_id IS NULL OR ev.c_elementvalue_id = p_account_id)
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
    INSERT
    INTO tbpFilteredFactAcct (beginningBalance, previousBalance, lineType, account_id)
    SELECT nonZero.previousDayBalance,
           nonZero.previousDayBalance,
           LINE_TYPE_BEGINNINGBALANCE,
           nonZero.c_elementvalue_id
    FROM nonZeroPreviousBalances nonZero;
    v_time := logDbg('inserted beginningBalance', v_time);


    --
    -- insert the fact_acct rows into the table
    WITH filteredFactAcct AS
             (
                 SELECT fa.*,
                        tc.c_taxcategory_id,
                        tbp.beginningBalance::numeric beginningBalance,
                        tbp.previousBalance::numeric  previousBalance,
                        LINE_TYPE_TRANSACTION
                 FROM fact_acct fa
                          INNER JOIN tbpFilteredFactAcct tbp ON tbp.account_id = fa.account_id --
                          LEFT JOIN c_tax t ON fa.c_tax_id = t.c_tax_id
                          LEFT JOIN c_taxcategory tc ON t.c_taxcategory_id = tc.c_taxcategory_id
                 WHERE TRUE
                   -- AND ev.accounttype = 'A' -- no longer needed here, since we're joining with the already filtered tbpFilteredFactAcct
                   AND fa.c_acctschema_id = p_c_acctschema_id
                   AND (fa.dateacct >= p_dateFrom AND fa.dateacct <= p_dateTo) -- todo can this be here now?(do i want to include fact_acct's which have transactions in the past if i already have  the beginning balance computed already?)
                   AND (p_account_id IS NULL OR fa.account_id = p_account_id)
                   AND (p_c_activity_id IS NULL OR fa.c_activity_id = p_c_activity_id)
                   AND (p_c_project_id IS NULL OR fa.c_project_id = p_c_project_id)
             )
    INSERT
    INTO tbpFilteredFactAcct
    SELECT *
    FROM filteredFactAcct;
    SELECT count(1) FROM tbpFilteredFactAcct INTO v_temp;
    v_time := logDbg('inserted:' || v_temp || ' fact_acct: ', v_time);


    --
    -- remove all the rows which don't have any transactions
    -- todo i believe this can be thrown out now
    DELETE FROM tbpFilteredFactAcct t WHERE t.beginningBalance = 0;
    v_time := logDbg('deleted rows w/o transactions', v_time);

    --
    -- Update the current balance for each row.
    -- This implementation uses a rolling sum over the previous rows
    WITH beginningBalance_fa AS
             (
                 SELECT fa.fact_acct_id,
                        (
                                fa.previousBalance
                                + sum(acctbalance(fa.account_id, fa.amtacctdr, fa.amtacctcr))
                                  OVER
                                      (
                                      PARTITION BY fa.account_id
                                      ORDER BY fa.dateacct, fa.fact_acct_id ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
                                      )
                            ) AS                                               beginningBalance,
                        acctbalance(fa.account_id, fa.amtacctdr, fa.amtacctcr) transactionBalance
                 FROM tbpFilteredFactAcct fa
                 WHERE (fa.dateacct >= p_dateFrom AND fa.dateacct <= p_dateTo)
             ),
         final_fa AS
             (
                 SELECT fa.*,
                        fa.beginningBalance - fa.transactionBalance previousBalance
                 FROM beginningBalance_fa fa
             )
    UPDATE tbpFilteredFactAcct tbpffa
    SET beginningBalance = ffa.previousBalance,
        previousBalance  = ffa.beginningBalance
    FROM final_fa ffa
    WHERE tbpffa.fact_acct_id = ffa.fact_acct_id;

    v_time := logDbg('finished calculating rolling sum', v_time);

    RETURN QUERY
        SELECT --
               t.dateacct,
               t.documentno::text,
               t.description::text,
               t.amtacctdr,
               t.amtacctcr,
               t.account_id,
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
SELECT --
       account_id,
       dateacct,
--        fact_acct_id,
--        documentno,
--        description,
       amtacctdr,
       amtacctcr,
--        c_doctype_id,
--        c_tax_id,
--        c_taxcategory_id,
       beginningBalance,
       previousBalance
FROM tbpReport(
        '2018-04-01'::date,
        '2018-05-31'::date,
        1000000,
        1000000
    )
--      ,
--         540003,
--         NULL,
--         NULL
--     )
WHERE TRUE
  AND account_id NOT IN (540003)
ORDER BY account_id, dateacct NULLS FIRST, fact_acct_id NULLS FIRST
;


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
;




