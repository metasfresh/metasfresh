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

-- View: public.edi_cctop_invoic_500_v

DROP VIEW IF EXISTS public.edi_cctop_invoic_500_v
;

CREATE OR REPLACE VIEW edi_cctop_invoic_500_v AS
SELECT qtyinvoiced, eancom_uom, qtyinvoicedinordereduom, eancom_ordered_uom, edi_cctop_invoic_500_v_id, linenetamt,
         line, c_invoice_id, edi_cctop_invoic_v_id, priceactual, pricelist, discount, invoicableqtybasedon, upc_cu,
         ean_cu, value, product_deposittype, customerproductno, name, name2, rate, eancom_price_uom, taxfree,
         istaxexempt, iso_code, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive, leergut,
         productdescription, orderline, orderporeference, c_orderline_id, taxamtinfo, gtin, ean_tu, upc_tu,
         buyer_gtin_tu, buyer_gtin_cu, buyer_ean_cu, supplier_gtin_cu, qtyenteredinbpartneruom, c_uom_bpartner_id,
         externalid, externalseqno
FROM edi_cctop_invoic_500_incl_packaging_v
WHERE IsPackagingMaterial = 'N'
ORDER BY orderline
;

COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
Packaging-material lines are excluded here (legacy XML export, historical invoices JSON); the INVOIC JSON reads edi_cctop_invoic_500_incl_packaging_v.
'
;
