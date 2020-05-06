DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Description ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Description ( IN C_Invoice_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE 
	(description character varying(255),
	documentno character varying(30),
	reference character varying(40),
	dateinvoiced timestamp without time zone,
	VATaxID character varying(60),
	bp_value character varying(40),
	cont_name text,
	cont_phone character varying(40),
	cont_fax character varying(40),
	cont_email character varying(60),
	sr_name text,
	sr_phone character varying(40),
	sr_fax character varying(40),
	sr_email character varying(60),
	PrintName character varying(60)
	)
AS
$$	
SELECT
	i.description 	as description,
	i.documentno 	as documentno,
	i.poreference	as reference,
	i.dateinvoiced	as dateinvoiced,
	bp.VATaxID,
	bp.value	as bp_value,
	Coalesce(cogr.name, '')||
	Coalesce(' ' || cont.title, '') ||
	Coalesce(' ' || cont.firstName, '') ||
	Coalesce(' ' || cont.lastName, '') as cont_name,
	cont.phone	as cont_phone,
	cont.fax	as cont_fax,
	cont.email	as cont_email,
	Coalesce(srgr.name, '')||
	Coalesce(' ' || srep.title, '') ||
	Coalesce(' ' || srep.firstName , '')||
	Coalesce(' ' || srep.lastName, '') as sr_name,
	srep.phone	as sr_phone,
	srep.fax	as sr_fax,
	srep.email	as sr_email,
	COALESCE(dtt.PrintName, dt.PrintName) AS PrintName
FROM
	C_Invoice i
	JOIN C_BPartner bp 		ON i.C_BPartner_ID = bp.C_BPartner_ID
	LEFT JOIN AD_User srep	ON i.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID<>100
	LEFT JOIN AD_User cont	ON i.AD_User_ID = cont.AD_User_ID
	LEFT JOIN C_Greeting cogr	ON cont.C_Greeting_ID = cogr.C_Greeting_ID
	LEFT JOIN C_Greeting srgr	ON srep.C_Greeting_ID = srgr.C_Greeting_ID
	LEFT OUTER JOIN C_DocType dt ON i.C_DocTypeTarget_ID = dt.C_DocType_ID
	LEFT OUTER JOIN C_DocType_Trl dtt ON i.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2

WHERE
	i.C_Invoice_ID = $1

$$
LANGUAGE sql STABLE	
;