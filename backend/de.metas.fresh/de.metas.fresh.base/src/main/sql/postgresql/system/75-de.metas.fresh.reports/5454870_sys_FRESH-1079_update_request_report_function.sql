--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.request_report(IN ad_org_id numeric, IN R_RequestType_ID numeric, IN StartDate Date, IN EndDate Date, IN C_BPartner_ID numeric, IN QualityNote numeric, IN performanceType character varying(50), IN isMaterialReturned character(1),IN R_Resolution_ID numeric, IN R_Status_ID numeric, IN M_Product_ID numeric);

--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.request_report(IN ad_org_id numeric, IN R_RequestType_ID numeric, IN StartDate Date, IN EndDate Date, IN C_BPartner_ID numeric, IN QualityNote numeric, IN performanceType character varying(50), IN isMaterialReturned character(1), IN R_Status_ID numeric, IN M_Product_ID numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.request_report(
	IN ad_org_id numeric, --$1
	IN R_RequestType_ID numeric, --$2
	IN StartDate Date, --$3
	IN EndDate Date, --$4
	IN C_BPartner_ID numeric, --$5
	IN QualityNote numeric, --$6
	IN performanceType character varying(50), --$7
	IN isMaterialReturned character(1), --$8
	IN R_Status_ID numeric, --$9
	IN M_Product_ID numeric --$10
	)

RETURNS TABLE
(
	--data
	Date date,
	BP_Value character varying(40),
	BP_Name character varying(60),
	IO_Docno character varying(30),
	P_Value character varying(40),
	P_Name character varying(255),
	QualityNote character varying(250),
	Performance character varying(50),
	isMaterialReturned character(1),
	lastresult character varying(200),
	Status character varying(60),
	--parameters
	p_doctype character varying,
	p_startdate text,
	p_enddate text,
	p_bpartner character varying,
	p_qualitynote character varying,
	p_performancetype character varying,
	p_ismaterialreturned character,
	p_status character varying,
	p_product character varying
)
AS
$$

SELECT 
	--data
	req.DateDelivered::Date as Date, 
	bp.Value AS BP_Value, 
	bp.Name AS BP_Name,
	First_agg ( DISTINCT io.DocumentNo ORDER BY io.DocumentNo ) ||
				CASE WHEN Count(DISTINCT io.documentNo) > 1 THEN ' ff.' ELSE '' END AS IO_Docno,
	p.Value AS P_Value,
	p.Name AS P_Name,
	qn.Name AS QualityNote,
	req.performanceType AS Performance,
	req.isMaterialReturned AS isMaterialReturned,
	req.LastResult As lastresult,
	r.Name AS Status,
	--parameters
	(SELECT Name FROM R_RequestType WHERE R_RequestType_ID = $2 AND isActive = 'Y') AS p_doctype,
	to_char($3, 'DD.MM.YYYY') AS p_startdate,
	to_char($4, 'DD.MM.YYYY') AS p_enddate,

	(SELECT Name FROM C_BPartner WHERE C_BPartner_ID = $5 AND isActive = 'Y') AS p_bpartner,
	(SELECT Name FROM M_QualityNote WHERE M_QualityNote_ID = $6 AND isActive = 'Y') AS p_qualitynote,
	$7 AS p_performancetype,
	$8 AS p_ismaterialreturned,
	(SELECT Name FROM R_Status WHERE R_Status_ID = $9 AND isActive = 'Y') AS p_status,
	(SELECT Name FROM M_Product WHERE M_Product_ID = $10 AND isActive = 'Y') AS p_product
	

FROM R_Request req

LEFT OUTER JOIN C_BPartner bp ON req.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
LEFT OUTER JOIN M_InOut io ON req.Record_ID = io.M_InOut_ID AND io.isActive = 'Y'
LEFT OUTER JOIN M_Product p ON req.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

LEFT OUTER JOIN R_Status r ON req.R_Status_ID = r.R_Status_ID AND r.isActive = 'Y'

LEFT OUTER JOIN M_QualityNote qn ON req.M_QualityNote_ID = qn.M_QualityNote_ID


WHERE 
	--req.AD_Table_ID = get_table_ID ('M_InOut') AND
	req.isActive = 'Y'
	AND req.DateDelivered::date >= $3 AND req.DateDelivered::date <= $4
	AND (CASE WHEN $1 IS NOT NULL THEN req.AD_Org_ID = $1 ELSE TRUE END)
	AND (CASE WHEN $2 IS NOT NULL THEN req.R_RequestType_ID = $2 ELSE TRUE END)
	AND (CASE WHEN $5 IS NOT NULL THEN req.C_BPartner_ID = $5 ELSE TRUE END)
	AND (CASE WHEN $6 IS NOT NULL THEN req.M_QualityNote_ID = $6 ELSE TRUE END)
	AND (CASE WHEN $7 IS NOT NULL THEN req.performanceType = $7 ELSE TRUE END)
	AND (CASE WHEN $8 IS NOT NULL THEN req.isMaterialReturned = $8 ELSE TRUE END)
	AND (CASE WHEN $9 IS NOT NULL THEN req.R_Status_ID = $9 ELSE TRUE END)
	AND (CASE WHEN $10 IS NOT NULL THEN req.M_Product_ID = $10 ELSE TRUE END)

GROUP BY  Date, 
	BP_Value, 
	BP_Name,
	P_Value,
	P_Name,
	QualityNote,
	Performance,
	isMaterialReturned,
	lastresult,
	Status,
	p_doctype,
	p_startdate,
	p_enddate,
	p_bpartner,
	p_qualitynote,
	p_performancetype,
	p_ismaterialreturned,
	p_status,
	p_product

ORDER BY req.DateDelivered::Date, 
	bp.Value, 
	bp.Name,
	IO_Docno,
	p.Value,
	p.Name
	
$$
LANGUAGE sql STABLE;	
