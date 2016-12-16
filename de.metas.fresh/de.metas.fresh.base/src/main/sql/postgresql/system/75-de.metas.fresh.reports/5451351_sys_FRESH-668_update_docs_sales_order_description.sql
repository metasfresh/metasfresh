DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Description(IN record_id numeric, IN ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Description(IN record_id numeric, IN ad_language Character Varying (6))

RETURNS TABLE
(
	description character varying(1024),
	documentno character varying(30),
	dateordered timestamp without time zone,
	reference text,
	isoffer text,
	offervaliddate timestamp without time zone,
	offervaliddays numeric(10,0),
	bp_value character varying(40),
	cont_name text,
	cont_phone character varying(40),
	cont_fax character varying(40),
	cont_email character varying(60),
	sr_name text,
	sr_phone character varying(40),
	sr_fax character varying(40),
	sr_email character varying(60),
	printname character varying(60)
)
AS
$$
SELECT
	o.description 	as description,
	o.documentno 	as documentno,
	o.dateordered	as dateordered,
	o.poreference	as reference,
	CASE WHEN dt.docbasetype = 'SOO' AND dt.docsubtype IN ('ON', 'OB')
		THEN 'Y'
		ELSE 'N'
	END AS isoffer,
	o.offervaliddate,
	o.offervaliddays,
	bp.value	as bp_value,
	Coalesce(cogrt.name, cogrt.name, '') ||
	Coalesce(' ' || cont.title, '') ||
	Coalesce(' ' || cont.firstName, '') ||
	Coalesce(' ' || cont.lastName, '') as cont_name,
	cont.phone	as cont_phone,
	cont.fax	as cont_fax,
	cont.email	as cont_email,
	Coalesce(srgrt.name, srgr.name, '') ||
	Coalesce(' ' || srep.title, '') ||
	Coalesce(' ' || srep.firstName, '') ||
	Coalesce(' ' || srep.lastName, '') as sr_name,
	srep.phone	as sr_phone,
	srep.fax	as sr_fax,
	srep.email	as sr_email,
	COALESCE(dtt.PrintName, dt.PrintName) AS PrintName
FROM
	C_Order o
	INNER JOIN C_BPartner bp 		ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN AD_User srep		ON o.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100 AND srep.isActive = 'Y'
	LEFT OUTER JOIN AD_User cont		ON o.Bill_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt 		ON o.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt 	ON o.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'

	-- Translatables
	LEFT OUTER JOIN C_Greeting cogr	ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
	LEFT OUTER JOIN C_Greeting_Trl cogrt	ON cont.C_Greeting_ID = cogrt.C_Greeting_ID AND cogrt.ad_language = $2 AND cogrt.isActive = 'Y'
	LEFT OUTER JOIN C_Greeting srgr	ON srep.C_Greeting_ID = srgr.C_Greeting_ID AND srgr.isActive = 'Y'
	LEFT OUTER JOIN C_Greeting_Trl srgrt	ON srep.C_Greeting_ID = srgrt.C_Greeting_ID AND srgrt.ad_language = $2 AND srgrt.isActive = 'Y'
WHERE
	o.C_Order_ID = $1 AND o.isActive = 'Y'
$$
LANGUAGE sql STABLE
;