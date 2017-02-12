DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Invoice_Details_Footer ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Invoice_Details_Footer ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
(
	descriptionbottom text,
	P_Cond text,
	p_term character varying(60),
	textcenter text
)
AS
$$
SELECT
	i.descriptionbottom,
	replace(
		replace(
			replace(
				COALESCE( ptt.name_invoice, ptt.name, pt.name_invoice, pt.name ),
				'$datum_netto',
				to_char(i.dateinvoiced + pt.netdays, 'DD.MM.YYYY')
			),
			'$datum_skonto_1',
			to_char(i.dateinvoiced::date + pt.discountdays, 'DD.MM.YYYY')
		),
		'$datum_skonto_2',
		to_char(i.dateinvoiced::date + pt.discountdays2, 'DD.MM.YYYY')
	) AS P_Cond,
	COALESCE( reft.name, ref.name ) AS P_Term,
	CASE WHEN i.descriptionbottom IS NOT NULL
			THEN '<br><br><br>'
			ELSE ''
		END || COALESCE(dtt.documentnote, dt.documentnote) 	as textcenter
FROM
	C_Invoice i
	LEFT OUTER JOIN C_PaymentTerm pt on i.C_PaymentTerm_ID = pt.C_PaymentTerm_ID
	LEFT OUTER JOIN C_PaymentTerm_Trl ptt on i.C_PaymentTerm_ID = ptt.C_PaymentTerm_ID AND ptt.AD_Language = $2
	LEFT OUTER JOIN AD_Ref_List ref ON i.PaymentRule = ref.Value AND ref.AD_Reference_ID = 195
	LEFT OUTER JOIN AD_Ref_List_Trl reft ON reft.AD_Ref_List_ID = ref.AD_Ref_List_ID AND reft.AD_Language = $2
	LEFT OUTER JOIN c_doctype dt ON i.c_doctype_id = dt.c_doctype_id
	LEFT OUTER JOIN c_doctype_trl dtt ON dt.c_doctype_id = dtt.c_doctype_id AND dtt.AD_Language = $2
	
WHERE
	i.C_Invoice_ID = $1

$$
LANGUAGE sql STABLE;
