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
DROP FUNCTION IF EXISTS get_Customs_Invoice_Payment_Data(p_C_Customs_Invoice_ID numeric)
;

CREATE OR REPLACE FUNCTION get_Customs_Invoice_Payment_Data(p_C_Customs_Invoice_ID numeric)
    RETURNS TABLE
            (
                C_PaymentTerm_ID numeric,
                C_Incoterms_ID   numeric
            )
    LANGUAGE sql
    STABLE
AS
$$
SELECT MIN(o.C_PaymentTerm_ID) AS C_PaymentTerm_ID,
       MIN(o.C_Incoterms_ID)   AS C_Incoterms_ID
FROM m_inoutline_to_c_customs_invoice_line io_to_ci
         INNER JOIN m_inout io ON io_to_ci.M_InOut_ID = io.M_InOut_ID
         INNER JOIN c_order o ON io.C_Order_ID = o.C_Order_ID
WHERE io_to_ci.C_Customs_Invoice_ID = p_C_Customs_Invoice_ID;
$$
;
