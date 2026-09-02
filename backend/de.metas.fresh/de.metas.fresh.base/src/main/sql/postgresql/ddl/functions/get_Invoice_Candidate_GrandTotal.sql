/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

DROP FUNCTION IF EXISTS get_Invoice_Candidate_GrandTotal(numeric)
;

CREATE OR REPLACE FUNCTION get_Invoice_Candidate_GrandTotal(IN p_Invoice_Candidate_ID numeric)
    RETURNS numeric
    LANGUAGE sql
AS
$$
WITH net_amount AS (SELECT (COALESCE(ic.qtytoinvoice_override, ic.QtyOrdered) *
                            COALESCE(ic.PriceEntered_Override, ic.priceentered) *
                            (1 - COALESCE(ic.discount_override, ic.Discount, 0) / 100)) AS net,
                           COALESCE(tax.rate, 0)                                         AS tax_rate
                    FROM c_invoice_candidate ic
                             LEFT JOIN c_tax tax ON tax.C_Tax_ID = COALESCE(ic.C_Tax_Override_ID, ic.C_Tax_ID)
                    WHERE ic.c_invoice_candidate_id = p_Invoice_Candidate_ID)
SELECT ROUND(net + (net * tax_rate / 100), 2) AS grandtotal
FROM net_amount
$$
;
