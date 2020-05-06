-- View: EDI_Cctop_INVOIC_v

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
	, CASE WHEN dt.DocSubType='CS' THEN NULL ELSE s.MovementDate END AS MovementDate
	, CASE WHEN dt.DocSubType='CS' THEN NULL ELSE s.DocumentNo END AS Shipment_DocumentNo
	, t.TotalVAT
	, t.TotalTaxBaseAmt
	, rl.GLN AS ReceiverGLN
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
LEFT JOIN EDI_Desadv s ON s.EDI_Desadv_ID = o.EDI_Desadv_ID -- note that we don't use the M_InOut, but the desadv. there might be multiple InOuts, all with the same POReference and the same desadv_id
LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
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

