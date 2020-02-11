DROP FUNCTION IF EXISTS tbpReport(numeric, numeric, numeric, numeric, date, date);


CREATE OR REPLACE FUNCTION tbpReport(p_ad_org_id numeric, p_c_acctschema_id NUMERIC, p_account_id NUMERIC, p_c_activity_id numeric, p_dateFrom date, p_dateTo date)
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
                currentBalance   numeric,
                rollingBalance   numeric
            )
AS
$BODY$
DECLARE
    v_BalanceUntilPreviousDay NUMERIC;
BEGIN
    -- save the beginning balance
    SELECT (de_metas_acct.acctBalanceToDate(p_account_id, p_c_acctschema_id, (p_dateFrom - INTERVAL '1 day')::date, p_ad_org_id, 'Y'::char(1), 'Y'::char(1))::de_metas_acct.BalanceAmt).Balance
    INTO v_BalanceUntilPreviousDay;

    RETURN QUERY
        WITH filtered_fact_acct AS
                 (
                     SELECT fa.*,
                            tc.c_taxcategory_id
                     FROM fact_acct fa
                              LEFT JOIN c_tax t ON fa.c_tax_id = t.c_tax_id
                              LEFT JOIN c_taxcategory tc ON t.c_taxcategory_id = tc.c_taxcategory_id
                     WHERE TRUE
-- todo use this filters after talking with mark about which params are mandatory and which are not
--          mandatory filters
--       AND (fa.dateacct >= p_dateFrom AND fa.dateacct <= p_dateTo)
--       AND fa.account_id = p_account_id
--       AND fa.c_acctschema_id = p_c_acctschema_id
--       AND fa.c_activity_id = p_c_activity_id
                 ),
             summed_fa AS
                 (
                     SELECT fa.*,
                            (
                                    v_BalanceUntilPreviousDay
                                    + sum(acctbalance(fa.account_id, fa.amtacctdr, fa.amtacctcr)) OVER (ORDER BY fa.dateacct, fa.fact_acct_id ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
                                )                                                  AS rollingBalance,
                            acctbalance(fa.account_id, fa.amtacctdr, fa.amtacctcr) AS currentBalance
                     FROM filtered_fact_acct fa
                 )
        SELECT fa.dateacct,
               fa.documentno ::text,
               fa.description::text,
               fa.amtacctdr,
               fa.amtacctcr,
               fa.fact_acct_id,
               fa.c_doctype_id,
               fa.c_tax_id,
               fa.c_taxcategory_id,
               fa.currentBalance,
               fa.rollingBalance
        FROM summed_fa fa;

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
       currentBalance,
       rollingBalance
FROM tbpReport(
        1000000,
        1000000,
        540003,
        NULL,
        '2018-04-01'::date,
        '2018-05-31'::date
    );
