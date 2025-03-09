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

CREATE OR REPLACE VIEW "de.metas.edi".c_remittanceadvice_diag_problems_v AS
SELECT *
FROM "de.metas.edi".c_remittanceadvice_diag_v
WHERE COALESCE(inv_docstatus_ok, FALSE) = FALSE
   OR COALESCE(sinv_ref_invoice_id_ok, FALSE) = FALSE
   OR COALESCE(sinv_docstatus_ok, FALSE) = FALSE
   OR COALESCE(i2p_c_payment_id_ok, FALSE) = FALSE
   OR COALESCE(i2p_ra_p_docstatus_ok, FALSE) = FALSE
   OR COALESCE(i2p_al_amount_ok, FALSE) = FALSE
   OR COALESCE(i2p_al_discountamt_ok, FALSE) = FALSE
   OR COALESCE(i2p_al_writeoffamt_ok, FALSE) = FALSE
   OR COALESCE(i2p_al_overunderamt_ok, FALSE) = FALSE
   OR COALESCE(i2si_al_amount_ok, FALSE) = FALSE
   OR COALESCE(i2si_al_writeoffamt_ok, FALSE) = FALSE
   OR COALESCE(i2si_al_discountamt_ok, FALSE) = FALSE
   OR COALESCE(si2i_al_amount_ok, FALSE) = FALSE
   OR COALESCE(si2i_al_writeoffamt_ok, FALSE) = FALSE
   OR COALESCE(si2i_al_discountamt_ok, FALSE) = FALSE
;

