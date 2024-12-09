<<<<<<< HEAD
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
	LEFT JOIN C_DunningLevel_Trl dlt 	ON dd.C_Dunninglevel_ID = dlt.C_DunningLevel_ID AND dlt.ad_Language = $2 AND dlt.isActive = 'Y' AND dlt.isTranslated = 'Y'
	LEFT JOIN C_BPartner bp 		ON dd.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT JOIN AD_Org o 			ON dd.AD_Org_ID = o.AD_Org_ID AND o.isActive = 'Y'
	LEFT JOIN AD_OrgInfo inf		ON o.AD_Org_ID = inf.AD_Org_ID AND inf.isActive = 'Y'
	LEFT JOIN C_PaymentTerm p 		ON dl.C_PaymentTerm_ID = p.C_PaymentTerm_ID AND p.isActive = 'Y'
	LEFT JOIN C_PaymentTerm_Trl pt		ON dl.C_PaymentTerm_ID = pt.C_PaymentTerm_ID AND pt.ad_Language = $2 AND pt.isActive = 'Y' AND pt.isTranslated = 'Y'
WHERE
	dd.C_DunningDoc_ID = $1 AND dd.isActive = 'Y'
;
$$ 
LANGUAGE sql STABLE;

=======
DROP FUNCTION IF EXISTS report.Docs_Sales_Dunning_Report_description (IN p_record_id numeric,
                                                                      IN p_language  Character Varying(6))
;

CREATE FUNCTION report.Docs_Sales_Dunning_Report_description(p_record_id numeric,
                                                             p_language  character varying)
    RETURNS TABLE
            (
                doctype              character varying,
                docno                character varying,
                noteheader           character varying,
                note                 character varying,
                dunningdate          timestamp without time zone,
                bp_value             character varying,
                org_value            character varying,
                taxid                character varying,
                condition            character varying,
                Account_manager      character varying,
                Account_manager_mail character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(dlt.PrintName, dl.PrintName) AS DocType,
       dd.documentno                         AS docno,
       COALESCE(dlt.noteheader, dl.noteheader),
       COALESCE(dlt.note, dl.note),
       dd.dunningdate,
       bp.value                              AS bp_value,
       o.value                               AS org_value,
       inf.taxid,
       REPLACE(
               REPLACE(
                       REPLACE(
                               COALESCE(pt.name_invoice, pt.name),
                               '$datum_netto',
                               TO_CHAR(dd.dunningdate + p.netdays, 'DD.MM.YYYY')
                       ),
                       '$datum_skonto_1',
                       TO_CHAR(dd.dunningdate::date + p.discountdays, 'DD.MM.YYYY')
               ),
               '$datum_skonto_2',
               TO_CHAR(dd.dunningdate::date + p.discountdays2, 'DD.MM.YYYY')
       )                                     AS condition,
       usr.firstname || ' ' || usr.lastname  AS Account_manager,
       usr.email                             AS Account_manager_mail

FROM C_DunningDoc dd
         INNER JOIN C_DunningLevel dl ON dd.C_Dunninglevel_ID = dl.C_DunningLevel_ID
         LEFT OUTER JOIN C_DunningLevel_Trl dlt ON dd.C_Dunninglevel_ID = dlt.C_DunningLevel_ID AND dlt.ad_Language = p_language

         LEFT OUTER JOIN C_BPartner bp ON dd.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN AD_Org o ON dd.AD_Org_ID = o.AD_Org_ID
         LEFT OUTER JOIN AD_OrgInfo inf ON o.AD_Org_ID = inf.AD_Org_ID
         LEFT OUTER JOIN C_PaymentTerm p ON dl.C_PaymentTerm_ID = p.C_PaymentTerm_ID
         LEFT OUTER JOIN C_PaymentTerm_Trl pt ON dl.C_PaymentTerm_ID = pt.C_PaymentTerm_ID AND pt.ad_Language = p_language
         LEFT OUTER JOIN
     (SELECT inv.salesrep_id
      FROM c_dunningdoc dd
               INNER JOIN c_dunningdoc_line ddl ON dd.c_dunningdoc_id = ddl.c_dunningdoc_id
               INNER JOIN c_dunningdoc_line_source ddls ON ddl.c_dunningdoc_line_id = ddls.c_dunningdoc_line_id
               INNER JOIN c_dunning_candidate dc ON ddls.c_dunning_candidate_id = dc.c_dunning_candidate_id
               INNER JOIN c_invoice inv ON dc.record_id = inv.c_invoice_id AND dc.ad_table_id = get_table_id('C_Invoice')
      WHERE dd.c_dunningdoc_id = p_record_id) inv ON TRUE
         LEFT OUTER JOIN ad_user usr ON inv.salesrep_id = usr.ad_user_id
WHERE dd.C_DunningDoc_ID = p_record_id
  AND dd.isActive = 'Y'
    ;
$$
;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

