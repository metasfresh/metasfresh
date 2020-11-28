-- View: c_rfqresponselineqty_v

-- DROP VIEW c_rfqresponselineqty_v;

CREATE OR REPLACE VIEW c_rfqresponselineqty_v AS 
 SELECT
 	rq.c_rfqresponseline_id, rq.c_rfqresponselineqty_id, rq.c_rfqlineqty_id
	, rq.ad_client_id, rq.ad_org_id, rq.isactive, rq.created, rq.createdby, rq.updated, rq.updatedby
	, 'de_DE'::character varying AS ad_language
	, q.c_uom_id
	, uom.uomsymbol
	, q.qty
	, rq.price
	, rq.discount
   FROM c_rfqresponselineqty rq
   JOIN c_rfqlineqty q ON rq.c_rfqlineqty_id = q.c_rfqlineqty_id
   JOIN c_uom uom ON q.c_uom_id = uom.c_uom_id
  WHERE rq.isactive = 'Y'::bpchar AND q.isactive = 'Y'::bpchar
 ;

