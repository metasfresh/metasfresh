DROP VIEW IF EXISTS historical_invoices_json_v
;

DROP VIEW IF EXISTS C_Invoice_Export_EDI_INVOIC_JSON_V
;

DROP VIEW IF EXISTS EDI_Cctop_119_v;
CREATE OR REPLACE VIEW EDI_Cctop_119_v AS
SELECT lookup.C_Invoice_ID       AS EDI_Cctop_119_v_ID,
       lookup.C_Invoice_ID,
       lookup.C_Invoice_ID       AS EDI_Cctop_INVOIC_v_ID,
       lookup.M_InOut_ID,
       pl.C_BPartner_Location_ID,
       REGEXP_REPLACE(pl.GLN, '\s+$', '') AS GLN,
       pl.Phone,
       pl.Fax,
       p.Name,
       p.Name2,
       p.Value,
       p.VATaxID,
       lookup.Vendor_ReferenceNo AS ReferenceNo,
       lookup.SiteName,
       lookup.Setup_Place_No,
       CASE lookup.Type_V
           WHEN 'ship'::TEXT THEN 'DP'::TEXT
           WHEN 'bill'::TEXT THEN 'IV'::TEXT
           WHEN 'cust'::TEXT THEN 'BY'::TEXT
           WHEN 'vend'::TEXT THEN 'SU'::TEXT
           WHEN 'snum'::TEXT THEN 'SN'::TEXT
                             ELSE 'Error EANCOM_Type'::TEXT
       END                       AS eancom_locationtype,
       l.Address1,
       l.Address2,
       l.Postal,
       l.City,
       c.CountryCode,
       l.AD_Client_ID,
       l.AD_Org_ID,
       l.Created,
       l.CreatedBy,
       l.Updated,
       l.UpdatedBy,
       l.IsActive,
       CASE lookup.Type_V WHEN 'vend'::text THEN u.Name END AS Contact
FROM (
         SELECT union_lookup.*
         FROM (
                  SELECT DISTINCT 1::INTEGER   AS SeqNo,
                                  'cust'::TEXT AS Type_V,
                                  pl_cust.C_BPartner_Location_ID,
                                  i.C_Invoice_ID,
                                  0::INTEGER   AS M_InOut_ID,
                                  NULL::TEXT   AS Vendor_ReferenceNo,
                                  pl_cust.bpartnername AS SiteName,
                                  pl_cust.Setup_Place_No,
                                  i.CreatedBy
                  FROM C_Invoice i
                           LEFT JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID
                           LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID = il.C_OrderLine_ID
                           LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
                           LEFT JOIN C_BPartner_Location pl_cust ON pl_cust.C_BPartner_Location_ID =
                                                                    (CASE
                                                                         WHEN o.C_BPartner_Location_ID IS NOT NULL AND o.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN o.C_BPartner_Location_ID
                                                                             ELSE -- Fallback if the C_Invoice is Hand Solo :) (I mean no C_Order)
                                                                             i.C_BPartner_Location_ID
                                                                     END)
                       --
                  UNION
                  --
                  SELECT 2::INTEGER         AS SeqNo,
                         'vend'::TEXT       AS Type_V,
                         COALESCE(pl_wh.C_BPartner_Location_ID, pl_vend.C_BPartner_Location_ID) AS C_BPartner_Location_ID,
                         i.C_Invoice_ID,
                         0::INTEGER         AS M_InOut_ID,
                         COALESCE(p_wh.ReferenceNo, p_vend.ReferenceNo)                         AS Vendor_ReferenceNo,
                         COALESCE(pl_wh.bpartnername, pl_vend.bpartnername)                     AS SiteName,
                         COALESCE(pl_wh.Setup_Place_No, pl_vend.Setup_Place_No)                 AS Setup_Place_No,
                         i.CreatedBy
                  FROM C_Invoice i
                           JOIN C_BPartner p_vend ON p_vend.AD_OrgBP_ID = i.AD_Org_ID
                           JOIN C_BPartner_Location pl_vend ON pl_vend.C_BPartner_ID = p_vend.C_BPartner_ID AND pl_vend.isremitto = 'Y'
                           LEFT JOIN m_inout inout ON inout.m_inout_id = i.m_inout_id
                           LEFT JOIN M_Warehouse wh ON wh.M_Warehouse_ID = inout.M_Warehouse_ID
                           LEFT JOIN C_BPartner p_wh ON p_wh.C_BPartner_ID = wh.C_BPartner_ID
                           LEFT JOIN C_BPartner_Location pl_wh ON pl_wh.C_BPartner_Location_ID = wh.C_BPartner_Location_ID
                       --
                  UNION
                  --
                  SELECT DISTINCT 3::INTEGER   AS SeqNo,
                                  'ship'::TEXT AS Type_V,
                                  pl_ship.c_bpartner_location_id,
                                  i.C_Invoice_ID,
                                  0::INTEGER   AS M_InOut_ID,
                                  NULL::TEXT   AS Vendor_ReferenceNo,
                                  pl_ship.bpartnername AS SiteName,
                                  pl_ship.Setup_Place_No,
                                  i.CreatedBy
                  FROM C_Invoice i
                           INNER JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID
                           LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID = il.C_OrderLine_ID
                           LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
                           LEFT JOIN M_InOutline sl ON ( -- try to join directly from C_InvoiceLine
                                                                   sl.M_InOutLine_ID = il.M_InOutLine_ID AND il.M_InOutLine_ID IS NOT NULL AND il.M_InOutLine_ID != 0)
                      -- fallback and try to join from C_OrderLine if (and only if) it's missing in C_InvoiceLine
                      OR (sl.C_OrderLine_ID = il.C_OrderLine_ID AND (il.M_InOutLine_ID IS NULL OR il.M_InOutLine_ID = 0))
                           LEFT JOIN M_InOut s ON s.M_InOut_ID = sl.M_InOut_ID

                           LEFT JOIN C_BPartner_Location pl_ship ON pl_ship.C_BPartner_Location_ID =
                                                                    (CASE -- Could use COALESCE(NULLIF(.., ..), ..) here, but it gets messy, so I prefer CASE
                                                                         WHEN o.HandOver_Location_ID IS NOT NULL AND o.HandOver_Location_ID != 0::INTEGER
                                                                             THEN o.HandOver_Location_ID
                                                                         WHEN s.C_BPartner_Location_ID IS NOT NULL AND s.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN s.C_BPartner_Location_ID
                                                                         WHEN o.DropShip_Location_ID IS NOT NULL AND o.DropShip_Location_ID != 0::INTEGER
                                                                             THEN o.DropShip_Location_ID
                                                                         WHEN o.C_BPartner_Location_ID IS NOT NULL AND o.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN o.C_BPartner_Location_ID
                                                                             ELSE -- Fallback if the C_Invoice is Hand Solo :) (I mean no C_Order)
                                                                             i.C_BPartner_Location_ID
                                                                     END)
                       --
                  UNION
                  --
                  SELECT 4::INTEGER   AS SeqNo,
                         'bill'::TEXT AS Type_V,
                         pl_bill.C_BPartner_Location_ID,
                         i.C_Invoice_ID,
                         0::INTEGER   AS M_InOut_ID,
                         NULL::TEXT   AS Vendor_ReferenceNo,
                         pl_bill.bpartnername AS SiteName,
                         pl_bill.Setup_Place_No,
                         i.CreatedBy
                  FROM C_Invoice i
                           LEFT JOIN C_BPartner_Location pl_bill ON pl_bill.C_BPartner_Location_ID = i.C_BPartner_Location_ID
                       --
                  UNION
                  --
                  SELECT DISTINCT 5::INTEGER   AS SeqNo,
                                  'snum'::TEXT AS Type_V,
                                  pl_snum.C_BPartner_Location_ID,
                                  i.C_Invoice_ID,
                                  s.M_InOut_ID,
                                  NULL::TEXT   AS Vendor_ReferenceNo,
                                  pl_snum.bpartnername AS SiteName,
                                  pl_snum.Setup_Place_No,
                                  i.CreatedBy
                  FROM C_Invoice i
                           INNER JOIN C_Invoiceline il ON il.C_Invoice_ID = i.C_Invoice_ID
                           LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID = il.C_OrderLine_ID
                           LEFT JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID
                           LEFT JOIN M_InOutline sl ON ( -- try to join directly from C_InvoiceLine
                                                                   sl.M_InOutLine_ID = il.M_InOutLine_ID AND il.M_InOutLine_ID IS NOT NULL AND il.M_InOutLine_ID != 0)
                      -- fallback and try to join from C_OrderLine if (and only if) it's missing in C_InvoiceLine
                      OR (sl.C_OrderLine_ID = il.C_OrderLine_ID AND (il.M_InOutLine_ID IS NULL OR il.M_InOutLine_ID = 0))
                           LEFT JOIN M_InOut s ON s.M_InOut_ID = sl.M_InOut_ID
                           LEFT JOIN C_BPartner_Location pl_snum ON pl_snum.C_BPartner_Location_ID =
                                                                    (CASE -- Could use COALESCE(NULLIF(.., ..), ..) here, but it gets messy, so I prefer CASE
                                                                         WHEN s.C_BPartner_Location_ID IS NOT NULL AND s.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN s.C_BPartner_Location_ID
                                                                         WHEN o.DropShip_Location_ID IS NOT NULL AND o.DropShip_Location_ID != 0::INTEGER
                                                                             THEN o.DropShip_Location_ID
                                                                         WHEN o.C_BPartner_Location_ID IS NOT NULL AND o.C_BPartner_Location_ID != 0::INTEGER
                                                                             THEN o.C_BPartner_Location_ID
                                                                             ELSE -- Fallback if the C_Invoice is Hand Solo :) (I mean no C_Order)
                                                                             i.C_BPartner_Location_ID
                                                                     END)
              ) union_lookup
         ORDER BY union_lookup.SeqNo, union_lookup.Type_V, union_lookup.C_BPartner_Location_ID, C_Invoice_ID, M_InOut_ID
     ) lookup
         LEFT JOIN C_BPartner_Location pl ON pl.C_BPartner_Location_ID = lookup.C_BPartner_Location_ID
         LEFT JOIN C_BPartner p ON p.C_BPartner_ID = pl.C_BPartner_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = pl.C_Location_ID
         LEFT JOIN C_Country c ON c.C_Country_ID = l.C_Country_ID
         LEFT JOIN AD_User u ON u.AD_User_ID = lookup.CreatedBy
WHERE TRUE
  --  AND p.VATaxID IS NOT NULL -- VATaxIDs are not needed in general, but only if the customer is in a different country or if the customer explicitly requests them to be in their INVOICs
  AND (l.Address1 IS NOT NULL OR l.Address2 IS NOT NULL)
ORDER BY (
          lookup.C_Invoice_ID,
          CASE lookup.Type_V
              WHEN 'ship'::TEXT THEN 'DP'::TEXT
              WHEN 'bill'::TEXT THEN 'IV'::TEXT
              WHEN 'cust'::TEXT THEN 'BY'::TEXT
              WHEN 'vend'::TEXT THEN 'SU'::TEXT
              WHEN 'snum'::TEXT THEN 'SN'::TEXT
                                ELSE 'Error EANCOM_Type'::TEXT
          END);

DROP VIEW IF EXISTS historical_invoices_json_v
;

CREATE OR REPLACE VIEW historical_invoices_json_v AS
SELECT invoic_v.c_invoice_id               AS "Invoice_ID",
       invoic_v.ReceiverGLN                AS "Invoice_Receiver_Tec_GLN",
       invoic_v.SenderGLN                  AS "Invoice_Sender_Tec_GLN",
       invoic_v.CountryCode                AS "Invoice_Sender_CountryCode",
       invoic_v.VATaxId                    AS "Invoice_Sender_VATaxId",
       invoic_v.Invoice_DocumentNo         AS "Invoice_DocumentNo",
       invoic_v.DateInvoiced               AS "Invoice_Date",
       invoic_v.DateAcct                   AS "Invoice_Acct_Date",
       invoic_v.docbasetype                AS "DocType_Base",
       invoic_v.docsubtype                 AS "DocType_Sub",
       invoic_v.CreditMemoReason           AS "CreditMemo_Reason",
       invoic_v.CreditMemoReasonText       AS "CreditMemo_ReasonText",
       invoic_v.POReference                AS "Order_POReference",
       invoic_v.DateOrdered                AS "Order_Date",
       invoic_v.MovementDate               AS "Shipment_Date",
       invoic_v.Shipment_DocumentNo        AS "Shipment_DocumentNo",
       invoic_v.EDIDesadvDocumentNo        AS "DESADV_DocumentNo",
       invoic_v.ISO_Code                   AS "Invoice_Currency_Code",
       invoic_v.GrandTotal                 AS "Invoice_GrandTotal",
       invoic_v.TotalLines                 AS "Invoice_TotalLines",
       invoic_v.TotalVAT                   AS "Invoice_TotalVAT",
       invoic_v.TotalTaxBaseAmt            AS "Invoice_TotalVATBaseAmt",
       invoic_v.SurchargeAmt               AS "Invoice_SurchargeAmt",
       invoic_v.TotalLinesWithSurchargeAmt AS "Invoice_TotalLinesWithSurchargeAmt",
       invoic_v.TotalVatWithSurchargeAmt   AS "Invoice_TotalVATWithSurchargeAmt",
       invoic_v.GrandTotalWithSurchargeAmt AS "Invoice_GrandTotalWithSurchargeAmt",
       invoic_v.updated::timestamp         AS "Updated",
       invoic_v.ExternalId                 AS "ExternalId",
       COALESCE(invoic_v.DataSource, '')   AS "DataSource",
       invoic_v.DocStatus                  AS "DocStatus",
       edi_119_v.json_data                 AS "Partners",
       edi_120_v.json_data                 AS "PaymentTerms",
       edi_140_v.json_data                 AS "PaymentDiscounts",
       edi_500_v.json_data                 AS "Lines",
       edi_901_991_v.json_data             AS "Sums"
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
                                               JSON_AGG(JSON_BUILD_OBJECT(
                                                       bpartner_attr_value.attr_value,
                                                       bpartner_attr_value.valuestring
                                                   )) AS json_data
                                        FROM edi_bpartner_attribute_valuestring_v bpartner_attr_value
                                        GROUP BY bpartner_value, ad_client_id, ad_org_id) attr_v
                                       ON attr_v.bpartner_value = cctop119.value
                                           AND attr_v.ad_client_id = cctop119.ad_client_id
                                           AND (cctop119.ad_org_id = 0 OR attr_v.ad_org_id = cctop119.ad_org_id)
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
                                            'Product_Name', v.name || v.name2,
                                            'Product_Description', v.productdescription,
                                            'Product_Buyer_CU_GTIN', v.Buyer_GTIN_CU,
                                            'Product_Buyer_TU_GTIN', v.Buyer_GTIN_TU,
                                            'Product_Buyer_ProductNo', v.CustomerProductNo,
                                            'Product_Supplier_TU_GTIN', v.Supplier_GTIN_CU,
                                            'Product_Supplier_ProductNo', v.Value,
                                            'ExternalId', v.ExternalId
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
ORDER BY invoic_v.updated, invoic_v.c_invoice_id
;

DROP VIEW IF EXISTS C_Invoice_Export_EDI_INVOIC_JSON_V
;

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
                                            'Product_Supplier_ProductNo', v.Value
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
