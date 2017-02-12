-- View: c_rfqresponseline_vt

-- DROP VIEW c_rfqresponseline_vt;

CREATE OR REPLACE VIEW c_rfqresponseline_vt AS 
 SELECT
 	rrl.c_rfqresponse_id, rrl.c_rfqresponseline_id, rrl.c_rfqline_id, rq.c_rfqresponselineqty_id
	, rq.c_rfqlineqty_id, rrl.ad_client_id, rrl.ad_org_id
	, rrl.isactive, rrl.created, rrl.createdby, rrl.updated, rrl.updatedby
	, l.ad_language
	, rl.line
	, rl.m_product_id
	, rl.m_attributesetinstance_id
	, COALESCE(p.name::text || productattribute(rl.m_attributesetinstance_id)::text, rl.description::text) AS name
	, CASE
            WHEN p.name IS NOT NULL THEN rl.description
            ELSE NULL::character varying
        END AS description
	, p.documentnote, p.upc, p.sku, p.value AS productvalue, rl.help, rl.dateworkstart
	, rl.deliverydays
	, q.c_uom_id
	, uom.uomsymbol
	, q.benchmarkprice
	, q.qty
	, rq.price
	, rq.discount
   FROM c_rfqresponselineqty rq
   JOIN c_rfqlineqty q ON rq.c_rfqlineqty_id = q.c_rfqlineqty_id
   JOIN c_uom uom ON q.c_uom_id = uom.c_uom_id
   JOIN c_rfqresponseline rrl ON rq.c_rfqresponseline_id = rrl.c_rfqresponseline_id
   JOIN c_rfqline rl ON rrl.c_rfqline_id = rl.c_rfqline_id
   LEFT JOIN m_product p ON rl.m_product_id = p.m_product_id
   JOIN ad_language l ON l.issystemlanguage = 'Y'::bpchar
  WHERE rq.isactive = 'Y'::bpchar AND q.isactive = 'Y'::bpchar AND rrl.isactive = 'Y'::bpchar AND rl.isactive = 'Y'::bpchar
 ;

 