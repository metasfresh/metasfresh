
select db_alter_view('edi_cctop_901_991_v','
SELECT i.c_invoice_id                                                                                       AS edi_cctop_901_991_v_id,
       i.c_invoice_id,
       i.c_invoice_id                                                                                       AS edi_cctop_invoic_v_id,
       SUM(it.taxamt + it.taxbaseamt)                                                                       AS totalamt,
       SUM(it.taxamt)                                                                                       AS taxamt,
       SUM(it.taxbaseamt)                                                                                   AS taxbaseamt,
       t.rate /* we support the case of having two different C_Tax_IDs with the same tax-rate */,
       i.ad_client_id,
       i.ad_org_id,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.isactive,
       REGEXP_REPLACE(rn.referenceno, ''\s+$'', '''')                                                           AS ESRReferenceNumber,
       ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2)                                                 AS SurchargeAmt, -- may be be positve or negative
       ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2) + SUM(it.TaxBaseAmt)                            AS TaxBaseAmtWithSurchargeAmt,
       ROUND((ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2) + SUM(it.TaxBaseAmt)) * t.rate / 100, 2) AS TaxAmtWithSurchargeAmt
FROM c_invoice i
         LEFT JOIN c_invoicetax it ON it.c_invoice_id = i.c_invoice_id
         LEFT JOIN c_tax t ON t.c_tax_id = it.c_tax_id
         LEFT JOIN (SELECT sit.c_invoice_id,
                           COUNT(DISTINCT st.rate
                           ) AS tax_count
                    FROM c_invoicetax sit
                             LEFT JOIN c_tax st ON st.c_tax_id = sit.c_tax_id
                    GROUP BY sit.c_invoice_id) tc ON tc.c_invoice_id = it.c_invoice_id
         LEFT JOIN c_referenceno_doc rnd ON rnd.record_id = i.c_invoice_id AND rnd.ad_table_id = 318 /* C_Invoice DocumentType */
         LEFT JOIN c_referenceno rn ON rn.c_referenceno_id = rnd.c_referenceno_id
         LEFT JOIN C_PaymentTerm pterm ON i.c_paymentterm_id = pterm.c_paymentterm_id
WHERE it.IsActive = ''Y''
  AND tc.tax_count > 0
  -- can either be an ESRReferenceNumber, or we may not have it at all. Regardless, both cases should work.
  AND (rn.c_referenceno_type_id = 540005 OR rnd.c_referenceno_doc_id IS NULL
    ) /* c_referenceno_type_id = 540005 (ESRReferenceNumber) */
GROUP BY i.c_invoice_id, i.ad_client_id,
         t.rate,
         pterm.discount,
         i.ad_org_id,
         i.created,
         i.createdby,
         i.updated,
         i.updatedby,
         i.isactive,
         rn.referenceno
;                                           
');



-- View: EDI_Cctop_INVOIC_v

DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v
;

CREATE OR REPLACE VIEW EDI_Cctop_INVOIC_v AS
SELECT i.C_Invoice_ID                                                          AS EDI_Cctop_INVOIC_v_ID
     , i.C_Invoice_ID
     , i.C_Order_ID
     , REGEXP_REPLACE(i.DocumentNo, '\s+$', '')                                AS Invoice_DocumentNo
     , i.DateInvoiced
     , (CASE
            WHEN REGEXP_REPLACE(i.POReference::TEXT, '\s+$', '') <> ''::TEXT AND i.POReference IS NOT NULL /* task 09182: if there is a POReference, then export it */
                THEN REGEXP_REPLACE(i.POReference, '\s+$', '')
                ELSE NULL::CHARACTER VARYING
        END)                                                                   AS POReference
     , (CASE
            WHEN COALESCE(i.DateOrdered, o.DateOrdered) IS NOT NULL /* task 09182: if there is an orderDate, then export it */
                THEN COALESCE(i.DateOrdered, o.DateOrdered)
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END)                                                                   AS DateOrdered
     , (CASE dt.DocBaseType
            WHEN 'ARI'::BPChar THEN (CASE
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = ''   THEN '380'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AQ' THEN '383'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AP' THEN '84'::TEXT
                                                                                                                ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
            WHEN 'ARC'::BPChar THEN (CASE

                /* CQ => "GS - Lieferdifferenz"; CS => "GS - Retoure" */
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) IN ('CQ', 'CS') THEN '381'
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'CR'          THEN '83'::TEXT
                                                                                                                         ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
                               ELSE 'ERROR EAN_DocType'::TEXT
        END)                                                                   AS EANCOM_DocType
     , i.GrandTotal
     , i.TotalLines
    /* IF docSubType is CS, the we don't reference the original shipment*/
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE shipment.MovementDate END AS MovementDate
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE shipment.DocumentNo END   AS Shipment_DocumentNo
     , taxAndSurchage.TotalVAT
     , taxAndSurchage.TotalTaxBaseAmt
     , COALESCE(rbp.EdiInvoicRecipientGLN, rl.GLN)                             AS ReceiverGLN
     , rl.C_BPartner_Location_ID
     , (SELECT DISTINCT ON (REGEXP_REPLACE(sl.GLN, '\s+$', '')) REGEXP_REPLACE(sl.GLN, '\s+$', '') AS GLN
        FROM C_BPartner_Location sl
        WHERE TRUE
          AND sl.C_BPartner_ID = sp.C_BPartner_ID
          AND sl.IsRemitTo = 'Y'::BPChar
          AND sl.GLN IS NOT NULL
          AND sl.IsActive = 'Y'::BPChar)                                       AS SenderGLN
     , REGEXP_REPLACE(sp.VATaxId, '\s+$', '')                                  AS VATaxId
     , c.ISO_Code
     , REGEXP_REPLACE(i.CreditMemoReason, '\s+$', '')                          AS CreditMemoReason
     , (SELECT REGEXP_REPLACE(Name, '\s+$', '') AS Name
        FROM AD_Ref_List
        WHERE AD_Reference_ID = 540014 -- C_CreditMemo_Reason
          AND Value = i.CreditMemoReason)                                      AS CreditMemoReasonText
     , (SELECT CASE
                   WHEN ARRAY_LENGTH(ARRAY_AGG(DISTINCT ol.invoicableqtybasedon), 1) = 1
                       THEN (ARRAY_AGG(DISTINCT ol.invoicableqtybasedon))[1]
               END
        FROM c_orderline ol
        WHERE ol.c_order_id = o.c_order_id)                                    AS invoicableqtybasedon
     , cc.CountryCode
     , cc.CountryCode_3Digit
     , cc.CountryCode                                                          AS AD_Language
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
        WHERE icl.c_invoice_id = i.c_invoice_id)                               AS EDIDesadvDocumentNo
     , taxAndSurchage.SurchargeAmt
     , taxAndSurchage.TotalLinesWithSurchargeAmt
     , taxAndSurchage.TotalVatWithSurchargeAmt
     , taxAndSurchage.GrandTotalWithSurchargeAmt
FROM C_Invoice i
         LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypetarget_ID
         LEFT JOIN C_Order o ON o.C_Order_ID = i.C_Order_ID
         LEFT JOIN EDI_Desadv desadv ON desadv.EDI_Desadv_ID = o.EDI_Desadv_ID -- note that we prefer the EDI_Desadv over M_InOut. there might be multiple InOuts, all with the same POReference and the same EDI_Desadv_ID
         LEFT JOIN LATERAL ( SELECT io.DocumentNo,
                                    io.MovementDate
                             FROM M_InOut io
                             WHERE io.C_Order_ID = o.C_Order_ID
                               AND io.DocStatus IN ('CO', 'CL')
                             ORDER BY io.Created
                             LIMIT 1 ) shipment ON TRUE -- for the case of missing EDI_Desadv, we still get the first M_InOut; DESADV can be switched off for individual C_BPartners
         LEFT JOIN C_BPartner rbp ON rbp.C_BPartner_ID = i.C_BPartner_ID
         LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
         LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
         LEFT JOIN C_Country cc ON cc.C_Country_ID = l.C_Country_ID
         LEFT JOIN C_BPartner sp ON sp.AD_OrgBP_ID = i.AD_Org_ID
         LEFT JOIN LATERAL (SELECT C_Invoice_ID
                                 , SUM(TaxAmt)                                              AS TotalVAT
                                 , SUM(TaxBaseAmt)                                          AS TotalTaxBaseAmt
                                 , SUM(SurchargeAmt)                                        AS SurchargeAmt
                                 , SUM(TaxBaseAmtWithSurchargeAmt)                          AS TotalLinesWithSurchargeAmt
                                 , SUM(TaxAmtWithSurchargeAmt)                              AS TotalVatWithSurchargeAmt
                                 , SUM(TaxBaseAmtWithSurchargeAmt + TaxAmtWithSurchargeAmt) AS GrandTotalWithSurchargeAmt
                            FROM edi_cctop_901_991_v
                            WHERE c_invoice_id = i.c_invoice_id
                            GROUP BY C_Invoice_ID) taxAndSurchage ON TRUE
;

