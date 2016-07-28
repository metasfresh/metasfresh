--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_rfq_response_report_details_summary_invitation_without_qty(in numeric,in character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_rfq_response_report_details_summary_invitation_without_qty(IN record_id numeric,IN ad_language character varying)
  RETURNS TABLE(startBidDate timestamp without time zone, 
		endBidDate timestamp without time zone, 
		dateResponse timestamp with time zone, 
		invitationtext text) AS
$$
SELECT  r.rfq_bidstartdate AS startBidDate ,
	r.rfq_bidenddate AS endBidDate, 
	rl.dateResponse
	,COALESCE(mtt.mailtext,mt.mailtext) || E'\n\n' || COALESCE(mtt.mailtext2,COALESCE(mt.mailtext2, '') )|| E'\n\n' || COALESCE(mtt.mailtext2,COALESCE(mt.mailtext3,'')) AS invitationText
FROM C_RfQResponse rr

INNER JOIN C_RfQResponseLine rl ON rr.C_RfQResponse_ID = rl.C_RfQResponse_ID
INNER JOIN C_RfQ r ON rr.C_RfQ_ID = r.C_RfQ_ID
INNER JOIN C_RFQ_Topic t ON r.C_RFQ_Topic_ID = t.C_RFQ_Topic_ID
INNER JOIN R_MailText mt ON t.rfq_invitationwithoutqty_mailtext_id = mt.R_MailText_ID
LEFT OUTER JOIN R_MailText_Trl mtt ON mt.R_MailText_ID=mtt.R_MailText_ID and mtt.ad_language = $2
WHERE rr.C_RfQResponse_ID = $1
LIMIT 1

$$
  LANGUAGE sql STABLE;