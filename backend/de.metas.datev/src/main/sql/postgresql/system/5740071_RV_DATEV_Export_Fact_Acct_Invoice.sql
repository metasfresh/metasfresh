DROP VIEW IF EXISTS RV_DATEV_Export_Fact_Acct_Invoice
;

DROP FUNCTION IF EXISTS RV_DATEV_Export_Fact_Acct_Invoice(
    p_IsOneLinePerInvoiceTax char(1)
)
;


CREATE OR REPLACE FUNCTION RV_DATEV_Export_Fact_Acct_Invoice(
    p_IsOneLinePerInvoiceTax char(1) = 'N'
)
    RETURNS TABLE
            (
                dr_account                           varchar,
                cr_account                           varchar,
                Amt                                  numeric,
                Currency                             char(3),
                AmtSource                            numeric,
                TaxAmtSource                         numeric,
                c_tax_id                             numeric,
                c_tax_rate                           numeric,
                vatcode                              varchar,
                dateacct                             timestamp,
                datetrx                              timestamp,
                documentno                           varchar,
                c_doctype_id                         numeric,
                c_doctype_name                       varchar,
                IsSOTrx                              char(1),
                docbasetype                          varchar(3),
                c_bpartner_id                        numeric,
                BPValue                              varchar,
                BPName                               varchar,
                description                          text,
                c_acctschema_id                      numeric,
                postingtype                          char(1),
                c_invoice_id                         numeric,
                poreference                          varchar,
                ad_client_id                         numeric,
                ad_org_id                            numeric,
                c_activity_id                        numeric,
                ActivityName                         varchar,
                rv_datev_export_fact_acct_invoice_id numeric
            )
    LANGUAGE 'plpgsql'
AS
$BODY$
BEGIN
    RETURN QUERY SELECT ev_dr.value                                                                          AS dr_account,
                        ev_cr.value                                                                          AS cr_account,
                        SUM(fa.Amt)                                                                          AS Amt,
                        (SELECT cur.iso_code FROM c_currency cur WHERE cur.c_currency_id = fa.c_currency_id) AS Currency,
                        SUM(fa.AmtSource)                                                                    AS AmtSource,
                        SUM(fa.TaxAmtSource)                                                                 AS TaxAmtSource,
                        fa.c_tax_id                                                                          AS c_tax_id,
                        tax.rate                                                                             AS c_tax_rate,
                        fa.vatcode,
                        fa.dateacct,
                        fa.datetrx,
                        fa.documentno,
                        fa.c_doctype_id                                                                      AS c_doctype_id,
                        dt.name                                                                              AS c_doctype_name,
                        dt.issotrx                                                                           AS IsSOTrx,
                        fa.docbasetype,
                        fa.c_bpartner_id,
                        bp.Value                                                                             AS BPValue,
                        bp.Name                                                                              AS BPName,
                        STRING_AGG(DISTINCT fa.description, ', ')                                            AS description,
                        fa.c_acctschema_id,
                        fa.postingtype,
                        fa.c_invoice_id,
                        i.poreference                                                                        AS poreference,
                        fa.ad_client_id,
                        fa.ad_org_id,
                        fa.c_activity_id,
                        a.name                                                                               AS ActivityName,
                        MIN(fa.id)                                                                           AS rv_datev_export_fact_acct_invoice_id
                 FROM (SELECT
                           --
                           -- DR/CR Accounts:
                           fa.dr_account_id,
                           fa.cr_account_id,
                           --
                           -- Amounts:
                           fa.amt                                                                                                                                                    AS Amt,
                           fa.c_currency_id                                                                                                                                          AS C_Currency_ID,
                           fa.amtsource                                                                                                                                              AS AmtSource,
                           --
                           -- Tax Amounts
                           (SELECT SUM(il.TaxAmtInfo) FROM c_invoiceline il WHERE il.c_invoice_id = fa.record_id AND il.c_invoiceline_id = fa.line_id AND il.c_tax_id = fa.c_tax_id) AS TaxAmtSource,
                           fa.c_tax_id,
                           fa.vatcode,

                           --
                           -- Document Info
                           fa.dateacct,
                           fa.datetrx,
                           fa.documentno,
                           fa.c_doctype_id,
                           fa.docbasetype,
                           fa.c_bpartner_id,
                           fa.description,
                           --
                           -- Posting schema, type
                           fa.c_acctschema_id,
                           fa.postingtype,
                           --
                           -- Document ref
                           -- fa.ad_table_id,
                           fa.record_id                                                                                                                                              AS c_invoice_id,
                           fa.line_id,
                           fa.ad_client_id,
                           fa.ad_org_id,
                           fa.c_activity_id,
                           fa.id
                       FROM RV_DATEV_Fact_Acct_Source fa
                       WHERE TRUE
                         AND fa.ad_table_id = 318 -- C_Invoice
                         -- If not p_IsOneLinePerInvoiceTax then skip tax account bookings because taxamt is already incorporated in the other lines
                         AND (p_IsOneLinePerInvoiceTax = 'N'
                           OR NOT (fa.line_id IS NULL AND fa.c_tax_id IS NOT NULL)
                           )
                          --
                      ) fa
                          LEFT OUTER JOIN C_ElementValue ev_dr ON (ev_dr.C_ElementValue_ID = fa.DR_Account_ID)
                          LEFT OUTER JOIN C_ElementValue ev_cr ON (ev_cr.C_ElementValue_ID = fa.CR_Account_ID)
                          LEFT OUTER JOIN C_Tax tax ON tax.C_Tax_ID = fa.C_Tax_ID
                          LEFT OUTER JOIN C_Activity a ON a.c_activity_id = fa.c_activity_id
                          LEFT OUTER JOIN C_BPartner bp ON bp.C_BPartner_ID = fa.C_BPartner_ID
                          LEFT OUTER JOIN C_DocType dt ON dt.C_DocType_ID = fa.C_DocType_ID
                          LEFT OUTER JOIN C_Invoice i ON i.C_Invoice_ID = fa.C_Invoice_ID
                 GROUP BY fa.dr_account_id, ev_dr.value, ev_dr.name,
                          fa.cr_account_id, ev_cr.value, ev_cr.name,
                          fa.c_currency_id,
                          fa.c_tax_id, tax.rate,
                          fa.vatcode,
                          fa.dateacct,
                          fa.datetrx,
                          fa.documentno,
                          fa.c_doctype_id, dt.name, dt.issotrx,
                          fa.docbasetype,
                          fa.c_bpartner_id, bp.Value, bp.Name,
                          fa.c_acctschema_id,
                          fa.postingtype,
                          fa.c_invoice_id, i.poreference, i.c_paymentterm_id, i.dateinvoiced,
                          fa.ad_client_id,
                          fa.ad_org_id,
                          fa.c_activity_id, a.name,
                          -- aggregate only if p_IsOneLinePerInvoiceTax=Y: 
                          (CASE WHEN p_IsOneLinePerInvoiceTax = 'Y' THEN 0 ELSE fa.id END)
    --
    ;
END;
$BODY$
;



--
--
--
--
--

CREATE OR REPLACE VIEW RV_DATEV_Export_Fact_Acct_Invoice AS
SELECT *
FROM RV_DATEV_Export_Fact_Acct_Invoice()
;


