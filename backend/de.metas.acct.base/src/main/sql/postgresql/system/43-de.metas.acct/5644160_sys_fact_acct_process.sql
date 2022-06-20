DROP FUNCTION IF EXISTS de_metas_acct.fact_acct_log_process(
    p_BatchSize integer
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_log_process(
    p_BatchSize integer = 1000
)
    RETURNS integer
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_ProcessingTag       varchar;
    v_rowcount            integer;
    v_amountsToAdd        record;
    v_lastFactAcctSummary record;
BEGIN
    --
    -- Tag what we are going to process
    v_ProcessingTag := gen_random_uuid()::varchar;
    UPDATE fact_acct_log l
    SET processingtag = v_ProcessingTag
    FROM (SELECT fact_acct_log_id
          FROM fact_acct_log
          WHERE processingtag IS NULL
          ORDER BY fact_acct_log_id
          LIMIT p_BatchSize) sel
    WHERE l.fact_acct_log_id = sel.fact_acct_log_id;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    IF (v_rowcount = 0) THEN
        RAISE NOTICE 'fact_acct_log_process: No fact_acct_log to process';
        RETURN 0;
    END IF;
    RAISE NOTICE 'fact_acct_log_process: Tagged % fact_acct_log(s) with `%`', v_rowcount, v_ProcessingTag;

    --
    -- Aggregate amounts from logs and add them to fact_acct_summary
    FOR v_amountsToAdd IN (SELECT l.c_elementvalue_id,
                                  l.c_acctschema_id,
                                  l.postingtype,
                                  l.c_period_id,
                                  p.c_year_id,
                                  l.dateacct,
                                  l.ad_client_id,
                                  l.ad_org_id,
                                  SUM(l.amtacctdr * (CASE WHEN l.action = 'D' THEN -1 ELSE 1 END)) AS amtacctdr,
                                  SUM(l.amtacctcr * (CASE WHEN l.action = 'D' THEN -1 ELSE 1 END)) AS amtacctcr,
                                  SUM(l.qty * (CASE WHEN l.action = 'D' THEN -1 ELSE 1 END))       AS qty
                           FROM fact_acct_log l
                                    INNER JOIN c_period p ON p.c_period_id = l.c_period_id
                           WHERE l.processingtag = v_ProcessingTag
                           GROUP BY l.c_elementvalue_id,
                                    l.c_acctschema_id,
                                    l.postingtype,
                                    l.c_period_id,
                                    p.c_year_id,
                                    l.dateacct,
                                    l.ad_client_id,
                                    l.ad_org_id)
        LOOP
            RAISE DEBUG 'Processing aggregated log: %', v_amountsToAdd;

            --
            -- Get fact_acct_summary closed to our dateacct
            SELECT fas.*
            INTO v_lastFactAcctSummary
            FROM fact_acct_summary fas
            WHERE
              -- Aggregation Group:
                    fas.account_id = v_amountsToAdd.c_elementvalue_id
              AND fas.c_acctschema_id = v_amountsToAdd.c_acctschema_id
              AND fas.postingtype = v_amountsToAdd.postingtype
              -- skip date filtering: c_period_id, c_year_id, dateacct
              AND fas.ad_client_id = v_amountsToAdd.ad_client_id
              AND fas.ad_org_id = v_amountsToAdd.ad_org_id
              AND fas.pa_reportcube_id IS NULL
              --
              AND fas.dateacct <= v_amountsToAdd.dateacct
            ORDER BY fas.dateacct DESC
            LIMIT 1;

            --
            -- Make sure we have an entry in fact_acct_summary for current date
            IF (v_lastFactAcctSummary IS NULL) THEN
                RAISE DEBUG 'Creating a new fact_acct_summary entry because no previous found';
                INSERT INTO fact_acct_summary (account_id,
                                               c_acctschema_id,
                                               postingtype,
                                               ad_client_id,
                                               ad_org_id,
                                               pa_reportcube_id,
                    --
                                               dateacct, c_period_id, c_year_id,
                    --
                                               amtacctdr, amtacctcr, amtacctdr_ytd, amtacctcr_ytd, qty,
                    --
                                               created, createdby, isactive, updated, updatedby)
                SELECT v_amountsToAdd.c_elementvalue_id,
                       v_amountsToAdd.c_acctschema_id,
                       v_amountsToAdd.postingtype,
                       v_amountsToAdd.ad_client_id,
                       v_amountsToAdd.ad_org_id,
                       NULL  AS pa_reportcube_id,
                       --
                       v_amountsToAdd.dateacct,
                       v_amountsToAdd.c_period_id,
                       v_amountsToAdd.c_year_id,
                       --
                       0     AS amtacctdr,
                       0     AS amtacctcr,
                       0     AS amtacctdr_ytd,
                       0     AS amtacctcr_ytd,
                       0     AS qty,
                       --
                       NOW() AS created,
                       0     AS createdby,
                       'Y'   AS isactive,
                       NOW() AS updated,
                       0     AS updatedby;
            ELSIF (v_lastFactAcctSummary.dateacct != v_amountsToAdd.dateacct) THEN
                RAISE DEBUG 'Creating a new fact_acct_summary entry by copying the previous one: %', v_lastFactAcctSummary;
                INSERT INTO fact_acct_summary (account_id,
                                               c_acctschema_id,
                                               postingtype,
                                               ad_client_id,
                                               ad_org_id,
                                               pa_reportcube_id,
                    --
                                               dateacct, c_period_id, c_year_id,
                    --
                                               amtacctdr, amtacctcr, amtacctdr_ytd, amtacctcr_ytd, qty,
                    --
                                               created, createdby, isactive, updated, updatedby)
                SELECT v_lastFactAcctSummary.account_id,
                       v_lastFactAcctSummary.c_acctschema_id,
                       v_lastFactAcctSummary.postingtype,
                       v_lastFactAcctSummary.ad_client_id,
                       v_lastFactAcctSummary.ad_org_id,
                       NULL                                                                                                                       AS pa_reportcube_id,
                       --
                       v_amountsToAdd.dateacct,
                       v_amountsToAdd.c_period_id,
                       v_amountsToAdd.c_year_id,
                       --
                       v_lastFactAcctSummary.amtacctdr                                                                                            AS amtacctdr,
                       v_lastFactAcctSummary.amtacctcr                                                                                            AS amtacctcr,
                       (CASE WHEN v_lastFactAcctSummary.c_year_id = v_amountsToAdd.c_year_id THEN v_lastFactAcctSummary.amtacctdr_ytd ELSE 0 END) AS amtacctdr_ytd,
                       (CASE WHEN v_lastFactAcctSummary.c_year_id = v_amountsToAdd.c_year_id THEN v_lastFactAcctSummary.amtacctcr_ytd ELSE 0 END) AS amtacctcr_ytd,
                       v_lastFactAcctSummary.qty                                                                                                  AS qty,
                       --
                       NOW()                                                                                                                      AS created,
                       0                                                                                                                          AS createdby,
                       'Y'                                                                                                                        AS isactive,
                       NOW()                                                                                                                      AS updated,
                       0                                                                                                                          AS updatedby;
            ELSE
                -- we already have a record for this dateacct => nothing to do
                RAISE DEBUG 'Found an existing fact_acct_summary entry for this dateacct. Do nothing';
            END IF;

            --
            -- Add amounts to current date and all next days.
            UPDATE fact_acct_summary fas
            SET --
                amtacctdr     = fas.amtacctdr + v_amountsToAdd.amtacctdr,
                amtacctcr     = fas.amtacctcr + v_amountsToAdd.amtacctcr,
                qty           = fas.qty + v_amountsToAdd.qty,
                --
                amtacctdr_ytd = fas.amtacctdr_ytd + (CASE WHEN fas.c_year_id = v_amountsToAdd.c_year_id THEN v_amountsToAdd.amtacctdr ELSE 0 END),
                amtacctcr_ytd = fas.amtacctcr_ytd + (CASE WHEN fas.c_year_id = v_amountsToAdd.c_year_id THEN v_amountsToAdd.amtacctcr ELSE 0 END),
                --
                updated       = NOW(),
                updatedby=0
            WHERE
              -- Aggregation Group:
                    fas.account_id = v_amountsToAdd.c_elementvalue_id
              AND fas.c_acctschema_id = v_amountsToAdd.c_acctschema_id
              AND fas.postingtype = v_amountsToAdd.postingtype
              -- skip date filtering: c_period_id, c_year_id, dateacct
              AND fas.ad_client_id = v_amountsToAdd.ad_client_id
              AND fas.ad_org_id = v_amountsToAdd.ad_org_id
              AND fas.pa_reportcube_id IS NULL
              --
              AND fas.dateacct >= v_amountsToAdd.dateacct;
            --
            GET DIAGNOSTICS v_rowcount = ROW_COUNT;
            RAISE DEBUG 'Updated amounts to % rows', v_rowcount;


        END LOOP;

    --
    -- Update ending balances
    PERFORM de_metas_acct.Fact_Acct_EndingBalance_UpdateForTag(v_ProcessingTag);

    --
    -- Delete processed logs
    DELETE FROM fact_acct_log l WHERE processingtag = v_ProcessingTag;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'fact_acct_log_process: Deleted % processed fact_acct_log(s)', v_rowcount;
    RETURN v_rowcount;
END ;
$$
;


