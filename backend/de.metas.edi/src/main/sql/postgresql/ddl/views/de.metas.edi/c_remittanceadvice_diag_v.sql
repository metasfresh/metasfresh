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

CREATE VIEW "de.metas.edi".c_remittanceadvice_diag_v AS
SELECT ral.created                    AS ral_created,
       ral.c_remittanceadvice_id      AS ral_c_remittanceadvice_id,
       ral.c_remittanceadvice_line_id AS ral_c_remittanceadvice_line_id,

       i2p.inv_c_invoice_id,
       i2p.inv_docstatus,
       i2p.inv_docstatus_ok,
       i2si.inv_c_invoice_docbasetype,
       i2si.inv_c_invoice_doctypename,
       i2si.sinv_c_invoice_id,
       i2si.sinv_docstatus,
       si2i.sinv_docstatus_ok,
       si2i.sinv_ref_invoice_id,
       si2i.sinv_ref_invoice_id_ok,
       si2i.sinv_c_invoice_docbasetype,
       si2i.sinv_c_invoice_doctypename,

       i2p.allocation_type            AS i2p_allocation_type,
       i2p.al_amount                  AS i2p_al_amount,
       i2p.al_amount_exp              AS i2p_al_amount_exp,
       i2p.al_amount_ok               AS i2p_al_amount_ok,
       i2p.al_discountamt             AS i2p_al_discountamt,
       i2p.al_discountamt_exp         AS i2p_al_discountamt_exp,
       i2p.al_discountamt_ok          AS i2p_al_discountamt_ok,
       i2p.al_writeoffamt             AS i2p_al_writeoffamt,
       i2p.al_writeoffamt_ok          AS i2p_al_writeoffamt_ok,
       i2p.al_overunderamt            AS i2p_al_overunderamt,
       i2p.al_overunderamt_ok         AS i2p_al_overunderamt_ok,
       i2p.ra_p_c_payment_id          AS i2p_ra_p_c_payment_id,
       i2p.ra_p_docstatus_ok          AS i2p_ra_p_docstatus_ok,
       i2p.al_c_payment_id            AS i2p_al_c_payment_id,
       i2p.c_payment_id_ok            AS i2p_c_payment_id_ok,
       i2p.al_c_allocationhdr_id      AS i2p_al_c_allocationhdr_id,
       i2p.al_c_allocationline_id     AS i2p_al_c_allocationline_id,

       i2si.allocation_type           AS i2si_allocation_type,
       i2si.al_amount                 AS i2si_al_amount,
       i2si.al_amount_exp             AS i2si_al_amount_exp,
       i2si.al_amount_ok              AS i2si_al_amount_ok,
       i2si.al_discountamt            AS i2si_al_discountamt,
       i2si.al_discountamt_ok         AS i2si_al_discountamt_ok,
       i2si.al_writeoffamt            AS i2si_al_writeoffamt,
       i2si.al_writeoffamt_ok         AS i2si_al_writeoffamt_ok,
       i2si.al_overunderamt           AS i2si_al_overunderamt,
       i2si.al_c_allocationhdr_id     AS i2si_al_c_allocationhdr_id,
       i2si.al_c_allocationline_id    AS i2si_al_c_allocationline_id,

       si2i.allocation_type           AS si2i_allocation_type,
       si2i.al_amount                 AS si2i_al_amount,
       si2i.al_amount_exp             AS si2i_al_amount_exp,
       si2i.al_amount_ok              AS si2i_al_amount_ok,
       si2i.al_discountamt            AS si2i_al_discountamt,
       si2i.al_discountamt_exp        AS si2i_al_discountamt_exp,
       si2i.al_discountamt_ok         AS si2i_al_discountamt_ok,
       si2i.al_writeoffamt            AS si2i_al_writeoffamt,
       si2i.al_writeoffamt_ok         AS si2i_al_writeoffamt_ok,
       si2i.al_overunderamt           AS si2i_al_overunderamt,
       si2i.al_c_allocationhdr_id     AS si2i_al_c_allocationhdr_id,
       si2i.al_c_allocationline_id    AS si2i_al_c_allocationline_id
FROM c_remittanceadvice_line ral
         LEFT JOIN "de.metas.edi".c_remittanceadvice_diag_I_to_P_allocs_v i2p ON ral.c_remittanceadvice_line_id = i2p.c_remittanceadvice_line_id
         LEFT JOIN "de.metas.edi".c_remittanceadvice_diag_I_to_SI_allocs_v i2si ON ral.c_remittanceadvice_line_id = i2si.c_remittanceadvice_line_id
         LEFT JOIN "de.metas.edi".c_remittanceadvice_diag_SI_to_I_allocs_v si2i ON ral.c_remittanceadvice_line_id = si2i.c_remittanceadvice_line_id
;
