
DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_description ( IN Record_ID numeric, IN AD_Language Character Varying (6)) ;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_description ( IN Record_ID numeric, IN AD_Language Character Varying (6))
	RETURNS TABLE ( 
		doctype character varying,
		docno character varying, 
		noteheader  character varying,
		note  character varying,
		dunningdate timestamp,
		bp_value character varying,
		org_value character varying,
		taxid character varying,
		condition character varying
	) AS 
$$
SELECT
	COALESCE(dlt.PrintName, dl.PrintName) AS DocType,
	dd.documentno as docno,
	COALESCE(dlt.noteheader, dl.noteheader),
	COALESCE(dlt.note, dl.note),
	dd.dunningdate,
	bp.value as bp_value,
	o.value as org_value,
	inf.taxid,
	replace(
		replace(
			replace(
				COALESCE(pt.name_invoice, pt.name),
				'$datum_netto',
				to_char(dd.dunningdate + p.netdays, 'DD.MM.YYYY')
			),
			'$datum_skonto_1',
			to_char(dd.dunningdate::date + p.discountdays, 'DD.MM.YYYY')
		),
		'$datum_skonto_2',
		to_char(dd.dunningdate::date + p.discountdays2, 'DD.MM.YYYY')
	) as condition

FROM
	C_DunningDoc dd
	JOIN C_DunningLevel dl		ON dd.C_Dunninglevel_ID = dl.C_DunningLevel_ID AND dl.isActive = 'Y'
	LEFT JOIN C_DunningLevel_Trl dlt 	ON dd.C_Dunninglevel_ID = dlt.C_DunningLevel_ID AND dlt.ad_Language = $2 AND dlt.isActive = 'Y'
	LEFT JOIN C_BPartner bp 		ON dd.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT JOIN AD_Org o 			ON dd.AD_Org_ID = o.AD_Org_ID AND o.isActive = 'Y'
	LEFT JOIN AD_OrgInfo inf		ON o.AD_Org_ID = inf.AD_Org_ID AND inf.isActive = 'Y'
	LEFT JOIN C_PaymentTerm p 		ON dl.C_PaymentTerm_ID = p.C_PaymentTerm_ID AND p.isActive = 'Y'
	LEFT JOIN C_PaymentTerm_Trl pt		ON dl.C_PaymentTerm_ID = pt.C_PaymentTerm_ID AND pt.ad_Language = $2 AND pt.isActive = 'Y'
WHERE
	dd.C_DunningDoc_ID = $1 AND dd.isActive = 'Y'
;
$$ 
LANGUAGE sql STABLE;


