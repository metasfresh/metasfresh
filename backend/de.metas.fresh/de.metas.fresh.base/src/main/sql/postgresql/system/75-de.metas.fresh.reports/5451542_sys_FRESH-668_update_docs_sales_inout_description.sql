
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description 
(
	Description Character Varying (255),
	DocumentNo Character Varying (30),
	MovementDate Timestamp Without Time Zone,
	Reference Character Varying (40),
	BP_Value Character Varying (40),
	Cont_Name Character Varying (40),
	Cont_Phone Character Varying (40),
	Cont_Fax Character Varying (40),
	Cont_Email Character Varying (60),
	SR_Name Text,
	SR_Phone Character Varying (40),
	SR_Fax Character Varying (40),
	SR_Email Character Varying (60),
	PrintName Character Varying (60)
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Description AS
$$
SELECT
	io.description 	as description,
	io.documentno 	as documentno,
	io.movementdate,
	io.poreference	as reference,
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
	Coalesce(' ' || srep.firstName, '') ||
	Coalesce(' ' || srep.lastName, '') as sr_name,
	srep.phone	as sr_phone,
	srep.fax	as sr_fax,
	srep.email	as sr_email,
	COALESCE ( dtt.printname, dt.printname ) AS printname
FROM
	m_inout io
	INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
	INNER JOIN c_bpartner bp ON io.c_bpartner_id	= bp.c_bpartner_id AND bp.isActive = 'Y'
	LEFT OUTER JOIN AD_User srep ON io.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID<>100 AND srep.isActive = 'Y'
	LEFT OUTER JOIN AD_User cont ON io.AD_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
	LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
	LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID AND srgr.isActive = 'Y'
WHERE
	io.m_inout_id = $1 AND io.isActive = 'Y'
$$
LANGUAGE sql STABLE
;