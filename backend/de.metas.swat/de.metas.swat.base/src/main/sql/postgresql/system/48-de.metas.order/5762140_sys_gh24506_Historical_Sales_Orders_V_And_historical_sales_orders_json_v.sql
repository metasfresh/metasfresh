DROP VIEW IF EXISTS historical_sales_orders_json_v
;

DROP VIEW IF EXISTS Historical_Sales_Orders_V
;

CREATE OR REPLACE VIEW Historical_Sales_Orders_V AS
SELECT o.C_Order_ID
     , REGEXP_REPLACE(o.DocumentNo, '\s+$', '') AS Order_DocumentNo
     , (CASE
            WHEN REGEXP_REPLACE(o.POReference::TEXT, '\s+$', '') <> ''::TEXT AND o.POReference IS NOT NULL
                THEN REGEXP_REPLACE(o.POReference, '\s+$', '')
                ELSE NULL::CHARACTER VARYING
        END)                                    AS POReference
     , (CASE
            WHEN o.DateOrdered IS NOT NULL
                THEN o.DateOrdered
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END)                                    AS DateOrdered
     , (CASE
            WHEN o.DatePromised IS NOT NULL
                THEN o.DatePromised
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END)                                    AS DatePromised
     , o.externalid                             AS ExternalId
     , (CASE
            WHEN dsource.internalname IS NOT NULL
                THEN 'int-' || dsource.internalname
        END)                                    AS DataSource
     , rbp.C_BPartner_ID
     , rbp.value                                AS BPartnerValue
     , rbp.name                                 AS BPartnerName
     , bbp.C_BPartner_ID                        AS BillBParterId
     , bbp.value                                AS BillBParterValue
     , bbp.name                                 AS BillBParterName
     , hbp.C_BPartner_ID                        AS HandoverBParterId
     , hbp.value                                AS HandoverBParterValue
     , hbp.name                                 AS HandoverBParterName
     , dbp.C_BPartner_ID                        AS DropshipBParterId
     , dbp.value                                AS DropshipBParterValue
     , dbp.name                                 AS DropshipBParterName
     , o.grandtotal                             AS GrandTotal
     , pterm.name                               AS PaymentTermName
     , c.iso_code                               AS Currency
FROM C_Order o
         INNER JOIN C_DocType dt ON dt.C_DocType_ID = o.C_DocTypetarget_ID
         LEFT JOIN C_BPartner rbp ON rbp.C_BPartner_ID = o.C_BPartner_ID
         LEFT JOIN C_BPartner bbp ON bbp.C_BPartner_ID = o.bill_bpartner_id
         LEFT JOIN C_BPartner hbp ON hbp.C_BPartner_ID = o.handover_partner_id
         LEFT JOIN C_BPartner dbp ON dbp.C_BPartner_ID = o.dropship_bpartner_id
         LEFT JOIN C_Currency c ON c.C_Currency_ID = o.C_Currency_ID
         LEFT JOIN C_PaymentTerm pterm ON pterm.c_paymentterm_id = o.c_paymentterm_id
         LEFT JOIN AD_InputDataSource dsource ON dsource.ad_inputdatasource_id = o.ad_inputdatasource_id
WHERE dt.docbasetype = 'SOO'
  AND dt.docsubtype = 'SO'
  AND o.DocStatus IN ('CO')
  AND o.issotrx = 'Y'
;

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
                                            'Product_ID', product.m_product_id
                                        ) ORDER BY ol.line) AS json_data
                    FROM c_orderline ol
                             LEFT JOIN m_product product ON product.m_product_id = ol.m_product_id
                             LEFT JOIN c_uom suom ON suom.c_uom_id = product.c_uom_id
                             LEFT JOIN c_uom ouom ON ouom.c_uom_id = ol.c_uom_id
                             LEFT JOIN c_uom puom ON puom.c_uom_id = ol.price_uom_id
                             LEFT JOIN c_tax tax ON tax.c_tax_id = ol.c_tax_id
                    GROUP BY ol.c_order_id) lines ON lines.c_order_id = order_v.C_Order_ID
ORDER BY DateOrdered, order_v.C_Order_ID
;
