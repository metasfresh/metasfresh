DROP VIEW IF EXISTS C_Invoice_Export_EDI_INVOIC_JSON_V;
DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v;

CREATE OR REPLACE VIEW EDI_Cctop_INVOIC_v AS
SELECT i.C_Invoice_ID                                                                                       AS EDI_Cctop_INVOIC_v_ID
     , i.C_Invoice_ID
     , i.C_Order_ID
     , REGEXP_REPLACE(i.DocumentNo, '\s+$', '')                                                             AS Invoice_DocumentNo
     , i.DateInvoiced
     , i.DateAcct
     , (CASE
            WHEN REGEXP_REPLACE(i.POReference::TEXT, '\s+$', '') <> ''::TEXT AND i.POReference IS NOT NULL /* task 09182: if there is a POReference, then export it */
                THEN REGEXP_REPLACE(i.POReference, '\s+$', '')
                ELSE NULL::CHARACTER VARYING
        END)                                                                                                AS POReference
     , (CASE
            WHEN COALESCE(i.DateOrdered, o.DateOrdered, ol.dateordered) IS NOT NULL /* task 09182: if there is an orderDate, then export it */
                THEN COALESCE(i.DateOrdered, o.DateOrdered, ol.dateordered)
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END)                                                                                                AS DateOrdered
     , dt.docbasetype
     , dt.docsubtype
     , (CASE dt.DocBaseType
            WHEN 'ARI'::BPChar THEN (CASE
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = ''   THEN '380'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AQ' THEN '383'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AP' THEN '84'::TEXT
                                                                                                                ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
            WHEN 'ARC'::BPChar THEN (CASE
                /* CQ => "GS - Delivery-Difference"; CS => "GS - Retoure"; CR => Price-Difference */
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) IN ('CQ', 'CS') THEN '381'
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'CR'          THEN '83'
                                                                                                                         ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
                               ELSE 'ERROR EAN_DocType'::TEXT
        END)                                                                                                AS EANCOM_DocType
     , i.GrandTotal
     , i.TotalLines
    /* IF docSubType is CS, the we don't reference the original shipment*/
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE COALESCE(shipment.MovementDate, iomd.movementdate) END AS MovementDate
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE COALESCE(shipment.DocumentNo, iodn.documentno) END     AS Shipment_DocumentNo
     , taxAndSurchage.TotalVAT
     , taxAndSurchage.TotalTaxBaseAmt
     , COALESCE(rbp.EdiInvoicRecipientGLN, rl.GLN)                                                          AS ReceiverGLN
     , rl.C_BPartner_Location_ID
     , (SELECT DISTINCT ON (REGEXP_REPLACE(sl.GLN, '\s+$', '')) REGEXP_REPLACE(sl.GLN, '\s+$', '') AS GLN
        FROM C_BPartner_Location sl
        WHERE TRUE
          AND sl.C_BPartner_ID = sp.C_BPartner_ID
          AND sl.IsRemitTo = 'Y'::BPChar
          AND sl.GLN IS NOT NULL
          AND sl.IsActive = 'Y'::BPChar)                                                                    AS SenderGLN
     , REGEXP_REPLACE(sp.VATaxId, '\s+$', '')                                                               AS VATaxId
     , c.ISO_Code
     , REGEXP_REPLACE(i.CreditMemoReason, '\s+$', '')                                                       AS CreditMemoReason
     , (SELECT REGEXP_REPLACE(Name, '\s+$', '') AS Name
        FROM AD_Ref_List
        WHERE AD_Reference_ID = 540014 -- C_CreditMemo_Reason
          AND Value = i.CreditMemoReason)                                                                   AS CreditMemoReasonText
     , (SELECT CASE
                   WHEN ARRAY_LENGTH(ARRAY_AGG(DISTINCT ol.invoicableqtybasedon), 1) = 1
                       THEN (ARRAY_AGG(DISTINCT ol.invoicableqtybasedon))[1]
                       ELSE NULL
               END
        FROM c_orderline ol
        WHERE ol.c_order_id = o.c_order_id)                                                                 AS invoicableqtybasedon
     , cc.CountryCode
     , cc.CountryCode_3Digit
     , cc.CountryCode                                                                                       AS AD_Language
     , i.AD_Client_ID
     , i.AD_Org_ID
     , i.Created
     , i.CreatedBy
     , i.Updated
     , i.UpdatedBy
     , i.IsActive
     , (SELECT STRING_AGG(DISTINCT edi.DocumentNo, ',')
        FROM c_invoiceline icl
                 INNER JOIN c_invoice_line_alloc inalloc ON icl.c_invoiceline_id = inalloc.c_invoiceline_id
                 INNER JOIN C_InvoiceCandidate_InOutLine candinout
                            ON inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id
                 INNER JOIN M_InOutLine inoutline ON candinout.m_inoutline_id = inoutline.m_inoutline_id
                 INNER JOIN m_inout inout ON inoutline.m_inout_id = inout.m_inout_id
                 INNER JOIN EDI_Desadv edi ON inout.EDI_Desadv_ID = edi.EDI_Desadv_ID
        WHERE icl.c_invoice_id = i.c_invoice_id)                                                            AS EDIDesadvDocumentNo
     , taxAndSurchage.SurchargeAmt
     , taxAndSurchage.TotalLinesWithSurchargeAmt
     , taxAndSurchage.TotalVatWithSurchargeAmt
     , taxAndSurchage.GrandTotalWithSurchargeAmt
FROM C_Invoice i
         LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypetarget_ID
         LEFT JOIN C_Order o ON o.C_Order_ID = i.C_Order_ID
         LEFT JOIN LATERAL ( SELECT io.DocumentNo,
                                    io.MovementDate
                             FROM M_InOut io
                                      LEFT JOIN c_invoice inv ON io.m_inout_id = inv.m_inout_id AND inv.c_invoice_id = i.c_invoice_id
                             WHERE io.C_Order_ID = o.C_Order_ID
                               AND io.DocStatus IN ('CO', 'CL')
                             ORDER BY inv.c_invoice_id NULLS LAST, io.Created
                             LIMIT 1 ) shipment ON TRUE -- for the case of missing EDI_Desadv, we still get the first M_InOut; DESADV can be switched off for individual C_BPartners
         LEFT JOIN C_BPartner rbp ON rbp.C_BPartner_ID = i.C_BPartner_ID
         LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
         LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
         LEFT JOIN C_Country cc ON cc.C_Country_ID = l.C_Country_ID
         LEFT JOIN C_BPartner sp ON sp.AD_OrgBP_ID = i.AD_Org_ID
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(o.dateordered) AS dateordered --only add values if there is only one unique value
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT ol.dateordered) = 1 ) ol ON ol.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(io.movementdate) AS movementdate
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                                      INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT io.movementdate) = 1 ) iomd ON iomd.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(io.documentno) AS documentno
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                                      INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT io.documentno) = 1 ) iodn ON iodn.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT C_Invoice_ID
                                  , SUM(TaxAmt)                                              AS TotalVAT
                                  , SUM(TaxBaseAmt)                                          AS TotalTaxBaseAmt
                                  , SUM(SurchargeAmt)                                        AS SurchargeAmt
                                  , SUM(TaxBaseAmtWithSurchargeAmt)                          AS TotalLinesWithSurchargeAmt
                                  , SUM(TaxAmtWithSurchargeAmt)                              AS TotalVatWithSurchargeAmt
                                  , SUM(TaxBaseAmtWithSurchargeAmt + TaxAmtWithSurchargeAmt) AS GrandTotalWithSurchargeAmt
                             FROM edi_cctop_901_991_v
                             WHERE c_invoice_id = i.c_invoice_id
                             GROUP BY C_Invoice_ID) taxAndSurchage ON TRUE;

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
                       'Sums', edi_901_991_v.json_data
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
                                   'Fax', Fax
                                    )) AS json_data
                    FROM edi_cctop_119_v
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
