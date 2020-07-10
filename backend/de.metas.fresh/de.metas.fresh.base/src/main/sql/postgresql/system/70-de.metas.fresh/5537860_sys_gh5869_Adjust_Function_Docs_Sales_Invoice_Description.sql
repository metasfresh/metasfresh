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
	PrintName character varying(60),
	order_docno text,
	inout_docno text,
	io_movementdate date,
	isCreditMemo char(1),
	creditmemo_docNo character varying(30)
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
	COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
	o.docno AS order_docno,
	io.docno AS inout_docno,
	io.DateFrom AS io_movementdate,
	CASE WHEN dt.docbasetype = 'ARC'
		THEN 'Y'
		ELSE 'N'
	END AS isCreditMemo,
	cm.documentno as creditmemo_docNo
FROM
	C_Invoice i
	JOIN C_BPartner bp 		ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT JOIN AD_User srep	ON i.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID<>100 AND srep.isActive = 'Y'
	LEFT JOIN AD_User cont	ON i.AD_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
	LEFT JOIN C_Greeting cogr	ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
	LEFT JOIN C_Greeting srgr	ON srep.C_Greeting_ID = srgr.C_Greeting_ID AND srgr.isActive = 'Y'
	LEFT JOIN C_Invoice cm on cm.C_Invoice_id = i.ref_invoice_id
	LEFT OUTER JOIN C_DocType dt ON i.C_DocTypeTarget_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON i.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
	LEFT JOIN LATERAL 
	(
		SELECT 
		First_Agg ( o.DocumentNo ORDER BY o.DocumentNo ) ||
				CASE WHEN Count( o.documentNo ) > 1 THEN ' ff.' ELSE '' END AS DocNo
		FROM C_InvoiceLine il
		JOIN C_OrderLine ol ON il.C_OrderLine_ID = ol.C_OrderLine_ID
		JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
		
		WHERE il.C_Invoice_ID = $1
	) o ON TRUE
	
	LEFT JOIN LATERAL 
	(
		SELECT 
			First_Agg ( io.DocumentNo ORDER BY io.DocumentNo ) ||
				CASE WHEN Count( io.documentNo ) > 1 THEN ' ff.' ELSE '' END AS DocNo,
			Min ( io.MovementDate )::date AS DateFrom
		FROM C_InvoiceLine il
		JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID
		JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
		
		WHERE il.C_Invoice_ID = $1
	) io ON TRUE
	

WHERE
	i.C_Invoice_ID = $1 AND i.isActive = 'Y'

$$
LANGUAGE sql STABLE	
;