---------------
--allow RM-credit memo in manual SO-invoices

-- 11.08.2015 07:55
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(	C_DocType.DocBaseType IN (''API'',''APC'')
	OR (C_DocType.DocBaseType IN (''ARC'',''ARI'') AND C_DocType.DocSubType IS NULL) /*only the default types*/
	OR (C_DocType.DocBaseType=''ARC'' AND C_DocType.DocSubType = ''CS'') /*only the RMA-credit-memo*/

) 
AND C_DocType.IsSOTrx=''@IsSOTrx@''', Description='Document Type AR/AP Invoice and Credit Memos, PLUS return material credit memo. Excludes other special-purpose invoice and credit memo types that are available via the "C_Invoice_Create CreditMemo" proc',Updated=TO_TIMESTAMP('2015-08-11 07:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540294
;

--------------
-- add OrderPOReference to the export-format and the cctop-500-view
-- 11.08.2015 12:17
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542872,0,'OrderPOReference',TO_TIMESTAMP('2015-08-11 12:17:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','Auftragsreferenz','Auftragsreferenz',TO_TIMESTAMP('2015-08-11 12:17:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.08.2015 12:17
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542872 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 11.08.2015 12:19
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552627,542872,0,10,540463,'N','OrderPOReference',TO_TIMESTAMP('2015-08-11 12:19:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',40,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Auftragsreferenz',0,TO_TIMESTAMP('2015-08-11 12:19:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 11.08.2015 12:19
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552627 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 11.08.2015 12:48
-- URL zum Konzept
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552627,0,TO_TIMESTAMP('2015-08-11 12:48:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550145,'Y','N','N','OrderPOReference',195,'E',TO_TIMESTAMP('2015-08-11 12:48:40','YYYY-MM-DD HH24:MI:SS'),100,'OrderPOReference')
;
------------------


COMMIT;


-- DDL
DROP VIEW IF EXISTS edi_cctop_invoic_500_v;
CREATE OR REPLACE VIEW edi_cctop_invoic_500_v AS 
SELECT 
	-- this aggregation is a workaround for the problem of sometimes having multiple invoice lines (within one invoice) for one EDI ORDERS line
	SUM(il.qtyentered) AS qtyinvoiced, -- we output the Qty in the customer's UOM (i.e. QtyEntered)
	MIN(il.c_invoiceline_id) AS edi_cctop_invoic_500_v_id, 
	SUM(il.linenetamt) AS linenetamt, 
	MIN(il.line) AS line,
	
	il.c_invoice_id, il.c_invoice_id AS edi_cctop_invoic_v_id, 
	il.priceactual, il.pricelist, 
	pp.upc, -- in the invoic the customer expects the CU-EANs, not the TU-EANs (so we don't select upc_TU here)
	p.value, 
	pp.productno AS vendorproductno, 
	substr(p.name::text, 1, 35) AS name, 
	substr(p.name::text, 36, 70) AS name2, 
	t.rate, 
    CASE
            WHEN u.x12de355::text = 'TU'::text THEN 'PCE'::character varying
            ELSE u.x12de355
    END AS eancom_uom, 
    CASE
            WHEN u_price.x12de355::text = 'TU'::text THEN 'PCE'::character varying
            ELSE u_price.x12de355
    END AS eancom_price_uom, 
    CASE
            WHEN t.rate = 0::numeric THEN 'Y'::text
            ELSE ''::text
    END AS taxfree, 
    c.iso_code, il.ad_client_id, il.ad_org_id, 
    MIN(il.created) AS created, 
    MIN(il.createdby)::numeric(10,0) AS createdBy, 
    MAX(il.updated) AS updated, 
    MAX(il.updatedby)::numeric(10,0) AS updatedby, 
    il.isactive, 
    CASE pc.value
            WHEN 'Leergut'::text THEN 'P'::text
            ELSE ''::text
    END AS leergut, 
    COALESCE(pp.productdescription, pp.description, p.description, p.name) AS productdescription, -- fallback from customer's description to our own description
    ol.line AS OrderLine,
    o.POReference AS OrderPOReference, -- task 09182
    il.C_OrderLine_ID, -- grouping by C_OrderLine_ID to make sure that we don't aggregate too much
    SUM(il.taxamtinfo) AS taxamtinfo
FROM c_invoiceline il
   LEFT JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id
	 LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id -- task 09182
	 LEFT JOIN c_currency c ON c.c_currency_id = ol.c_currency_id
   LEFT JOIN m_product p ON p.m_product_id = il.m_product_id
	 LEFT JOIN m_product_category pc ON pc.m_product_category_id = p.m_product_category_id
   LEFT JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
   LEFT JOIN c_bpartner_product pp ON pp.c_bpartner_id = i.c_bpartner_id AND pp.m_product_id = il.m_product_id
   LEFT JOIN c_tax t ON t.c_tax_id = il.c_tax_id
   LEFT JOIN c_uom u ON u.c_uom_id = il.c_uom_id -- do export the il's UOM because that's the UOM the customere ordered in (and it matches Qtyentered)
   LEFT JOIN c_uom u_price ON u_price.c_uom_id = il.price_uom_id
WHERE il.m_product_id IS NOT NULL
GROUP BY 
	il.c_invoice_id,
	il.priceactual, il.pricelist, 
	pp.upc,
	p.value, 
	pp.productno,
	substr(p.name::text, 1, 35), -- name
	substr(p.name::text, 36, 70), -- name2
	t.rate,
	eancom_uom, 
	eancom_price_uom,
	taxfree, c.iso_code, il.ad_client_id, il.ad_org_id, 
	il.isactive, 
	leergut, 
    COALESCE(pp.productdescription, pp.description, p.description, p.name), -- productdescription
	OrderPOReference,
	OrderLine,
	il.C_OrderLine_ID -- grouping by C_OrderLine_ID to make sure that we don't aggregate too much
ORDER BY ol.line
;
COMMENT ON VIEW edi_cctop_invoic_500_v IS 'Notes:
we output the Qty in the customer''s UOM (i.e. QtyEntered), but we call it QtyInved for historical reasons.
task 08878: Note: we try to aggregate ils which have the same order line. Grouping by C_OrderLine_ID to make sure that we don''t aggregate too much;
';

-- View: EDI_Cctop_INVOIC_v

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
		WHEN i.DateOrdered IS NOT NULL /* task 09182: if there is an orderDate, then export it */
			THEN i.DateOrdered
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
