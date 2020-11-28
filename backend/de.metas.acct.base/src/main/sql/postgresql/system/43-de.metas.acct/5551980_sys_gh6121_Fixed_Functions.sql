/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
    LANGUAGE plpgsql VOLATILE
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

    GET DIAGNOSTICS v_temp = ROW_COUNT;
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
                   AND fa.postingtype = 'A' -- posting type = 'Actual'
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

