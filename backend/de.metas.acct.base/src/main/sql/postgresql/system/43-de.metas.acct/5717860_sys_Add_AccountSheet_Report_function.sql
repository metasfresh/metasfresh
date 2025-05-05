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
            AccountNo          text,
            CounterPartAccount text,
            Debit              numeric,
            Credit             numeric,
            Debit_Currency     numeric,
            Credit_Currency    numeric,
            Currency           text,
            DateDoc            text,
            AccountingDate     text,
            DocumentNo         text
        )
    LANGUAGE sql
    STABLE


AS
$BODY$
SELECT t.AccountValueAndName::text                                                            AS DocumentNo,
       t.counterpart_AccountValueAndName::text                                                AS CounterPartAccount,
       t.amtacctdr                                                                            AS Debit,
       t.amtacctcr                                                                            AS Credit,
       t.amtsourcedr                                                                          AS Debit_Currency,
       t.amtsourcecr                                                                          AS Credit_Currency,
       (SELECT cy.iso_code FROM c_currency cy WHERE cy.c_currency_id = t.c_currency_id)::text AS Currency,
       TO_CHAR(t.datetrx, 'DD.MM.YYYY')::text                                                 AS DateDoc,
       TO_CHAR(t.dateacct, 'DD.MM.YYYY')::text                                                AS AccountingDate,
       t.documentno::text                                                                     AS DocumentNo
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
