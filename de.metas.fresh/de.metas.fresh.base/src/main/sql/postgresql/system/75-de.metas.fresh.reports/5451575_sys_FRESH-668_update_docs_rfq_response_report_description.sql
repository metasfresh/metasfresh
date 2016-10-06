-- DROP FUNCTION de_metas_endcustomer_fresh_reports.docs_rfq_response_report_description(numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_rfq_response_report_description(IN record_id numeric)
  RETURNS TABLE(bp_value character varying, documentno character varying, sales_rep character varying, currentdate timestamp with time zone, dateworkstart timestamp with time zone, dateworkcomplete timestamp with time zone) AS
$$
SELECT	bp.Value AS bp_value, 
	r.documentno AS documentno, 
	u.name AS sales_rep, 
	now() AS currentdate,
	rl.dateworkstart, rl.dateworkcomplete

FROM C_RfQResponse rr
INNER JOIN C_RfQResponseLine rl ON rr.C_RfQResponse_ID = rl.C_RfQResponse_ID AND rl.isActive = 'Y'
INNER JOIN C_BPartner bp ON rl.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN C_RfQ r ON rr.C_RfQ_ID = r.C_RfQ_ID AND r.isActive = 'Y'
INNER JOIN AD_User u ON rr.AD_User_ID = u.AD_User_ID AND u.isActive = 'Y'

WHERE rr.C_RfQResponse_ID = $1 AND rr.isActive = 'Y'
LIMIT 1

$$
  LANGUAGE sql STABLE
 ;
