DROP FUNCTION IF EXISTS report.AccountSheet_Report(
    p_Account_ID      numeric,
    p_C_AcctSchema_ID numeric,
    p_DateAcctFrom    DATE,
    p_DateAcctTo      date
)
;


CREATE OR REPLACE FUNCTION report.AccountSheet_Report(
    p_Account_ID      numeric,
    p_C_AcctSchema_ID numeric,
    p_DateAcctFrom    DATE,
    p_DateAcctTo      date
)
    RETURNS
        TABLE
        (
            konto         text,
            gegenkonto    text,
            soll          numeric,
            haben         numeric,
            soll_währung  numeric,
            haben_währung numeric,
            währung       text,
            Belegdatum    text,
            Buchungsdatum text,
            "Nr"          text
        )
    LANGUAGE sql
    STABLE


AS
$BODY$
SELECT t.AccountValueAndName::text                                                            AS konto,
       t.counterpart_AccountValueAndName::text                                                AS gegenkonto,
       t.amtacctdr                                                                            AS soll,
       t.amtacctcr                                                                            AS haben,
       t.amtsourcedr                                                                          AS soll_währung,
       t.amtsourcecr                                                                          AS haben_währung,
       (SELECT cy.iso_code FROM c_currency cy WHERE cy.c_currency_id = t.c_currency_id)::text AS währung,
       TO_CHAR(t.datetrx, 'DD.MM.YYYY')::text                                                 AS Belegdatum,
       TO_CHAR(t.dateacct, 'DD.MM.YYYY')::text                                                AS Buchungsdatum,
       t.documentno::text                                                                     AS "Nr"
FROM de_metas_acct.RV_AccountSheet t
WHERE TRUE
  AND t.PostingType = 'A'
  AND t.C_AcctSchema_ID = p_C_AcctSchema_ID
  AND t.account_id = p_Account_ID
  AND (p_DateAcctFrom IS NULL OR t.dateacct >= p_DateAcctFrom)
  AND (p_DateAcctTo IS NULL OR t.dateacct <= p_DateAcctTo)
ORDER BY t.dateacct, t.ad_table_id, t.record_id, t.fact_acct_id
    ;
$BODY$
;
