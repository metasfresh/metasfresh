drop view if exists report.Fresh_EDI_DesadvLine_SSCC_Label_Report;

create or replace view report.Fresh_EDI_DesadvLine_SSCC_Label_Report
as
select
	/*
	 * NOTE to developer: please keep in sync with *_HU_SSCC_Label_Report
	 */
	dl_sscc.IPA_SSCC18 AS SSCC,
	dl.ProductNo AS p_CustomerValue,
	NULL::numeric AS PriceActual,
	COALESCE( pt.name, p.name ) AS p_name,
	dl_sscc.QtyCU ::numeric AS CU_per_TU,
	dl_sscc.QtyTU :: numeric AS TU_per_LU,
	NULL::numeric AS Net_Weight,
	NULL::numeric AS Gross_Weight,
	o.DocumentNo AS order_docno,
	p.value AS P_Value,
	NULL::varchar AS LotCode,
	sscc18_extract_serialNumber(dl_sscc.IPA_SSCC18) AS PaletNo,
	COALESCE(bp.name, '') || ' ' || COALESCE(hol.name, bpl.name, '') || E'\n' AS Customer,
	-- Adding language. This language is used for the captions in the report 
	bp.ad_Language,
	--
	-- Filtering fields
	NULL::numeric AS M_HU_ID,
	--
	dl_sscc.EDI_DesadvLine_SSCC_ID,
	sel.AD_PInstance_ID
--
from T_Selection sel
INNER JOIN EDI_DesadvLine_SSCC dl_sscc on (dl_sscc.EDI_DesadvLine_SSCC_ID=sel.T_Selection_ID)
INNER JOIN EDI_DesadvLine dl on (dl.EDI_DesadvLine_ID=dl_sscc.EDI_DesadvLine_ID)
INNER JOIN M_Product p on (p.M_Product_ID=dl.M_Product_ID)
LEFT OUTER JOIN C_OrderLine ol on (ol.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID)
LEFT OUTER JOIN C_Order o on (o.C_Order_ID=ol.C_Order_ID)
INNER JOIN EDI_Desadv dh on (dh.EDI_Desadv_ID=dl.EDI_Desadv_ID)
INNER JOIN C_BPartner bp ON (bp.C_BPartner_ID = dh.C_BPartner_ID)
LEFT OUTER JOIN C_BPartner_Location hol ON (hol.C_BPartner_Location_ID = dh.HandOver_Location_ID)
LEFT OUTER JOIN C_BPartner_Location bpl ON (bpl.C_BPartner_Location_ID = dh.C_BPartner_Location_ID)
LEFT OUTER JOIN M_Product_Trl pt ON (p.M_Product_ID = pt.M_Product_ID AND bp.AD_Language = pt.AD_Language)
;

