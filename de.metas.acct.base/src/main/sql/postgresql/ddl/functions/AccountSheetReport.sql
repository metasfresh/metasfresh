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
                fact_acct_id     numeric,
                c_doctype_id     numeric,
                c_tax_id         numeric,
                c_taxcategory_id numeric,
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
    CREATE TEMPORARY TABLE TMP_AccountSheetReport AS
        (
            SELECT fa.*,
                   tc.c_taxcategory_id,
                   0::numeric beginningBalance,
                   0::numeric endingBalance,
                   NULL::text lineType
            FROM c_elementvalue ev
                     INNER JOIN fact_acct fa ON ev.c_elementvalue_id = fa.account_id -- only search for accounts with at least 1 transaction
                     LEFT JOIN c_tax t ON fa.c_tax_id = t.c_tax_id
                     LEFT JOIN c_taxcategory tc ON t.c_taxcategory_id = tc.c_taxcategory_id
            WHERE FALSE
        );

    v_time := logDebug('created empty temporary table', v_time);


    --
    -- insert into the temp table the beginningBalance for all the available accounts which have the balance != 0
    -- noinspection SqlInsertValues
    WITH filteredElementValues AS
             (
                 SELECT ev.c_elementvalue_id
                 FROM c_elementvalue ev
                 WHERE TRUE
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
    INTO TMP_AccountSheetReport (beginningBalance, endingBalance, lineType, account_id)
    SELECT nonZero.previousDayBalance,
           nonZero.previousDayBalance,
           LINE_TYPE_BEGINNINGBALANCE,
           nonZero.c_elementvalue_id
    FROM nonZeroPreviousBalances nonZero;

    SELECT count(1) FROM TMP_AccountSheetReport INTO v_temp;
    v_time := logDebug('inserted beginningBalance: ' || v_temp || ' records', v_time);


    --
    -- insert the fact_acct rows into the table
    WITH filteredFactAcct AS
             (
                 SELECT fa.*,
                        tc.c_taxcategory_id,
                        tmp_fa.beginningBalance::numeric beginningBalance,
                        tmp_fa.endingBalance::numeric    endingBalance,
                        LINE_TYPE_TRANSACTION
                 FROM fact_acct fa
                          INNER JOIN TMP_AccountSheetReport tmp_fa ON tmp_fa.account_id = fa.account_id --
                          LEFT JOIN c_tax t ON fa.c_tax_id = t.c_tax_id
                          LEFT JOIN c_taxcategory tc ON t.c_taxcategory_id = tc.c_taxcategory_id
                 WHERE TRUE
                   and fa.postingtype = 'A' -- posting type = 'Actual'
                   AND fa.c_acctschema_id = p_c_acctschema_id
                   AND (fa.dateacct >= p_dateFrom AND fa.dateacct <= p_dateTo)
                   AND (p_account_id IS NULL OR fa.account_id = p_account_id)
                   AND (p_c_activity_id IS NULL OR fa.c_activity_id = p_c_activity_id)
                   AND (p_c_project_id IS NULL OR fa.c_project_id = p_c_project_id)
             )
    INSERT
    INTO TMP_AccountSheetReport
    SELECT *
    FROM filteredFactAcct;

    SELECT count(1) FROM TMP_AccountSheetReport INTO v_temp;
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
                            ) AS                                               beginningBalance,
                        acctbalance(tmp_fa.account_id, tmp_fa.amtacctdr, tmp_fa.amtacctcr) transactionBalance
                 FROM TMP_AccountSheetReport tmp_fa
                 WHERE (tmp_fa.dateacct >= p_dateFrom AND tmp_fa.dateacct <= p_dateTo)
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
               t.fact_acct_id,
               t.c_doctype_id,
               t.c_tax_id,
               t.c_taxcategory_id,
               t.beginningBalance,
               t.endingBalance
        FROM TMP_AccountSheetReport t;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE;


/*
Performance

Running on the biggest database we have available, I get these numbers:

- test 1:
    2017-04-01 -> 2018-05-31 = 13 months
    ~2.1 million records

select *
FROM AccountSheetReport(
    '2017-04-01'::date,
    '2018-05-31'::date,
    1000000,
    1000000
)
ORDER BY account_id, dateacct NULLS FIRST, fact_acct_id NULLS FIRST
;

[2020-02-12 16:20:19] [00000] [2020-02-12 14:20:18.842788](0s)   [Δ=0s]: start
[2020-02-12 16:20:19] [00000] [2020-02-12 14:20:18.849605](0s)   [Δ=0s]: created temporary table
[2020-02-12 16:20:22] [00000] [2020-02-12 14:20:21.720887](3s)   [Δ=3s]: inserted beginningBalance
[2020-02-12 16:20:36] [00000] [2020-02-12 14:20:35.628875](17s)  [Δ=14s]: inserted:2176571 fact_acct
[2020-02-12 16:20:38] [00000] [2020-02-12 14:20:37.256058](18s)  [Δ=1s]: deleted rows w/o transactions. Remaining: 2176571
[2020-02-12 16:22:06] [00000] [2020-02-12 14:22:05.357838](106s) [Δ=88s]: finished calculating rolling sum
[2020-02-12 16:22:15] Execution: 106 seconds


- test 2:
    2018-04-01 -> 2018-05-31 = 2 months
    ~300k records

select *
FROM AccountSheetReport(
    '2018-04-01'::date,
    '2018-05-31'::date,
    1000000,
    1000000
)
ORDER BY account_id, dateacct NULLS FIRST, fact_acct_id NULLS FIRST
;

[2020-02-12 16:26:55] [00000] [2020-02-12 14:26:55.109815](0s) [Δ=0s]: start
[2020-02-12 16:26:56] [00000] [2020-02-12 14:26:55.113405](0s) [Δ=0s]: created temporary table
[2020-02-12 16:26:59] [00000] [2020-02-12 14:26:58.7422](4s) [Δ=4s]: inserted beginningBalance
[2020-02-12 16:27:04] [00000] [2020-02-12 14:27:03.366925](8s) [Δ=4s]: inserted:299213 fact_acct
[2020-02-12 16:27:04] [00000] [2020-02-12 14:27:03.554333](9s) [Δ=1s]: deleted rows w/o transactions. Remaining: 299213
[2020-02-12 16:27:15] [00000] [2020-02-12 14:27:13.041447](18s) [Δ=9s]: finished calculating rolling sum
[2020-02-12 16:27:16] Execution: 18 seconds
*/



