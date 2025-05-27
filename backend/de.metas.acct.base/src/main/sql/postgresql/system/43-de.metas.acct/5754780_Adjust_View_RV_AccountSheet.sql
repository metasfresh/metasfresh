DROP VIEW IF EXISTS de_metas_acct.RV_AccountSheet
;


CREATE OR REPLACE VIEW de_metas_acct.RV_AccountSheet
AS
(
    --
    -- Show records that have the counterpart set: e.g. product account, tax account
    --
    SELECT ev.value || '_' || ev.name                                                                AS AccountValueAndName,
           ev2.value || '_' || ev2.name                                                              AS counterpart_AccountValueAndName,
           -- Amounts
           fa.amtacctdr,
           fa.amtacctcr,
           -- Document Info
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.amtsourcedr END)                  AS amtsourcedr,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.amtsourcecr END)                  AS amtsourcecr,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.amtsourcedr - fa.amtsourcecr END) AS balance,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.c_currency_id END)                AS c_currency_id,
           fa.dateacct,
           fa.datetrx,
           fa.description,
           tax.rate,
           dt.name                                                                                   AS docTypeName,
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
             LEFT OUTER JOIN C_Tax tax ON fa.c_tax_id = tax.c_tax_ID
             LEFT OUTER JOIN C_DocType dt ON fa.c_doctype_id = dt.c_doctype_id
    --
)
--
UNION ALL
--
(
    --
    -- Show records that do not have the counterpart set, but there are other records that point to them: e.g. partner account
    --
    SELECT ev.value || '_' || ev.name                                                                  AS AccountValueAndName,
           ev2.value || '_' || ev2.name                                                                AS counterpart_AccountValueAndName,
           -- Amounts:
           fa2.amtacctcr                                                                               AS amtacctdr,
           fa2.amtacctdr                                                                               AS amtacctcr,
           -- Document Info
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa2.amtsourcecr END)                   AS amtsourcedr,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa2.amtsourcedr END)                   AS amtsourcecr,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa2.amtsourcecr - fa2.amtsourcedr END) AS balance,
           (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.c_currency_id END)                  AS c_currency_id,
           fa.dateacct,
           fa.datetrx,
           fa.description,
           tax.rate,
           dt.name                                                                                     AS docTypeName,
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
             LEFT OUTER JOIN C_Tax tax ON fa2.c_tax_id = tax.c_tax_ID
             LEFT OUTER JOIN C_DocType dt ON fa.c_doctype_id = dt.c_doctype_id
    WHERE fa.counterpart_fact_acct_id IS NULL
    --
)
--
UNION ALL
--
(
    --
    -- Show records that do not have the counterpart set, neither other records point to them: e.g. accounts used in SAP Journal, Tax acounts
    --
    SELECT ev.value || '_' || ev.name        AS AccountValueAndName,
           NULL                              AS counterpart_AccountValueAndName,
           -- Amounts:
           fa.amtacctdr                      AS amtacctdr,
           fa.amtacctcr                      AS amtacctcr,
           -- Document Info
           fa.amtsourcedr                    AS amtsourcedr,
           fa.amtsourcecr                    AS amtsourcecr,
           (fa.amtsourcedr - fa.amtsourcecr) AS balance,
           fa.c_currency_id                  AS c_currency_id,
           fa.dateacct,
           fa.datetrx,
           fa.description,
           tax.rate,
           dt.name                           AS docTypeName,
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
             INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fa.Account_ID)
             LEFT OUTER JOIN C_Tax tax ON fa.c_tax_id = tax.c_tax_ID
             LEFT OUTER JOIN C_DocType dt ON fa.c_doctype_id = dt.c_doctype_id
    WHERE fa.counterpart_fact_acct_id IS NULL
      AND NOT EXISTS (SELECT 1 FROM fact_acct fa2 WHERE fa2.counterpart_fact_acct_id = fa.fact_acct_id))
;

