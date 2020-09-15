--
-- fix edi_cctop_000_v_view's column
-- 2020-09-07T08:25:01.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=578054, ColumnName='EdiInvoicRecipientGLN', Description='', FieldLength=255, Help=NULL, IsUpdateable='N', Name='EDI-ID des INVOIC-Empfängers',Updated=TO_TIMESTAMP('2020-09-07 10:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548360
;

-- 2020-09-07T08:25:01.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='EDI-ID des INVOIC-Empfängers', Description='', Help=NULL WHERE AD_Column_ID=548360
;

-- 2020-09-07T08:25:01.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578054) 
;

--
-- make sure that ReceiverGLN is preferring EdiInvoicRecipientGLN
DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v;
CREATE OR REPLACE VIEW EDI_Cctop_INVOIC_v AS
SELECT
    i.C_Invoice_ID AS EDI_Cctop_INVOIC_v_ID
     , i.C_Invoice_ID
     , i.C_Order_ID
     , i.DocumentNo AS Invoice_DocumentNo
     , i.DateInvoiced
     , (CASE
            WHEN i.POReference::TEXT <> ''::TEXT AND i.POReference IS NOT NULL /* task 09182: if there is a POReference, then export it */
                THEN i.POReference
                ELSE NULL::CHARACTER VARYING
        END) AS POReference
     , (CASE
            WHEN COALESCE(i.DateOrdered, o.DateOrdered) IS NOT NULL /* task 09182: if there is an orderDate, then export it */
                THEN COALESCE(i.DateOrdered, o.DateOrdered)
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
     , CASE WHEN dt.DocSubType='CS' THEN NULL ELSE COALESCE(desadv.MovementDate, s.MovementDate) END AS MovementDate
     , CASE WHEN dt.DocSubType='CS' THEN NULL ELSE COALESCE(desadv.DocumentNo, s.DocumentNo) END AS Shipment_DocumentNo
     , t.TotalVAT
     , t.TotalTaxBaseAmt
     , COALESCE(rbp.EdiInvoicRecipientGLN, rl.GLN) AS ReceiverGLN
     , rl.C_BPartner_Location_ID
     , (
    SELECT DISTINCT ON (sl.GLN)
        sl.GLN
    FROM C_BPartner_Location sl
    WHERE true
      AND sl.C_BPartner_ID = sp.C_BPartner_ID
      AND sl.IsRemitTo = 'Y'::BPChar
      AND sl.GLN IS NOT NULL
      AND sl.IsActive = 'Y'::BPChar
) AS SenderGLN
     , sp.VATaxId
     , c.ISO_Code
     , i.CreditMemoReason
     , (
    SELECT
        Name
    FROM AD_Ref_List
    WHERE AD_Reference_ID=540014 -- C_CreditMemo_Reason
      AND Value=i.CreditMemoReason
) AS CreditMemoReasonText
     , cc.CountryCode
     , cc.CountryCode_3Digit
     , cc.CountryCode as AD_Language
     , i.AD_Client_ID , i.AD_Org_ID, i.Created, i.CreatedBy, i.Updated, i.UpdatedBy, i.IsActive
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
;
