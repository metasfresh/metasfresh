
CREATE OR REPLACE VIEW rv_r_group_prospect AS 
 SELECT gp.r_group_id, gp.c_bpartner_id, gp.ad_user_id, bp.value AS bpvalue, u.phone, u.comments, gp.r_request_id, rq.salesrep_id, rq.r_status_id, rq.datenextaction, rs.value AS r_status_value, COALESCE(rs.isclosed, 'N'::bpchar) AS isclosed, COALESCE(rs.isfinalclose, 'N'::bpchar) AS isfinalclose, gp.locked, gp.lockedby, gp.lockeddate, gp.ad_client_id, gp.ad_org_id, gp.created, gp.createdby, gp.updated, gp.updatedby, gp.isactive
   FROM r_group_prospect gp
   LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = gp.c_bpartner_id
   LEFT JOIN ad_user u ON u.ad_user_id = gp.ad_user_id
   LEFT JOIN r_request rq ON rq.r_request_id = gp.r_request_id
   LEFT JOIN r_status rs ON rs.r_status_id = rq.r_status_id;
