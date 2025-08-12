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
       order_v.ExternalId           AS "ExternalId",
       order_v.DataSource           AS "DataSource",
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
                                            'Product_ID', product.m_product_id,
                                            'QtyTU', ol.qtyEnteredTU,
                                            'M_HU_PI_Item_Product_ID', ol.m_hu_pi_item_product_id,
                                            'Rendered PI Name', hupip.name
                                    ) ORDER BY ol.line) AS json_data
                    FROM c_orderline ol
                             LEFT JOIN m_product product ON product.m_product_id = ol.m_product_id
                             LEFT JOIN c_uom suom ON suom.c_uom_id = product.c_uom_id
                             LEFT JOIN c_uom ouom ON ouom.c_uom_id = ol.c_uom_id
                             LEFT JOIN c_uom puom ON puom.c_uom_id = ol.price_uom_id
                             LEFT JOIN c_tax tax ON tax.c_tax_id = ol.c_tax_id
                             LEFT JOIN M_HU_PI_Item_Product hupip ON ol.m_hu_pi_item_product_id = hupip.m_hu_pi_item_product_id
                    GROUP BY ol.c_order_id) lines ON lines.c_order_id = order_v.C_Order_ID

ORDER BY DateOrdered, order_v.C_Order_ID
;
