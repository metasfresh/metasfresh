DROP FUNCTION IF EXISTS de_metas_acct.taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                             p_DateFrom  date,
                                                             p_DateTo    date)
;


CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_perVATCode(p_AD_Org_ID numeric(10, 0),
                                                                p_DateFrom  date,
                                                                p_DateTo    date)
    RETURNS TABLE
            (
                Balance     numeric,
                BalanceYear numeric,
                taxName     varchar,
                vatcode     varchar
            )
AS
$BODY$
SELECT SUM(t.balance) AS balance, SUM(t.balanceyear) AS balanceyear, t.taxname, t.vatcode
FROM de_metas_acct.taxaccounts_details(p_AD_Org_ID,
                                       NULL,
                                       NULL,
                                       p_DateFrom,
                                       p_DateTo) AS t
WHERE t.taxname IS NOT NULL
GROUP BY t.vatcode, t.taxname
ORDER BY vatcode
$BODY$
    LANGUAGE sql STABLE
;
