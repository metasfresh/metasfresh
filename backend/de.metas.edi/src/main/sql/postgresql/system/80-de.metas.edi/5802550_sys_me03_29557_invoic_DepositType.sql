-- me03#29557: Surface M_Product.DepositType in INVOIC JSON export.
-- Adds Product_DepositType column to edi_cctop_invoic_500_v (inner view)
-- and exposes it as 'Product_DepositType' JSON key in C_Invoice_Export_EDI_INVOIC_JSON_V (outer view).

-- Drop the outer JSON view first (it depends on the inner view)
DROP VIEW IF EXISTS C_Invoice_Export_EDI_INVOIC_JSON_V
;

-- Recreate the inner view with the new Product_DepositType column
DROP VIEW IF EXISTS edi_cctop_invoic_500_v$new;

CREATE OR REPLACE VIEW edi_cctop_invoic_500_v$new AS
SELECT SUM(il.qtyEntered)                                                        AS QtyInvoiced,
       CASE
           WHEN u.x12de355 = 'TU' THEN 'PCE'
                                  ELSE u.x12de355
       END                                                                       AS eancom_uom,
       SUM(CASE
               WHEN u_ordered.x12de355 IN ('TU', 'COLI') THEN CEIL(il.QtyInvoiced / GREATEST(ol.QtyItemCapacity, 1))
                                                         ELSE uomconvert(il.M_Product_ID, p.C_UOM_ID, u_ordered.C_UOM_ID, il.QtyInvoiced)
           END)                                                                  AS QtyInvoicedInOrderedUOM,
       u_ordered.x12de355                                                        AS eancom_ordered_uom,
       MIN(il.c_invoiceline_id)                                                  AS edi_cctop_invoic_500_v_id,
       SUM(il.linenetamt)                                                        AS linenetamt,
       MIN(il.line)                                                              AS line,
       il.c_invoice_id,
       il.c_invoice_id                                                           AS edi_cctop_invoic_v_id,
       il.priceactual,
       il.pricelist,
       il.discount,
       ol.invoicableqtybasedon,
       REGEXP_REPLACE(asi_data.UPC, '\s+$', '')                                    AS UPC_CU,
       REGEXP_REPLACE(asi_data.EAN_CU, '\s+$', '')                               AS EAN_CU,
       REGEXP_REPLACE(p.value, '\s+$', '')                                       AS Value,
       p.DepositType                                                             AS Product_DepositType,
       REGEXP_REPLACE(asi_data.productno, '\s+$', '')                              AS CustomerProductNo,
       SUBSTR(p.name, 1, 35)                                                     AS name,
       SUBSTR(p.name, 36, 70)                                                    AS name2,
       t.rate,
       CASE
           WHEN COALESCE(u_price.x12de355, u.x12de355) = 'TU' THEN 'PCE'
                                                              ELSE COALESCE(u_price.x12de355, u.x12de355)
       END                                                                       AS eancom_price_uom,
       CASE
           WHEN t.rate = 0 THEN 'Y'
                           ELSE ''
       END                                                                       AS taxfree,
       t.IsTaxExempt,
       c.iso_code,
       il.ad_client_id,
       il.ad_org_id,
       MIN(il.created)                                                           AS created,
       MIN(il.createdby)::numeric(10, 0)                                         AS createdby,
       MAX(il.updated)                                                           AS updated,
       MAX(il.updatedby)::numeric(10, 0)                                         AS updatedby,
       il.isactive,
       CASE pc.value
           WHEN 'Leergut' THEN 'P'
                          ELSE ''
       END                                                                       AS leergut,
       COALESCE(NULLIF(asi_data.productdescription, ''), NULLIF(asi_data.productname, ''), NULLIF(p.description, ''),
                p.name)::character varying                                       AS productdescription,
       COALESCE(ol.line, il.line)                                                AS orderline,
       COALESCE(NULLIF(o.poreference, ''), i.poreference)::character varying(40) AS orderporeference,
       il.c_orderline_id,
       SUM(il.taxamtinfo)                                                        AS taxamtinfo,
       REGEXP_REPLACE(pip.GTIN::text, '\s+$'::text, ''::text)                    AS GTIN,
       REGEXP_REPLACE(pip.EAN_TU::text, '\s+$'::text, ''::text)                  AS EAN_TU,
       REGEXP_REPLACE(pip.UPC::text, '\s+$'::text, ''::text)                     AS UPC_TU,
       REGEXP_REPLACE(pip.GTIN::text, '\s+$'::text, ''::text)                    AS Buyer_GTIN_TU,
       COALESCE(
               NULLIF(REGEXP_REPLACE(asi_data.GTIN::text, '\s+$'::text, ''::text), ''::text),
               REGEXP_REPLACE(p.GTIN::text, '\s+$'::text, ''::text)
       )                                                                         AS Buyer_GTIN_CU,
       REGEXP_REPLACE(asi_data.EAN_CU::text, '\s+$'::text, ''::text)             AS Buyer_EAN_CU,
       REGEXP_REPLACE(p.GTIN::text, '\s+$'::text, ''::text)                      AS Supplier_GTIN_CU,
       SUM(il.QtyEnteredInBPartnerUOM)                                           AS qtyEnteredInBPartnerUOM,
       il.C_UOM_BPartner_ID                                                      AS C_UOM_BPartner_ID,
       il.externalids                                                            AS ExternalId,
       ol.externalseqno                                                          AS externalSeqNo
FROM c_invoiceline il
         LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id AND ol.isactive = 'Y'
         LEFT JOIN m_hu_pi_item_product pip ON COALESCE(ol.m_hu_pi_item_product_id, il.m_hu_pi_item_product_id) = pip.m_hu_pi_item_product_id
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         LEFT JOIN c_uom u_ordered ON u_ordered.c_uom_id = COALESCE(ol.c_uom_id, il.c_uom_id)
         LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
         LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
         LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
         LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
         LEFT JOIN LATERAL (
             SELECT gtin, ean_cu, upc, productno, productdescription, productname
             FROM m_product_asi_data
             WHERE isactive = 'Y'
               AND m_product_id = il.m_product_id
               AND (c_bpartner_id IS NULL OR c_bpartner_id = i.c_bpartner_id)
               AND IsASIAttributesKeySubset(m_attributesetinstance_id, il.m_attributesetinstance_id)
             ORDER BY seqno
             LIMIT 1
         ) asi_data ON TRUE
         LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
         LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id
         LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
WHERE il.m_product_id IS NOT NULL
  AND il.isactive = 'Y'
  AND il.qtyentered <> 0
GROUP BY il.c_invoice_id,
         il.priceactual,
         il.pricelist,
         il.discount,
         ol.InvoicableQtyBasedOn,
         asi_data.UPC,
         asi_data.EAN_CU,
         p.value,
         p.DepositType,
         asi_data.productno,
         (SUBSTR(p.name, 1, 35)),
         (SUBSTR(p.name, 36, 70)),
         t.rate,
         t.IsTaxExempt,
         (CASE
              WHEN u.x12de355 = 'TU' THEN 'PCE'
                                     ELSE u.x12de355
          END),
         (CASE
              WHEN COALESCE(u_price.x12de355, u.x12de355) = 'TU' THEN 'PCE'
                                                                 ELSE COALESCE(u_price.x12de355, u.x12de355)
          END),
         u_ordered.x12de355,
         (CASE
              WHEN t.rate = 0 THEN 'Y'
                              ELSE ''
          END),
         c.iso_code,
         il.ad_client_id,
         il.ad_org_id,
         il.isactive,
         (CASE pc.value
              WHEN 'Leergut' THEN 'P'
                             ELSE ''
          END),
         (COALESCE(NULLIF(asi_data.productdescription, ''), NULLIF(asi_data.productname, ''), NULLIF(p.description, ''), p.name)),
         (COALESCE(NULLIF(o.poreference, ''), i.poreference)),
         (COALESCE(ol.line, il.line)),
         il.c_orderline_id,
         pip.UPC, pip.GTIN, pip.EAN_TU, asi_data.GTIN, asi_data.EAN_CU, p.GTIN,
         il.C_UOM_BPartner_ID, il.externalids, ol.externalseqno
ORDER BY COALESCE(ol.line, il.line)
;

SELECT db_alter_view(
    'edi_cctop_invoic_500_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('edi_cctop_invoic_500_v$new'))
);

DROP VIEW IF EXISTS edi_cctop_invoic_500_v$new;

COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInvoiced for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
task 09182: in OrderPOReference and OrderLine we show reference and line for the original order, but fall back to the invoice''s own reference and line if there is no order(line).
'
;

-- Recreate the outer JSON view, adding 'Product_DepositType' to the per-line JSON object
CREATE OR REPLACE VIEW C_Invoice_Export_EDI_INVOIC_JSON_V AS
SELECT invoic_v.c_invoice_id,
       JSON_BUILD_OBJECT(
               'metasfresh_INVOIC', JSON_AGG(
               JSON_BUILD_OBJECT(
                       'Invoice_ID', invoic_v.c_invoice_id,
                       'Invoice_Receiver_Tec_GLN', invoic_v.ReceiverGLN,
                       'Invoice_Sender_Tec_GLN', invoic_v.SenderGLN,
                       'Invoice_Sender_CountryCode', invoic_v.CountryCode,
                       'Invoice_Sender_VATaxId', invoic_v.VATaxId,
                       'Invoice_DocumentNo', invoic_v.Invoice_DocumentNo,
                       'Invoice_Date', invoic_v.DateInvoiced,
                       'Invoice_Acct_Date', invoic_v.DateAcct,
                   -- doctype-related
                       'DocType_Base', invoic_v.docbasetype,
                       'DocType_Sub', invoic_v.docsubtype,
                       'CreditMemo_Reason', invoic_v.CreditMemoReason,
                       'CreditMemo_ReasonText', invoic_v.CreditMemoReasonText,
                   -- order
                       'Order_POReference', invoic_v.POReference,
                       'Order_Date', invoic_v.DateOrdered,
                   -- shipment
                       'Shipment_Date', invoic_v.MovementDate,
                       'Shipment_DocumentNo', invoic_v.Shipment_DocumentNo,
                       'DESADV_DocumentNo', invoic_v.EDIDesadvDocumentNo,
                   -- money
                       'Invoice_Currency_Code', invoic_v.ISO_Code,
                       'Invoice_GrandTotal', invoic_v.GrandTotal,
                       'Invoice_TotalLines', invoic_v.TotalLines,
                   -- tax
                       'Invoice_TotalVAT', invoic_v.TotalVAT,
                       'Invoice_TotalVATBaseAmt', invoic_v.TotalTaxBaseAmt,
                   -- surcharge
                       'Invoice_SurchargeAmt', invoic_v.SurchargeAmt,
                       'Invoice_TotalLinesWithSurchargeAmt', invoic_v.TotalLinesWithSurchargeAmt,
                       'Invoice_TotalVATWithSurchargeAmt', invoic_v.TotalVatWithSurchargeAmt,
                       'Invoice_GrandTotalWithSurchargeAmt', invoic_v.GrandTotalWithSurchargeAmt,
                       'Partners', edi_119_v.json_data,
                       'PaymentTerms', edi_120_v.json_data,
                       'PaymentDiscounts', edi_140_v.json_data,
                       'Lines', edi_500_v.json_data,
                       'Sums', edi_901_991_v.json_data,
                       'Version', '0.2'
                   )
           )
           ) AS embedded_json
FROM edi_cctop_invoic_v invoic_v
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                   'EANCOM_LocationType', eancom_locationtype,
                                   'GLN', GLN,
                                   'Name', Name,
                                   'Name2', Name2,
                                   'PartnerNo', Value,
                                   'VATaxID', VATaxID,
                                   'ReferenceNo', ReferenceNo,
                                   'SiteName', SiteName,
                                   'Setup_Place_No', Setup_Place_No,
                                   'Address1', Address1,
                                   'Address2', Address2,
                                   'Postal', Postal,
                                   'City', City,
                                   'CountryCode', CountryCode,
                                   'Phone', Phone,
                                   'Fax', Fax,
                                   'CustomEdiAttributes', attr_v.json_data
                               )) AS json_data
                    FROM edi_cctop_119_v cctop119
                             LEFT JOIN (SELECT bpartner_value,
                                               ad_client_id,
                                               ad_org_id,
                                               JSON_AGG(JSON_BUILD_OBJECT(bpartner_attr_value.attr_value, bpartner_attr_value.valuestring
                                                   )) AS json_data
                                        FROM edi_bpartner_attribute_valuestring_v bpartner_attr_value
                                        GROUP BY bpartner_value, ad_client_id, ad_org_id) attr_v ON attr_v.bpartner_value = cctop119.value AND attr_v.ad_client_id = cctop119.ad_client_id AND (cctop119.ad_org_id = 0 OR attr_v.ad_org_id = cctop119.ad_org_id)
                    GROUP BY c_invoice_id) edi_119_v ON edi_119_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                   'Net_Days', NetDays
                               )) AS json_data
                    FROM edi_cctop_120_v
                    GROUP BY c_invoice_id) edi_120_v ON edi_120_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                   'Discount_Name', Name,
                                   'Tax_Percent', Rate,
                                   'Discount_Days', discountdays,
                                   'Discount_Percent', Discount,
                                   'Discount_BaseAmt', DiscountBaseAmt,
                                   'Discount_Amt', DiscountAmt
                               )) AS json_data
                    FROM edi_cctop_140_v
                    GROUP BY c_invoice_id) edi_140_v ON edi_140_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'Invoice_Line', v.line,
                                            'Invoice_QtyInvoiced', v.QtyInvoiced,
                                            'Invoice_QtyInvoiced_UOM', v.eancom_uom,
                                            'ORDERS_Line', v.externalseqno,
                                            'ORDERS_QtyInvoiced', v.qtyEnteredInBPartnerUOM,
                                            'ORDERS_QtyInvoiced_UOM', uom.x12de355,
                                            'Order_POReference', v.orderporeference,
                                            'Order_Line', v.orderline,
                                            'Order_QtyInvoiced', v.QtyInvoicedInOrderedUOM,
                                            'Order_QtyInvoiced_UOM', v.eancom_ordered_uom,
                                            'Currency_Code', v.iso_code,
                                            'PricePerUnit', v.priceactual,
                                            'PriceUOM', v.eancom_price_uom,
                                            'Discount_Amt', v.discount,
                                            'QtyBasedOn', v.invoicableqtybasedon,
                                            'NetAmt', v.linenetamt,
                                            'Tax_Percent', v.rate,
                                            'Tax_Amount', v.taxamtinfo,
                                        -- product
                                            'Product_Name', v.name || v.name2,
                                            'Product_Description', v.productdescription,
                                            'Product_Buyer_CU_GTIN', v.Buyer_GTIN_CU,
                                            'Product_Buyer_TU_GTIN', v.Buyer_GTIN_TU,
                                            'Product_Buyer_ProductNo', v.CustomerProductNo,
                                            'Product_Supplier_TU_GTIN', v.Supplier_GTIN_CU,
                                            'Product_Supplier_ProductNo', v.Value,
                                            'Product_DepositType', v.Product_DepositType
                                        ) ORDER BY v.line) AS json_data
                    FROM edi_cctop_invoic_500_v v
                             LEFT JOIN c_uom uom ON uom.c_uom_id = v.C_UOM_BPartner_ID
                    GROUP BY c_invoice_id) edi_500_v ON edi_500_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'TotalAmt', TotalAmt,
                                            'Tax_Amt', TaxAmt,
                                            'Tax_BaseAmt', TaxBaseAmt,
                                            'Tax_Percent', Rate,
                                            'Tax_Exempt', IsTaxExempt = 'Y',
                                            'SurchargeAmt', SurchargeAmt,
                                            'Tax_BaseAmtWithSurchargeAmt', TaxBaseAmtWithSurchargeAmt,
                                            'Tax_AmtWithSurchargeAmt', TaxAmtWithSurchargeAmt
                                        ) ORDER BY rate DESC) AS json_data
                    FROM edi_cctop_901_991_v
                    GROUP BY c_invoice_id) edi_901_991_v ON edi_901_991_v.c_invoice_id = invoic_v.c_invoice_id
GROUP BY invoic_v.c_invoice_id
;
