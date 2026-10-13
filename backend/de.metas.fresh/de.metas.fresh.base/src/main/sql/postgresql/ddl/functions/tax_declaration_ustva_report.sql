SELECT db_drop_functions('report.tax_declaration_ustva_report')
;

-- UStVA (German VAT advance return) report for one tax declaration (C_TaxDeclaration).
-- One flat row set, discriminated by report_level: HEADER (1 row), SUMMARY (1 row per VAT code, + 1 per code with lines of type unknown), BALANCE (1 row), DETAIL.
-- Figures come only from C_TaxDeclarationLine / C_TaxDeclarationAcct (the frozen snapshot), never from Fact_Acct amounts.
-- amount_type 'U' (type unknown): lines and rows without amount type (postings made before Fact_Acct.VATCodeAmountType existed);
-- they get an own SUMMARY row per code with the amount in net_amt.
CREATE FUNCTION report.tax_declaration_ustva_report(
    p_C_TaxDeclaration_ID numeric,
    p_AD_Language         varchar -- not used: C_VAT_Code.Description has no translation; kept for the Jasper report's standard parameters
)
    RETURNS TABLE
            (
                report_level        varchar,
                level_order         integer,
                vatcode             varchar,
                amount_type         varchar,
                description         varchar,
                net_amt             numeric,
                tax_amt             numeric,
                balance_amt         numeric,
                documentno          varchar,
                docstatus           varchar,
                is_correction       varchar,
                original_documentno varchar,
                org_name            varchar,
                org_tax_id          varchar,
                org_vat_id          varchar,
                acctschema_name     varchar,
                period_from         date,
                period_to           date,
                currency            varchar,
                print_date          timestamptz,
                posting_date        date,
                doc_date            date,
                bpartner_name       varchar,
                bpartner_vatid      varchar,
                amount              numeric
            )
AS
$BODY$
WITH td AS (SELECT td.c_taxdeclaration_id,
                   td.c_acctschema_id,
                   td.documentno,
                   td.docstatus,
                   td.iscorrection,
                   td.c_taxdeclaration_original_id,
                   td.ad_org_id,
                   td.c_period_id
            FROM c_taxdeclaration td
            WHERE td.c_taxdeclaration_id = p_C_TaxDeclaration_ID),
     -- declared (debit - credit) non-zero lines; zero-amount lines are dropped everywhere
     lines AS (SELECT l.vatcode, COALESCE(l.amounttype, 'U') AS amounttype, l.amount AS declared_amt
               FROM c_taxdeclarationline l
                        JOIN td ON td.c_taxdeclaration_id = l.c_taxdeclaration_id
               WHERE l.isactive = 'Y'
                 AND l.amount <> 0),
     -- display sign: -1 when every active C_VAT_Code row of the code + amount type on the schema is a sales code;
     -- a row without IsSOTrx applies to both sides, so it is not a sales row; type unknown: all rows of the code
     signed AS (SELECT l.vatcode,
                       l.amounttype,
                       l.declared_amt,
                       CASE
                           WHEN COALESCE((SELECT BOOL_AND(COALESCE(v.issotrx, 'N') = 'Y')
                                          FROM c_vat_code v
                                          WHERE v.c_acctschema_id = td.c_acctschema_id
                                            AND v.vatcode = l.vatcode
                                            AND (v.amounttype = l.amounttype OR l.amounttype = 'U')
                                            AND v.isactive = 'Y'), FALSE)
                               THEN -1
                               ELSE 1
                       END AS sgn
                FROM lines l,
                     td),
     header AS (SELECT 'HEADER'::varchar           AS report_level,
                       1                           AS level_order,
                       NULL::varchar               AS vatcode,
                       NULL::varchar               AS amount_type,
                       NULL::varchar               AS description,
                       NULL::numeric               AS net_amt,
                       NULL::numeric               AS tax_amt,
                       NULL::numeric               AS balance_amt,
                       td.documentno::varchar      AS documentno,
                       td.docstatus::varchar       AS docstatus,
                       td.iscorrection::varchar    AS is_correction,
                       orig.documentno::varchar    AS original_documentno,
                       o.name::varchar             AS org_name,
                       orgbp_link.taxid::varchar   AS org_tax_id,
                       orgbp_link.vataxid::varchar AS org_vat_id,
                       s.name::varchar             AS acctschema_name,
                       p.startdate::date           AS period_from,
                       p.enddate::date             AS period_to,
                       cur.iso_code::varchar       AS currency,
                       NOW()                       AS print_date,
                       NULL::date                  AS posting_date,
                       NULL::date                  AS doc_date,
                       NULL::varchar               AS bpartner_name,
                       NULL::varchar               AS bpartner_vatid,
                       NULL::numeric               AS amount
                FROM td
                         JOIN ad_org o ON o.ad_org_id = td.ad_org_id
                         -- the organisation's business partner; a unique index allows at most one active one per organisation
                         LEFT JOIN c_bpartner orgbp_link ON orgbp_link.ad_orgbp_id = td.ad_org_id AND orgbp_link.isactive = 'Y'
                         JOIN c_acctschema s ON s.c_acctschema_id = td.c_acctschema_id
                         LEFT JOIN c_currency cur ON cur.c_currency_id = s.c_currency_id
                         LEFT JOIN c_period p ON p.c_period_id = td.c_period_id
                         LEFT JOIN c_taxdeclaration orig ON orig.c_taxdeclaration_id = td.c_taxdeclaration_original_id),
     summary AS (SELECT 'SUMMARY'::varchar                                                          AS report_level,
                        2                                                                           AS level_order,
                        c.vatcode::varchar                                                          AS vatcode,
                        CASE WHEN BOOL_OR(c.amounttype = 'U') THEN 'U' END::varchar                 AS amount_type,
                        (SELECT MAX(v.description)
                         FROM c_vat_code v
                         WHERE v.c_acctschema_id = td.c_acctschema_id
                           AND v.vatcode = c.vatcode
                           AND v.isactive = 'Y')::varchar                                           AS description,
                        TRUNC(SUM(CASE WHEN c.amounttype IN ('N', 'U') THEN c.declared_amt * c.sgn END), 0) AS net_amt,
                        SUM(CASE WHEN c.amounttype = 'T' THEN c.declared_amt * c.sgn END)           AS tax_amt
                 FROM signed c,
                      td
                 -- the code's lines without amount type get an own row
                 GROUP BY c.vatcode, td.c_acctschema_id, c.amounttype = 'U'),
     balance AS (SELECT -COALESCE(SUM(l.declared_amt), 0) AS balance_amt
                 FROM lines l
                 WHERE l.amounttype = 'T'),
     detail AS (SELECT a.vatcode,
                       sg.amounttype,
                       a.description,
                       a.amount * sg.sgn AS amount,
                       doc.documentno,
                       doc.posting_date,
                       doc.doc_date,
                       doc.bpartner_name,
                       doc.bpartner_vatid,
                       doc.currency
                FROM c_taxdeclarationacct a
                         JOIN td ON td.c_taxdeclaration_id = a.c_taxdeclaration_id
                         JOIN signed sg ON sg.vatcode = a.vatcode AND sg.amounttype = COALESCE(a.amounttype, 'U')
                         LEFT JOIN LATERAL (
                    -- read-only lookup of the referenced source document
                    SELECT i.documentno::varchar,
                           i.dateacct::date     AS posting_date,
                           i.dateinvoiced::date AS doc_date,
                           bp.name::varchar     AS bpartner_name,
                           bp.vataxid::varchar  AS bpartner_vatid,
                           c.iso_code::varchar  AS currency
                    FROM c_invoice i
                             LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = i.c_bpartner_id
                             LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
                    WHERE a.ad_table_id = (SELECT t.ad_table_id FROM ad_table t WHERE t.tablename = 'C_Invoice')
                      AND i.c_invoice_id = a.record_id
                    UNION ALL
                    SELECT j.documentno::varchar,
                           j.dateacct::date,
                           j.datedoc::date,
                           NULL::varchar,
                           NULL::varchar,
                           c.iso_code::varchar
                    FROM gl_journal j
                             LEFT JOIN c_currency c ON c.c_currency_id = j.c_currency_id
                    WHERE a.ad_table_id = (SELECT t.ad_table_id FROM ad_table t WHERE t.tablename = 'GL_Journal')
                      AND j.gl_journal_id = a.record_id
                    UNION ALL
                    SELECT h.documentno::varchar,
                           h.dateacct::date,
                           h.datetrx::date,
                           bp.name::varchar,
                           bp.vataxid::varchar,
                           c.iso_code::varchar
                    FROM c_allocationhdr h
                             LEFT JOIN c_allocationline al ON al.c_allocationline_id = a.line_id
                             LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = al.c_bpartner_id
                             LEFT JOIN c_currency c ON c.c_currency_id = h.c_currency_id
                    WHERE a.ad_table_id = (SELECT t.ad_table_id FROM ad_table t WHERE t.tablename = 'C_AllocationHdr')
                      AND h.c_allocationhdr_id = a.record_id
                    UNION ALL
                    SELECT sj.documentno::varchar,
                           sj.dateacct::date,
                           sj.datedoc::date,
                           bp.name::varchar,
                           bp.vataxid::varchar,
                           c.iso_code::varchar
                    FROM sap_gljournal sj
                             LEFT JOIN sap_gljournalline sjl ON sjl.sap_gljournalline_id = a.line_id
                             -- a generated tax line carries no partner; take the one of its base line
                             LEFT JOIN sap_gljournalline sjl_parent ON sjl_parent.sap_gljournalline_id = sjl.parent_id
                             LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = COALESCE(sjl.c_bpartner_id, sjl_parent.c_bpartner_id)
                             LEFT JOIN c_currency c ON c.c_currency_id = sj.c_currency_id
                    WHERE a.ad_table_id = (SELECT t.ad_table_id FROM ad_table t WHERE t.tablename = 'SAP_GLJournal')
                      AND sj.sap_gljournal_id = a.record_id
                    ) doc ON TRUE
                WHERE a.isactive = 'Y'
                  AND a.amount <> 0)
SELECT h.report_level,
       h.level_order,
       h.vatcode,
       h.amount_type,
       h.description,
       h.net_amt,
       h.tax_amt,
       h.balance_amt,
       h.documentno,
       h.docstatus,
       h.is_correction,
       h.original_documentno,
       h.org_name,
       h.org_tax_id,
       h.org_vat_id,
       h.acctschema_name,
       h.period_from,
       h.period_to,
       h.currency,
       h.print_date,
       h.posting_date,
       h.doc_date,
       h.bpartner_name,
       h.bpartner_vatid,
       h.amount
FROM header h
UNION ALL
SELECT s.report_level,
       s.level_order,
       s.vatcode,
       s.amount_type,
       s.description,
       s.net_amt,
       s.tax_amt,
       NULL::numeric,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::date,
       NULL::date,
       NULL::varchar,
       NULL::timestamptz,
       NULL::date,
       NULL::date,
       NULL::varchar,
       NULL::varchar,
       NULL::numeric
FROM summary s
UNION ALL
SELECT 'BALANCE'::varchar,
       3,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::numeric,
       NULL::numeric,
       b.balance_amt,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::date,
       NULL::date,
       NULL::varchar,
       NULL::timestamptz,
       NULL::date,
       NULL::date,
       NULL::varchar,
       NULL::varchar,
       NULL::numeric
FROM balance b
UNION ALL
SELECT 'DETAIL'::varchar,
       4,
       d.vatcode::varchar,
       d.amounttype::varchar,
       d.description::varchar,
       NULL::numeric,
       NULL::numeric,
       NULL::numeric,
       d.documentno,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::varchar,
       NULL::date,
       NULL::date,
       d.currency,
       NULL::timestamptz,
       d.posting_date,
       d.doc_date,
       d.bpartner_name,
       d.bpartner_vatid,
       d.amount
FROM detail d
-- NULLS FIRST: a code's regular SUMMARY row before its "type unknown" row
ORDER BY level_order, vatcode, amount_type NULLS FIRST, posting_date, documentno
$BODY$
    LANGUAGE sql
    STABLE
;
