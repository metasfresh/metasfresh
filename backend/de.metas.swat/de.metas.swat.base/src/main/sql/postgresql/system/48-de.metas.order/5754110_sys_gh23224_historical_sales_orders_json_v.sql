/*
 * #%L
 * de.metas.swat.base
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

DROP VIEW IF EXISTS historical_sales_orders_json_v
;

CREATE OR REPLACE VIEW historical_sales_orders_json_v AS
SELECT order_v.C_Order_ID           AS "Order_ID",
       order_v.Order_DocumentNo     AS "DocumentNo",
       order_v.POReference          AS "POReference",
       order_v.DateOrdered          AS "DateOrdered",
       order_v.DatePromised         AS "DatePromised",
       order_v.C_BPartner_ID        AS "Partner_ID",
       order_v.BPartnerValue        AS "Partner_Value",
       order_v.BPartnerName         AS "Partner_Name",
       order_v.BillBParterId        AS "Bill_Partner_ID",
       order_v.BillBParterValue     AS "Bill_Partner_Value",
       order_v.BillBParterName      AS "Bill_Partner_Name",
       order_v.HandoverBParterId    AS "Handover_Partner_ID",
       order_v.HandoverBParterValue AS "Handover_Partner_Value",
       order_v.HandoverBParterName  AS "Handover_Partner_Name",
       order_v.DropshipBParterId    AS "Dropship_Partner_ID",
       order_v.DropshipBParterValue AS "Dropship_Partner_Value",
       order_v.DropshipBParterName  AS "Dropship_Partner_Name",
       order_v.GrandTotal           AS "GrandTotal",
       order_v.PaymentTermName      AS "PaymentTerm",
       order_v.Currency             AS "Currency",
       lines.json_data              AS "Lines"
FROM Historical_Sales_Orders_V order_v
         LEFT JOIN (SELECT ol.c_order_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'Order_Line', ol.line,
                                            'OrderUOM', ouom.x12de355,
                                            'QtyOrdered_In_OrderUOM', ol.qtyentered,
                                            'StockUOM', suom.x12de355,
                                            'QtyOrdered_In_StockUOM', ol.qtyordered,
                                            'PriceEntered', ol.priceentered,
                                            'Discount', ol.discount,
                                            'PriceActual', ol.priceactual,
                                            'Price-UOM', puom.x12de355,
                                            'QtyOrdered_In_PriceUOM', ol.qtyenteredinpriceuom,
                                            'Catch-Or-Nominal', ol.invoicableqtybasedon,
                                            'TaxRate', tax.rate,
                                            'IsPackingMaterial', ol.ispackagingmaterial,
                                            'Product_Name', product.name,
                                            'Product_Value', product.value,
                                            'Product_ID', product.m_product_id
                                        ) ORDER BY ol.line) AS json_data
                    FROM c_orderline ol
                             LEFT JOIN m_product product ON product.m_product_id = ol.m_product_id
                             LEFT JOIN c_uom suom ON suom.c_uom_id = product.c_uom_id
                             LEFT JOIN c_uom ouom ON ouom.c_uom_id = ol.c_uom_id
                             LEFT JOIN c_uom puom ON puom.c_uom_id = ol.price_uom_id
                             LEFT JOIN c_tax tax ON tax.c_tax_id = ol.c_tax_id
                    GROUP BY ol.c_order_id) lines ON lines.c_order_id = order_v.C_Order_ID
ORDER BY DateOrdered
LIMIT 2000
;
