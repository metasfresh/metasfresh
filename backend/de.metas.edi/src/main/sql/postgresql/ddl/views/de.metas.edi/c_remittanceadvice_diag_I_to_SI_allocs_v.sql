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

DROP VIEW IF EXISTS "de.metas.edi".c_remittanceadvice_diag_I_to_SI_allocs_v
;

CREATE VIEW "de.metas.edi".c_remittanceadvice_diag_I_to_SI_allocs_v AS
SELECT 'invoice-to-serviceInvoice'                                                                                               AS allocation_type,
       ral.created                                                                                                               AS ral_created,
       ral.c_remittanceadvice_id,
       ral.c_remittanceadvice_line_id,
       inv.c_invoice_id                                                                                                          AS inv_c_invoice_id,
       inv.docstatus                                                                                                             AS inv_docstatus,
       inv.docstatus = 'CO'                                                                                                      AS inv_docstatus_ok,
       inv_dt.docbasetype                                                                                                        AS inv_c_invoice_docbasetype,
       inv_dt.name                                                                                                               AS inv_c_invoice_doctypename,
       sinv.c_invoice_id                                                                                                         AS sinv_c_invoice_id,
       sinv.docstatus                                                                                                            AS sinv_docstatus,
       al.amount                                                                                                                 AS al_amount,
       (CASE WHEN inv_dt.docbasetype IN ('ARI', 'APC') THEN ral.servicefeeamount ELSE ral.servicefeeamount * -1 END)             AS al_amount_exp,
       al.amount = (CASE WHEN inv_dt.docbasetype IN ('ARI', 'APC') THEN ral.servicefeeamount ELSE ral.servicefeeamount * -1 END) AS al_amount_ok,
       al.discountamt                                                                                                            AS al_discountamt,
       al.discountamt = 0                                                                                                        AS al_discountamt_ok,
       al.writeoffamt                                                                                                            AS al_writeoffamt,
       al.writeoffamt = 0                                                                                                        AS al_writeoffamt_ok,
       al.overunderamt                                                                                                           AS al_overunderamt,
       al.c_allocationhdr_id                                                                                                     AS al_c_allocationhdr_id,
       al.c_allocationline_id                                                                                                    AS al_c_allocationline_id
FROM c_remittanceadvice_line ral
         JOIN c_remittanceadvice ra ON ral.c_remittanceadvice_id = ra.c_remittanceadvice_id
         JOIN c_invoice inv ON ral.c_invoice_id = inv.c_invoice_id
         LEFT JOIN c_invoice sinv ON ral.service_fee_invoice_id = sinv.c_invoice_id
         LEFT JOIN c_allocationline al ON ral.c_invoice_id = al.c_invoice_id AND al.c_payment_id IS NULL
         LEFT JOIN c_allocationhdr ah ON ah.c_allocationhdr_id = al.c_allocationhdr_id
         LEFT JOIN c_doctype inv_dt ON inv.c_doctype_id = inv_dt.c_doctype_id
WHERE TRUE
  AND ral.created > '2024-01-01'
  AND ah.docstatus IN ('CO', 'CL')
ORDER BY ral.c_remittanceadvice_line_id, al.c_allocationline_id
;