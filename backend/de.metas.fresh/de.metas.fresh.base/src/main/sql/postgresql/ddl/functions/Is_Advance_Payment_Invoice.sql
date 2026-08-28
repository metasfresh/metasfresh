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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Is_Advance_Payment_Invoice(p_c_invoice_id numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Is_Advance_Payment_Invoice(p_c_invoice_id numeric)
    RETURNS char(1)
    LANGUAGE sql
    STABLE
AS
$func$
WITH related_orders AS (SELECT DISTINCT o.c_order_id, COALESCE(o.c_doctype_id, o.c_doctypetarget_id) AS c_doctype_id
                        FROM c_invoiceline il
                                 JOIN c_order o ON o.c_order_id = il.c_order_id
                        WHERE il.c_invoice_id = p_c_invoice_id
                          AND il.isactive = 'Y'
                        UNION
                        SELECT o.c_order_id, COALESCE(o.c_doctype_id, o.c_doctypetarget_id)
                        FROM c_invoice i
                                 JOIN c_order o ON o.c_order_id = i.c_order_id
                        WHERE i.c_invoice_id = p_c_invoice_id),
     flagged AS (SELECT COALESCE(dt.docsubtype = 'PR' AND dt.docbasetype = 'SOO', FALSE) AS is_prepay
                 FROM related_orders ro
                          LEFT JOIN c_doctype dt ON dt.c_doctype_id = ro.c_doctype_id)
SELECT CASE
           WHEN EXISTS (SELECT 1 FROM flagged)
               AND NOT EXISTS (SELECT 1 FROM flagged WHERE is_prepay = FALSE)
               THEN 'Y'
               ELSE 'N'
       END;
$func$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.Is_Advance_Payment_Invoice(numeric) IS
    'Returns ''Y'' when the invoice has at least one related sales order and every related order is a Vorauskasse/prepayment order (C_DocType.DocSubType=''PR'' AND DocBaseType=''SOO''), otherwise ''N''. Related orders are resolved via C_InvoiceLine.C_OrderLine_ID -> C_OrderLine -> C_Order plus the header C_Invoice.C_Order_ID.'
;
