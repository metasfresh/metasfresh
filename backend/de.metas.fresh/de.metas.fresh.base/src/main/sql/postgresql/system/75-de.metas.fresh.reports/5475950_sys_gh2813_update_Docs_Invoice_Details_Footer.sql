DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Invoice_Details_Footer ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Invoice_Details_Footer ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
(
	descriptionbottom text,
	P_Cond text,
	p_term character varying(60),
	textcenter text,

	paymentterm character varying(60),
	discount1 numeric,
	discount2 numeric,
	discount_date1 text,
	discount_date2 text,
	cursymbol character varying(10)
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
	CASE WHEN (i.descriptionbottom IS NOT NULL AND i.descriptionbottom != '')
			THEN '<br><br><br>'
			ELSE ''
		END || COALESCE(dtt.documentnote, dt.documentnote) 	as textcenter,
	
	COALESCE(ptt.name, pt.name) as paymentterm,
	(CASE WHEN pt.DiscountDays > 0 THEN (i.grandtotal - (i.grandtotal * pt.discount / 100)) ELSE null END) AS discount1,
	(CASE WHEN pt.DiscountDays2 > 0 THEN (i.grandtotal - (i.grandtotal * pt.discount2 / 100)) ELSE null END) AS discount2,
	to_char((i.DateInvoiced + DiscountDays),'dd.MM.YYYY') AS discount_date1,
	to_char((i.DateInvoiced + DiscountDays2),'dd.MM.YYYY') AS discount_date2,
	c.cursymbol	
FROM
	C_Invoice i
	LEFT OUTER JOIN C_PaymentTerm pt on i.C_PaymentTerm_ID = pt.C_PaymentTerm_ID AND pt.isActive = 'Y'
	LEFT OUTER JOIN C_PaymentTerm_Trl ptt on i.C_PaymentTerm_ID = ptt.C_PaymentTerm_ID AND ptt.AD_Language = $2 AND ptt.isActive = 'Y'
	LEFT OUTER JOIN AD_Ref_List ref ON i.PaymentRule = ref.Value AND ref.AD_Reference_ID = 195 AND ref.isActive = 'Y'
	LEFT OUTER JOIN AD_Ref_List_Trl reft ON reft.AD_Ref_List_ID = ref.AD_Ref_List_ID AND reft.AD_Language = $2 AND reft.isActive = 'Y'
	LEFT OUTER JOIN c_doctype dt ON i.c_doctype_id = dt.c_doctype_id AND dt.isActive = 'Y'
	LEFT OUTER JOIN c_doctype_trl dtt ON dt.c_doctype_id = dtt.c_doctype_id AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
	INNER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
	
WHERE
	i.C_Invoice_ID = $1 AND i.isActive = 'Y'

$$
LANGUAGE sql STABLE;
