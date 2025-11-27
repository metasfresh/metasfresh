/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

DROP VIEW IF EXISTS report.Lexware_Invoice_Export_V
;

CREATE OR REPLACE VIEW report.Lexware_Invoice_Export_V AS
SELECT
    -- Invoice Header Information
    inv.c_invoice_id,
    inv.documentno                                           AS invoice_number,
    inv.dateinvoiced                                         AS invoice_date,
    inv.dateacct                                             AS accounting_date,

    -- Invoice Type Classification
    CASE
        WHEN inv.issotrx = 'Y' THEN 'AR'
                               ELSE 'AP'
    END                                                      AS invoice_type,

    CASE
        WHEN inv.issotrx = 'Y' AND dt.docbasetype = 'ARI' THEN 'Customer Invoice'
        WHEN inv.issotrx = 'Y' AND dt.docbasetype = 'ARC' THEN 'Customer Credit Memo'
        WHEN inv.issotrx = 'N' AND dt.docbasetype = 'API' THEN 'Vendor Invoice'
        WHEN inv.issotrx = 'N' AND dt.docbasetype = 'APC' THEN 'Vendor Credit Memo'
                                                          ELSE dt.name
    END                                                      AS document_type,

    dt.docbasetype                                           AS document_base_type,

    -- Business Partner Information
    bp.value                                                 AS partner_number,
    bp.name                                                  AS partner_name,

    -- Financial Amounts
    inv.totallines                                           AS net_amount,
    inv.grandtotal - inv.totallines                          AS tax_amount,
    inv.grandtotal                                           AS gross_amount,

    -- Currency
    cur.iso_code                                             AS currency,

    -- Payment Information
    inv.paymentrule                                          AS payment_method,
    pt.value                                                 AS payment_term,
    CASE WHEN inv.ispaid = 'Y' THEN 'Paid' ELSE 'Unpaid' END AS payment_status,

    -- Tax Information
    COALESCE(inv.istaxincluded, 'N')                         AS tax_included,

    -- Document Status
    inv.docstatus,
    CASE
        WHEN inv.docstatus = 'CO' THEN 'Completed'
        WHEN inv.docstatus = 'CL' THEN 'Closed'
        WHEN inv.docstatus = 'VO' THEN 'Voided'
        WHEN inv.docstatus = 'DR' THEN 'Draft'
                                  ELSE inv.docstatus
    END                                                      AS document_status,

    -- Additional Information
    inv.description                                          AS invoice_description,
    inv.poreference                                          AS po_reference,
    inv.reversal_id,

    -- Dates
    inv.created                                              AS creation_date,
    inv.updated                                              AS last_update

FROM c_invoice inv
         INNER JOIN c_bpartner bp ON inv.c_bpartner_id = bp.c_bpartner_id
         LEFT JOIN c_doctype dt ON inv.c_doctype_id = dt.c_doctype_id
         LEFT JOIN c_currency cur ON inv.c_currency_id = cur.c_currency_id
         LEFT JOIN c_paymentterm pt ON inv.c_paymentterm_id = pt.c_paymentterm_id
WHERE inv.isactive = 'Y'
  AND inv.docstatus IN ('CO', 'CL')
ORDER BY inv.dateinvoiced DESC, inv.documentno
;
