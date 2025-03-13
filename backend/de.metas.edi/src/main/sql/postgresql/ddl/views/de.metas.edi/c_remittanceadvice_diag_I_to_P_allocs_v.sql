/*
 * #%L
 * de.metas.edi
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

CREATE VIEW "de.metas.edi".c_remittanceadvice_diag_I_to_P_allocs_v AS
SELECT 'invoice-to-payment'                                                                                                               AS allocation_type,
       ral.created                                                                                                                        AS ral_created,
       ral.c_remittanceadvice_id,
       ral.c_remittanceadvice_line_id,
       inv.c_invoice_id                                                                                                                   AS inv_c_invoice_id,
       inv.docstatus                                                                                                                      AS inv_docstatus,
       inv.docstatus = 'CO'                                                                                                               AS inv_docstatus_ok,
       inv_dt.docbasetype                                                                                                                 AS inv_c_invoice_docbasetype,
       inv_dt.name                                                                                                                        AS inv_c_invoice_doctypename,
       al.amount                                                                                                                          AS al_amount,
       (CASE WHEN inv_dt.docbasetype IN ('ARI', 'APC') THEN ral.remittanceamt ELSE ral.remittanceamt * -1 END)                            AS al_amount_exp,
       al.amount = (CASE WHEN inv_dt.docbasetype IN ('ARI', 'APC') THEN ral.remittanceamt ELSE ral.remittanceamt * -1 END)                AS al_amount_ok,
       al.discountamt                                                                                                                     AS al_discountamt,
       (CASE WHEN inv_dt.docbasetype IN ('ARI', 'APC') THEN ral.paymentdiscountamt ELSE ral.paymentdiscountamt * -1 END)                  AS al_discountamt_exp,
       al.discountamt = (CASE WHEN inv_dt.docbasetype IN ('ARI', 'APC') THEN ral.paymentdiscountamt ELSE ral.paymentdiscountamt * -1 END) AS al_discountamt_ok,
       al.writeoffamt                                                                                                                     AS al_writeoffamt,
       al.writeoffamt = 0                                                                                                                 AS al_writeoffamt_ok,
       al.overunderamt                                                                                                                    AS al_overunderamt,
       al.overunderamt = 0                                                                                                                AS al_overunderamt_ok,
       ra.c_payment_id                                                                                                                    AS ra_p_c_payment_id,
       ra_p.docstatus = 'CO'                                                                                                              AS ra_p_docstatus_ok,
       al.c_payment_id                                                                                                                    AS al_c_payment_id,
       COALESCE(al.c_payment_id, 0) = COALESCE(ra.c_payment_id, 1)                                                                        AS c_payment_id_ok,
       al.c_allocationhdr_id                                                                                                              AS al_c_allocationhdr_id,
       al.c_allocationline_id                                                                                                             AS al_c_allocationline_id
FROM c_remittanceadvice_line ral
         JOIN c_remittanceadvice ra ON ral.c_remittanceadvice_id = ra.c_remittanceadvice_id
         JOIN c_invoice inv ON ral.c_invoice_id = inv.c_invoice_id
         JOIN c_payment ra_p ON ra.c_payment_id = ra_p.c_payment_id
         LEFT JOIN c_allocationline al ON inv.c_invoice_id = al.c_invoice_id AND al.c_payment_id IS NOT NULL
         LEFT JOIN c_allocationhdr ah ON ah.c_allocationhdr_id = al.c_allocationhdr_id
         LEFT JOIN c_doctype inv_dt ON inv.c_doctype_id = inv_dt.c_doctype_id
WHERE TRUE
  AND ral.created > '2024-01-01'
  AND ah.docstatus IN ('CO', 'CL')
ORDER BY ral.c_remittanceadvice_line_id, al.c_allocationline_id
;
