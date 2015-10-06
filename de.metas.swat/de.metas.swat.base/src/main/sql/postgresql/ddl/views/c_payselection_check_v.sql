-- View: c_payselection_check_v
--DROP VIEW c_payselection_check_v;
CREATE OR REPLACE VIEW c_payselection_check_v AS 
 SELECT psc.ad_client_id, psc.ad_org_id, 'de_DE'::character varying AS ad_language, psc.c_payselection_id, psc.c_payselectioncheck_id, 
 obpl.c_location_id AS org_location_id, oi.taxid, 0 AS c_doctype_id, bp.c_bpartner_id, bp.value AS bpvalue, bp.taxid AS bptaxid, bp.naics, 
 bp.duns, bpg.greeting AS bpgreeting, bp.name, bp.name2, bpartnerremitlocation(bp.c_bpartner_id) AS c_location_id, bp.referenceno, 
 bp.poreference, ps.paydate, psc.payamt, psc.payamt AS amtinwords, psc.qty, psc.paymentrule, psc.documentno
   FROM c_payselectioncheck psc
   JOIN c_payselection ps ON psc.c_payselection_id = ps.c_payselection_id
   JOIN c_bpartner bp ON psc.c_bpartner_id = bp.c_bpartner_id
   LEFT JOIN c_greeting bpg ON bp.c_greeting_id = bpg.c_greeting_id
   JOIN ad_orginfo oi ON psc.ad_org_id = oi.ad_org_id
   JOIN c_bpartner obp ON psc.ad_org_id = obp.ad_orgbp_id
   LEFT OUTER JOIN C_Bpartner_Location obpl ON obp.C_BPartner_ID = obpl.C_Bpartner_ID AND obpl.IsDefaultLocation = 'Y'; -- location from bpartner location;

ALTER TABLE c_payselection_check_v OWNER TO adempiere;
GRANT ALL ON TABLE c_payselection_check_v TO adempiere;