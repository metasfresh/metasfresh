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

DROP FUNCTION IF EXISTS getCustoms_Invoice_Line_PriceActual (IN p_c_customs_invoice_line_id numeric)
;

CREATE OR REPLACE FUNCTION getCustoms_Invoice_Line_PriceActual(p_c_customs_invoice_line_id NUMERIC)
    RETURNS NUMERIC
    LANGUAGE sql
    STABLE
AS
$$
SELECT ol.priceactual
FROM M_InOutLine_To_C_Customs_Invoice_Line io_to_ci
         INNER JOIN m_inoutline il ON il.m_inoutline_id = io_to_ci.m_inoutline_id
         INNER JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id
WHERE c_customs_invoice_line_id = p_c_customs_invoice_line_id
    ;
$$
;
