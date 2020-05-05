DROP FUNCTION IF EXISTS logDebug(msg text, previousTime timestamp);

CREATE OR REPLACE FUNCTION logDebug(msg text,
                                    previousTime timestamp=now())
    RETURNS timestamp AS
$$
DECLARE
    v_now             timestamp;
    deltaSinceLastLog text;
    totalRuntime      text;
BEGIN
    v_now := clock_timestamp();
    deltaSinceLastLog := extract(EPOCH FROM v_now::timestamp(0) - previousTime::timestamp(0));
    totalRuntime := extract(EPOCH FROM v_now::timestamp(0) - now()::timestamp(0));

    -- todo: not sure if raise notice already prints the current timestamp and v_now is a duplicate
    RAISE NOTICE '[%](%s) [Δ=%s]: % ', v_now, totalRuntime, deltaSinceLastLog, msg;

    RETURN v_now;
END;
$$
    LANGUAGE plpgsql IMMUTABLE
                     COST 1;

COMMENT ON FUNCTION logDebug(msg text, previousTime timestamp) IS '
How to use:
Pass the message you want to appear in the console, and also the timestamp returned by the function, so that you get the time difference (Δ in seconds) from the previous output.

DECLARE
v_debugTime timestamp;

v_debugTime := logDebug(''START'');
[...]
v_debugTime := logDebug(''created TEMPORARY TABLE'', v_debugTime);
[...]
v_debugTime := logDebug(''inserted beginningBalance'', v_debugTime);
[...]
';



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
    DROP TABLE IF EXISTS tbpFilteredFactAcct;
    CREATE TEMPORARY TABLE tbpFilteredFactAcct AS
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
    INTO tbpFilteredFactAcct (beginningBalance, endingBalance, lineType, account_id)
    SELECT nonZero.previousDayBalance,
           nonZero.previousDayBalance,
           LINE_TYPE_BEGINNINGBALANCE,
           nonZero.c_elementvalue_id
    FROM nonZeroPreviousBalances nonZero;

    SELECT count(1) FROM tbpFilteredFactAcct INTO v_temp;
    v_time := logDebug('inserted beginningBalance: ' || v_temp || ' records', v_time);


    --
    -- insert the fact_acct rows into the table
    WITH filteredFactAcct AS
             (
                 SELECT fa.*,
                        tc.c_taxcategory_id,
                        tbp.beginningBalance::numeric beginningBalance,
                        tbp.endingBalance::numeric    endingBalance,
                        LINE_TYPE_TRANSACTION
                 FROM fact_acct fa
                          INNER JOIN tbpFilteredFactAcct tbp ON tbp.account_id = fa.account_id --
                          LEFT JOIN c_tax t ON fa.c_tax_id = t.c_tax_id
                          LEFT JOIN c_taxcategory tc ON t.c_taxcategory_id = tc.c_taxcategory_id
                 WHERE TRUE
                   -- AND ev.accounttype = 'A' -- no longer needed here, since we're joining with the already filtered tbpFilteredFactAcct
                   AND fa.c_acctschema_id = p_c_acctschema_id
                   AND (fa.dateacct >= p_dateFrom AND fa.dateacct <= p_dateTo)
                   AND (p_account_id IS NULL OR fa.account_id = p_account_id)
                   AND (p_c_activity_id IS NULL OR fa.c_activity_id = p_c_activity_id)
                   AND (p_c_project_id IS NULL OR fa.c_project_id = p_c_project_id)
             )
    INSERT
    INTO tbpFilteredFactAcct
    SELECT *
    FROM filteredFactAcct;

    SELECT count(1) FROM tbpFilteredFactAcct INTO v_temp;
    v_time := logDebug('inserted:' || v_temp || ' fact_acct', v_time);


    --
    -- Update the current balance for each row.
    -- This implementation uses a rolling sum over the previous rows
    WITH beginningBalance_fa AS
             (
                 SELECT fa.fact_acct_id,
                        (
                                fa.endingBalance
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
                        fa.beginningBalance - fa.transactionBalance endingBalance
                 FROM beginningBalance_fa fa
             )
    UPDATE tbpFilteredFactAcct tbpffa
    SET beginningBalance = ffa.endingBalance,
        endingBalance    = ffa.beginningBalance
    FROM final_fa ffa
    WHERE tbpffa.fact_acct_id = ffa.fact_acct_id;

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
        FROM tbpFilteredFactAcct t;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE;
