-- DROP FUNCTION de_metas_endcustomer_fresh_reports.docs_rfq_response_report_root(numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_rfq_response_report_root(IN record_id numeric)
  RETURNS TABLE (ad_org_id numeric) AS
$$
SELECT rl.AD_Org_ID
FROM C_RfQResponse rr
INNER JOIN C_RfQResponseLine rl ON rr.C_RfQResponse_ID = rl.C_RfQResponse_ID AND rl.isActive = 'Y'

WHERE rr.C_RfQResponse_ID = $1 AND rr.isActive = 'Y'
LIMIT 1

$$
  LANGUAGE sql STABLE
 ;
