-- View: c_rfqresponse_v
--DROP VIEW c_rfqresponse_v;
CREATE OR REPLACE VIEW c_rfqresponse_v AS 
 SELECT rr.c_rfqresponse_id, rr.c_rfq_id, rr.ad_client_id, rr.ad_org_id, rr.isactive, rr.created, rr.createdby, rr.updated, rr.updatedby, 
 'de_DE'::character varying AS ad_language, obpl.c_location_id AS org_location_id, oi.taxid, r.name, r.description, r.help, r.c_currency_id, 
 c.iso_code, r.dateresponse, r.dateworkstart, r.deliverydays, rr.c_bpartner_id, bp.name AS bpname, bp.name2 AS bpname2, rr.c_bpartner_location_id, 
 bpl.c_location_id, rr.ad_user_id, bpc.title, bpc.phone, NULLIF(bpc.name::text, bp.name::text) AS contactname
   FROM c_rfqresponse rr
   JOIN c_rfq r ON rr.c_rfq_id = r.c_rfq_id
   JOIN ad_orginfo oi ON rr.ad_org_id = oi.ad_org_id
   JOIN c_bpartner obp ON rr.ad_org_id = obp.ad_orgbp_id
   LEFT OUTER JOIN C_Bpartner_Location obpl ON obp.C_BPartner_ID = obpl.C_Bpartner_ID AND obpl.IsDefaultLocation = 'Y' -- location from bpartner location;  LEFT JOIN c_bpartner_location obpl ON obp.c_bpartner_id = obpl.c_bpartner_id
   JOIN c_currency c ON r.c_currency_id = c.c_currency_id
   JOIN c_bpartner bp ON rr.c_bpartner_id = bp.c_bpartner_id
   JOIN c_bpartner_location bpl ON rr.c_bpartner_location_id = bpl.c_bpartner_location_id
   LEFT JOIN ad_user bpc ON rr.ad_user_id = bpc.ad_user_id;


GRANT ALL ON TABLE c_rfqresponse_v TO adempiere;