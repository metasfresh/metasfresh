DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description
(
	DocumentNo Character Varying,
	Count Bigint,
	MovementDate Timestamp without time zone,
	Reference Character Varying,
	PrintName Character Varying,
	bp_value Character Varying
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_Alternate_InOut_1_Aggregation_Description AS
$$
SELECT
	First_Agg( io.documentno ORDER BY io.documentno ) as documentno,
	count( io.DocumentNo ) AS count,
	MIN( io.movementdate ) as MovementDate,
	First_Agg( io.poreference )	as reference,
	First_Agg( COALESCE ( dtt.printname, dt.printname ) ) AS printname,
	First_Agg( bp.value ) AS BP_Value
FROM
	m_inout io
	INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
WHERE
	io.M_InOut_ID IN (
		SELECT 	M_InOut_ID 
		FROM	M_InOut 
		WHERE 	DocStatus = 'CO' 
			AND POReference = ( SELECT POReference FROM M_InOut WHERE M_InOut_ID = $1 )
			AND AD_Org_ID = ( SELECT AD_Org_ID FROM M_InOut WHERE M_InOut_ID = $1 )
			AND C_BPartner_ID = ( SELECT C_BPartner_ID FROM M_InOut WHERE M_InOut_ID = $1 )
			AND isActive = 'Y'
	)
$$
LANGUAGE sql STABLE
;