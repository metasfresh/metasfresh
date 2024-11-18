DROP VIEW IF EXISTS RV_DATEV_Fact_Acct_Source
;

CREATE OR REPLACE VIEW RV_DATEV_Fact_Acct_Source AS
SELECT
    --
    -- DR/CR Accounts:
    (CASE WHEN fa.amtacctdr != 0 THEN fa.account_id ELSE fa2.account_id END)                                                              AS dr_account_id,
    (CASE WHEN fa.amtacctdr != 0 THEN fa2.account_id ELSE fa.account_id END)                                                              AS cr_account_id,
    --
    -- Amounts:
    (CASE WHEN fa.amtacctdr != 0 THEN fa2.amtacctcr ELSE fa2.amtacctdr END)                                                               AS amt,
    (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN fa.c_currency_id END)                                                            AS c_currency_id,
    (CASE WHEN fa.c_currency_id = fa2.c_currency_id THEN (CASE WHEN fa.amtacctdr != 0 THEN fa2.amtsourcecr ELSE fa2.amtsourcedr END) END) AS amtsource,
    --
    -- Document Info
    fa.dateacct,
    fa.datetrx,
    fa.documentno,
    fa.c_doctype_id,
    fa.docbasetype,
    COALESCE(fa2.c_bpartner_id, fa.c_bpartner_id)                                                                                         AS c_bpartner_id,
    COALESCE(fa2.c_tax_id, fa.c_tax_id)                                                                                                   AS c_tax_id,
    COALESCE(fa2.vatcode, fa.vatcode)                                                                                                     AS vatcode,
    COALESCE(fa2.description, fa.description)                                                                                             AS description,
    --
    -- Posting schema, type
    fa.c_acctschema_id,
    fa.postingtype,
    --
    -- Document ref
    fa.ad_table_id,
    fa.record_id,
    COALESCE(fa2.line_id, fa.line_id)                                                                                                     AS line_id,
    fa.ad_client_id,
    fa.ad_org_id,
    COALESCE(fa2.c_activity_id, fa.c_activity_id)                                                                                         AS c_activity_id,
    (CASE WHEN fa.amtacctdr != 0 THEN fa.fact_acct_id ELSE fa2.fact_acct_id END)                                                          AS dr_fact_acct_id,
    (CASE WHEN fa.amtacctdr != 0 THEN fa2.fact_acct_id ELSE fa.fact_acct_id END)                                                          AS cr_fact_acct_id,
    COALESCE(fa2.fact_acct_id, fa.fact_acct_id)                                                                                           AS id
FROM Fact_Acct fa
         LEFT OUTER JOIN Fact_Acct fa2 ON (fa2.counterpart_fact_acct_id = fa.fact_acct_id)
WHERE fa.counterpart_fact_acct_id IS NULL
;
