DROP VIEW IF EXISTS de_metas_acct.RV_AccountSheet
;


CREATE OR REPLACE VIEW de_metas_acct.RV_AccountSheet
AS
( --
    SELECT ev.value || '_' || ev.name                                                 AS AccountValueAndName,
           ev2.value || '_' || ev2.name                                               AS counterpart_AccountValueAndName,
           -- Amounts
           fa.amtacctdr,
           fa.amtacctcr,
           -- Document Info
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.amtsourcedr END)   AS amtsourcedr,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.amtsourcecr END)   AS amtsourcecr,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.c_currency_id END) AS c_currency_id,
           fa.dateacct,
           fa.datetrx,
           fa.documentno,
           --
           fa.account_id,
           fa.c_acctschema_id,
           fa.postingtype,
           --
           fa.ad_table_id,
           fa.record_id,
           fa.fact_acct_id
    FROM Fact_Acct fa
             INNER JOIN Fact_Acct fa2 ON (fa2.Fact_Acct_ID = fa.Counterpart_Fact_Acct_ID)
             INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fa.Account_ID)
             INNER JOIN C_ElementValue ev2 ON (ev2.C_ElementValue_ID = fa2.Account_ID)
    --
)
--
UNION ALL
--
( --
    SELECT ev.value || '_' || ev.name                                                 AS AccountValueAndName,
           ev2.value || '_' || ev2.name                                               AS counterpart_AccountValueAndName,
           -- Amounts:
           fa2.amtacctcr                                                              AS amtacctdr,
           fa2.amtacctdr                                                              AS amtacctcr,
           -- Document Info
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa2.amtsourcecr END)  AS amtsourcedr,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa2.amtsourcedr END)  AS amtsourcecr,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.c_currency_id END) AS c_currency_id,
           fa.dateacct,
           fa.datetrx,
           fa.documentno,
           --
           fa.account_id,
           fa.c_acctschema_id,
           fa.postingtype,
           --
           fa.ad_table_id,
           fa.record_id,
           fa.fact_acct_id
    FROM Fact_Acct fa
             INNER JOIN Fact_Acct fa2 ON (fa2.counterpart_fact_acct_id = fa.fact_acct_id)
             INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fa.Account_ID)
             INNER JOIN C_ElementValue ev2 ON (ev2.C_ElementValue_ID = fa2.Account_ID)
    WHERE fa.counterpart_fact_acct_id IS NULL
    --
)
;

