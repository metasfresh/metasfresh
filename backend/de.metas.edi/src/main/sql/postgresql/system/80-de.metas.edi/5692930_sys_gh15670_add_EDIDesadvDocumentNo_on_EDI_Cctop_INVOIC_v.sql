-- View: EDI_Cctop_INVOIC_v

DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v;
CREATE OR REPLACE VIEW EDI_Cctop_INVOIC_v AS
SELECT
    i.C_Invoice_ID AS EDI_Cctop_INVOIC_v_ID
     , i.C_Invoice_ID
     , i.C_Order_ID
     , REGEXP_REPLACE(i.DocumentNo, '\s+$', '') AS Invoice_DocumentNo
     , i.DateInvoiced
     , (CASE
            WHEN REGEXP_REPLACE(i.POReference::TEXT, '\s+$', '') <> ''::TEXT AND i.POReference IS NOT NULL /* task 09182: if there is a POReference, then export it */
                THEN REGEXP_REPLACE(i.POReference, '\s+$', '')
                ELSE NULL::CHARACTER VARYING
        END) AS POReference
     , (CASE
            WHEN COALESCE(i.DateOrdered, o.DateOrdered, ol.dateordered) IS NOT NULL /* task 09182: if there is an orderDate, then export it */
                THEN COALESCE(i.DateOrdered, o.DateOrdered, ol.dateordered)
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END) AS DateOrdered
     , (CASE dt.DocBaseType
            WHEN 'ARI'::BPChar THEN (CASE
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='' THEN '380'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='AQ' THEN '383'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='AP' THEN '84'::TEXT
                                         ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
            WHEN 'ARC'::BPChar THEN (CASE

                /* CQ => "GS - Lieferdifferenz"; CS => "GS - Retoure" */
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) IN ('CQ','CS') THEN '381'
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType)='CR' THEN '83'::TEXT
                                                                                                                        ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
                               ELSE 'ERROR EAN_DocType'::TEXT
        END) AS EANCOM_DocType
     , i.GrandTotal
     , i.TotalLines
    /* IF docSubType is CS, the we don't reference the original shipment*/
     , CASE WHEN dt.DocSubType='CS' THEN NULL ELSE COALESCE(desadv.MovementDate, s.MovementDate, iomd.movementdate) END AS MovementDate
     , CASE WHEN dt.DocSubType='CS' THEN NULL ELSE COALESCE(desadv.DocumentNo, s.DocumentNo, iodn.documentno) END AS Shipment_DocumentNo
     , t.TotalVAT
     , t.TotalTaxBaseAmt
     , COALESCE(rbp.EdiInvoicRecipientGLN, rl.GLN) AS ReceiverGLN
     , rl.C_BPartner_Location_ID
     , (
    SELECT DISTINCT ON (REGEXP_REPLACE(sl.GLN, '\s+$', ''))
        REGEXP_REPLACE(sl.GLN, '\s+$', '') as GLN
    FROM C_BPartner_Location sl
    WHERE true
      AND sl.C_BPartner_ID = sp.C_BPartner_ID
      AND sl.IsRemitTo = 'Y'::BPChar
      AND sl.GLN IS NOT NULL
      AND sl.IsActive = 'Y'::BPChar
) AS SenderGLN
     , REGEXP_REPLACE(sp.VATaxId, '\s+$', '') as VATaxId
     , c.ISO_Code
     , REGEXP_REPLACE(i.CreditMemoReason, '\s+$', '') as CreditMemoReason
     , (
    SELECT
        REGEXP_REPLACE(Name, '\s+$', '') as Name
    FROM AD_Ref_List
    WHERE AD_Reference_ID=540014 -- C_CreditMemo_Reason
      AND Value=i.CreditMemoReason
) AS CreditMemoReasonText
     , (select CASE
                   WHEN array_length(array_agg(DISTINCT ol.invoicableqtybasedon), 1) = 1
                       THEN (array_agg(DISTINCT ol.invoicableqtybasedon))[1]
                       ELSE NULL
               END
        from c_orderline ol
        where ol.c_order_id=o.c_order_id) as invoicableqtybasedon
     , cc.CountryCode
     , cc.CountryCode_3Digit
     , cc.CountryCode as AD_Language
     , i.AD_Client_ID , i.AD_Org_ID, i.Created, i.CreatedBy, i.Updated, i.UpdatedBy, i.IsActive
     , (select string_agg(edi.DocumentNo, ',') from c_invoiceline icl
                                                        inner join c_invoice_line_alloc inalloc on icl.c_invoiceline_id = inalloc.c_invoiceline_id
                                                        inner join C_InvoiceCandidate_InOutLine candinout
                                                                   on inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id
                                                        inner join M_InOutLine inoutline on candinout.m_inoutline_id = inoutline.m_inoutline_id
                                                        inner join m_inout inout on inoutline.m_inout_id = inout.m_inout_id
                                                        inner join EDI_Desadv edi on inout.EDI_Desadv_ID = edi.EDI_Desadv_ID
        where icl.c_invoice_id = i.c_invoice_id) as EDIDesadvDocumentNo

FROM C_Invoice i
         LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypetarget_ID
         LEFT JOIN C_Order o ON o.C_Order_ID=i.C_Order_ID
         LEFT JOIN EDI_Desadv desadv ON desadv.EDI_Desadv_ID = o.EDI_Desadv_ID -- note that we prefer the EDI_Desadv over M_InOut. there might be multiple InOuts, all with the same POReference and the same EDI_Desadv_ID
         LEFT JOIN LATERAL (
    SELECT
        io.DocumentNo, io.MovementDate
    FROM M_InOut io
    WHERE io.C_Order_ID=o.C_Order_ID AND io.DocStatus IN ('CO', 'CL')
    ORDER BY io.Created
    LIMIT 1
    ) s ON true -- for the case of missing EDI_Desadv, we still get the first M_InOut; DESADV can be switched off for individual C_BPartners
         LEFT JOIN C_BPartner rbp ON rbp.C_BPartner_ID = i.C_BPartner_ID
         LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
         LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
         LEFT JOIN C_Country cc ON cc.C_Country_ID = l.C_Country_ID
         LEFT JOIN (
    SELECT
        C_InvoiceTax.C_Invoice_ID
         , SUM(C_InvoiceTax.TaxAmt) AS TotalVAT
         , SUM(C_InvoiceTax.TaxBaseAmt) AS TotalTaxBaseAmt
    FROM C_InvoiceTax
    GROUP BY C_InvoiceTax.C_Invoice_ID
) t ON t.C_Invoice_ID = i.C_Invoice_ID
         LEFT JOIN C_BPartner sp ON sp.AD_OrgBP_ID = i.AD_Org_ID
         LEFT JOIN LATERAL ( --only add values if there is only one unique value
    SELECT i.c_invoice_id, min(o.dateordered) AS dateordered
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT ol.dateordered)=1
    ) ol ON ol.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(io.movementdate) AS movementdate
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
             INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
    GROUP BY i.c_invoice_id HAVING count(DISTINCT io.movementdate)=1
    ) iomd ON iomd.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(io.documentno) AS documentno
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
             INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
    GROUP BY i.c_invoice_id HAVING count(DISTINCT io.documentno)=1
    ) iodn ON iodn.c_invoice_id = i.c_invoice_id
;