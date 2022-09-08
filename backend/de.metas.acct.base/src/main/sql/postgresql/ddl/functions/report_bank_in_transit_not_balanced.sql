DROP FUNCTION IF EXISTS de_metas_acct.report_bank_in_transit_not_balanced
(
    p_EndDate    date,
    p_Account_ID numeric
)
;

DROP FUNCTION IF EXISTS de_metas_acct.report_bank_in_transit_not_balanced
(
    p_EndDate            date,
    p_Account_ID         numeric,
    p_C_BankStatement_ID numeric
)
;


CREATE OR REPLACE FUNCTION de_metas_acct.report_bank_in_transit_not_balanced(
    p_EndDate            date = NULL,
    p_Account_ID         numeric = NULL,
    p_C_BankStatement_ID numeric = NULL
)
    RETURNS table
            (
                accountno       varchar,
                accountname     varchar,
                amtacctdr       numeric,
                amtacctcr       numeric,
                balance         numeric,
                rolling_balance numeric,
                docinfos        text[],
                aggregationKey  text,
                dateacct_first  date
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_Account_IDs numeric[];
    v_rowcount    integer;
BEGIN
    RAISE NOTICE 'Using p_EndDate=%', p_EndDate;
    RAISE NOTICE 'Using p_C_BankStatement_ID=%', p_C_BankStatement_ID;

    IF (p_Account_ID IS NOT NULL AND p_Account_ID > 0) THEN
        v_Account_IDs := ARRAY [p_Account_ID];
    ELSE
        SELECT ARRAY_AGG(DISTINCT vc.account_id)
        INTO v_Account_IDs
        FROM C_BP_BankAccount_Acct bpa
                 INNER JOIN c_validcombination vc ON vc.c_validcombination_id = bpa.b_intransit_acct;
    END IF;
    IF (v_Account_IDs IS NULL OR ARRAY_LENGTH(v_Account_IDs, 1) <= 0) THEN
        RAISE EXCEPTION 'No accounts provided or found';
    END IF;

    RAISE NOTICE 'Consider Bank in Transit accounts: %', v_Account_IDs;

    --
    --
    --
    RAISE NOTICE 'Pre-selecting Fact_Acct records...';
    DROP TABLE IF EXISTS tmp_fact_acct_bankInTransit_prefiltered;
    CREATE TEMPORARY TABLE tmp_fact_acct_bankInTransit_prefiltered
    (
        fact_acct_id numeric,
        tablename    text,
        c_payment_id numeric
    );
    --
    IF (p_C_BankStatement_ID IS NOT NULL) THEN
        INSERT INTO tmp_fact_acct_bankInTransit_prefiltered (fact_acct_id, tablename, c_payment_id)
        SELECT fa.fact_acct_id, 'C_Payment', p.c_payment_id
        FROM c_payment p
                 INNER JOIN fact_acct fa ON fa.ad_table_id = get_table_id('C_Payment') AND fa.record_id = p.c_payment_id
        WHERE p.c_bankstatement_id = p_C_BankStatement_ID
          AND fa.account_id = ANY (v_Account_IDs)
          AND (p_EndDate IS NULL OR fa.dateacct::date <= p_EndDate);
        --
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Pre-selected % Fact_Acct rows (from C_Payment into tmp_fact_acct_bankInTransit_prefiltered)', v_rowcount;

        INSERT INTO tmp_fact_acct_bankInTransit_prefiltered (fact_acct_id, tablename, c_payment_id)
        SELECT fa.fact_acct_id,
               'C_BankStatement',
               (CASE
                    WHEN fa.subline_id IS NOT NULL THEN (SELECT bslr.c_payment_id FROM c_bankstatementline_ref bslr WHERE bslr.c_bankstatementline_ref_id = fa.subline_id)
                    WHEN fa.line_id IS NOT NULL    THEN (SELECT bsl.c_payment_id FROM c_bankstatementline bsl WHERE bsl.c_bankstatementline_id = fa.line_id)
                END) AS c_payment_id
        FROM fact_acct fa
        WHERE fa.ad_table_id = get_table_id('C_BankStatement')
          AND fa.record_id = p_C_BankStatement_ID
          AND fa.account_id = ANY (v_Account_IDs)
          AND (p_EndDate IS NULL OR fa.dateacct::date <= p_EndDate);
        --
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Pre-selected % Fact_Acct rows (from C_BankStatement into tmp_fact_acct_bankInTransit_prefiltered)', v_rowcount;
    ELSE
        INSERT INTO tmp_fact_acct_bankInTransit_prefiltered (fact_acct_id, tablename, c_payment_id)
        SELECT fa.fact_acct_id,
               t.tablename,
               (CASE
                    WHEN t.tablename = 'C_Payment'                                     THEN fa.record_id
                    WHEN t.tablename = 'C_BankStatement' AND fa.subline_id IS NOT NULL THEN (SELECT bslr.c_payment_id FROM c_bankstatementline_ref bslr WHERE bslr.c_bankstatementline_ref_id = fa.subline_id)
                    WHEN t.tablename = 'C_BankStatement' AND fa.line_id IS NOT NULL    THEN (SELECT bsl.c_payment_id FROM c_bankstatementline bsl WHERE bsl.c_bankstatementline_id = fa.line_id)
                END) AS c_payment_id
        FROM fact_acct fa
                 INNER JOIN ad_table t ON t.ad_table_id = fa.ad_table_id
        WHERE --
            fa.account_id = ANY (v_Account_IDs)
          AND (p_EndDate IS NULL OR fa.dateacct::date <= p_EndDate);
        --
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Pre-selected % Fact_Acct rows (into tmp_fact_acct_bankInTransit_prefiltered)', v_rowcount;
    END IF;
    --
    CREATE UNIQUE INDEX ON tmp_fact_acct_bankInTransit_prefiltered (fact_acct_id);

    --
    --
    RAISE NOTICE 'Selecting Fact_Acct records...';
    DROP TABLE IF EXISTS tmp_fact_acct_bankInTransit;

    CREATE TEMPORARY TABLE tmp_fact_acct_bankInTransit AS
    SELECT (CASE
                WHEN p.c_payment_id IS NOT NULL THEN LEAST(p.c_payment_id, p.reversal_id)::text
                                                ELSE (t.tablename || '_' || t.record_id)::text
            END) AS aggregationKey,
           t.*
    FROM (SELECT sel.c_payment_id,
                 sel.tablename,
                 ev.value AS accountno,
                 ev.name  AS accountname,
                 fa.*
          FROM tmp_fact_acct_bankInTransit_prefiltered sel
                   INNER JOIN fact_acct fa ON (fa.fact_acct_id = sel.fact_acct_id)
                   INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
             --
         ) t
             LEFT OUTER JOIN c_payment p ON p.c_payment_id = t.c_payment_id;
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Selected % Fact_Acct rows (into tmp_fact_acct_bankInTransit)', v_rowcount;

    --
    --
    --
    --
    RAISE NOTICE 'Aggregating...';
    DROP TABLE IF EXISTS tmp_fact_acct_bankInTransit_aggregated;

    CREATE TEMPORARY TABLE tmp_fact_acct_bankInTransit_aggregated AS
    SELECT t.accountno,
           t.accountname,
           SUM(t.amtacctdr)               AS amtacctdr,
           SUM(t.amtacctcr)               AS amtacctcr,
           SUM(t.amtacctdr - t.amtacctcr) AS balance,
           ARRAY_AGG(''
                         || t.documentno
                         || '/' || (SELECT rl.name FROM ad_ref_list rl WHERE rl.ad_reference_id = 183 AND rl.value = t.docbasetype)
                         || '/' || (SELECT rl.name FROM ad_ref_list rl WHERE rl.ad_reference_id = 131 AND rl.value = t.docstatus)
                         || '/' || t.dateacct::date
                     ORDER BY t.dateacct, t.fact_acct_id
               )                          AS docinfos,
           t.aggregationKey,
           MIN(t.dateacct::date)          AS dateacct_first,
           MIN(t.fact_acct_id)            AS fact_acct_id_first
    FROM tmp_fact_acct_bankInTransit t
             LEFT OUTER JOIN c_payment p ON p.c_payment_id = t.c_payment_id
    GROUP BY t.c_acctschema_id,
             t.aggregationKey,
             t.accountno,
             t.accountname
    HAVING COALESCE(SUM(t.amtacctdr), 0) != COALESCE(SUM(t.amtacctcr), 0)
    ORDER BY t.accountno, MIN(t.dateacct::date);
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Aggregated to % rows (into tmp_fact_acct_bankInTransit_aggregated)', v_rowcount;


    --
    --
    --
    RETURN QUERY
        SELECT t.accountno,
               t.accountname,
               t.amtacctdr,
               t.amtacctcr,
               t.balance,
               SUM(t.balance) OVER (PARTITION BY t.accountno ORDER BY t.dateacct_first, t.fact_acct_id_first) AS rolling_balance,
               t.docinfos,
               t.aggregationKey,
               t.dateacct_first
        FROM tmp_fact_acct_bankInTransit_aggregated t
        ORDER BY t.accountno, t.dateacct_first, t.fact_acct_id_first;
END;
$$
;
