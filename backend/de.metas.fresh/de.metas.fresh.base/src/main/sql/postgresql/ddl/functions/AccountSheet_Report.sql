DROP FUNCTION IF EXISTS de_metas_acct.AccountSheet_Report(
    p_Account_ID      numeric,
    p_C_AcctSchema_ID numeric,
    p_DateAcctFrom    DATE,
    p_DateAcctTo      date
)
;

DROP FUNCTION IF EXISTS de_metas_acct.AccountSheet_Report(
    p_C_AcctSchema_ID numeric,
    p_DateAcctFrom    DATE,
    p_DateAcctTo      date,
    p_Account_ID      numeric,
    p_Account_IDs     numeric[]
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.AccountSheet_Report(
    p_C_AcctSchema_ID numeric,
    p_DateAcctFrom    DATE,
    p_DateAcctTo      date,
    p_Account_ID      numeric = NULL,
    p_Account_IDs     numeric[] = NULL
)
    RETURNS
        TABLE
        (
            konto         text,
            gegenkonto    text,
            Buchungsdatum text,
            Belegdatum    text,
            Beschreibung  character varying,
            Steuersatz    numeric,
            soll          numeric,
            haben         numeric,
            Saldo         numeric,
            soll_währung  numeric,
            haben_währung numeric,
            währung       text,
            Belegart      character varying,
            "Nr"          text
        )
    LANGUAGE plpgsql
    STABLE


AS
$BODY$
DECLARE
    v_Account_IDs numeric[];
BEGIN
    IF (p_Account_ID IS NOT NULL AND p_Account_ID > 0) THEN
        v_Account_IDs := ARRAY [p_Account_ID];
    ELSE
        v_Account_IDs := array_retain_positive(p_Account_IDs);
    END IF;

    IF (v_Account_IDs IS NULL OR ARRAY_LENGTH(v_Account_IDs, 1) = 0) THEN
        RAISE EXCEPTION 'No account(s) provided. p_Account_ID(=%) or p_Account_IDs(=%) must be set.', p_Account_ID, p_Account_IDs;
    END IF;

    RETURN QUERY
        SELECT t.AccountValueAndName::text                                                            AS konto,
               t.counterpart_AccountValueAndName::text                                                AS gegenkonto,
               TO_CHAR(t.dateacct, 'DD.MM.YYYY')::text                                                AS Buchungsdatum,
               TO_CHAR(t.datetrx, 'DD.MM.YYYY')::text                                                 AS Belegdatum,
               t.description                                                                          AS Beschreibung,
               t.rate                                                                                 AS Steuersatz,
               t.amtacctdr                                                                            AS soll,
               t.amtacctcr                                                                            AS haben,
               t.balance                                                                              AS Saldo,
               t.amtsourcedr                                                                          AS soll_währung,
               t.amtsourcecr                                                                          AS haben_währung,
               (SELECT cy.iso_code FROM c_currency cy WHERE cy.c_currency_id = t.c_currency_id)::text AS währung,
               t.docTypeName                                                                          AS Belegart,
               t.documentno::text                                                                     AS "Nr"
        FROM de_metas_acct.RV_AccountSheet t
        WHERE TRUE
          AND t.PostingType = 'A'
          AND t.C_AcctSchema_ID = p_C_AcctSchema_ID
          AND t.account_id = ANY (v_Account_IDs)
          AND (p_DateAcctFrom IS NULL OR t.dateacct >= p_DateAcctFrom)
          AND (p_DateAcctTo IS NULL OR t.dateacct <= p_DateAcctTo)
        ORDER BY t.dateacct, t.ad_table_id, t.record_id, t.fact_acct_id;
END;
$BODY$
;
