CREATE OR REPLACE VIEW rv_c_rfqresponse AS 
SELECT
	q.ad_client_id, q.ad_org_id
	, q.c_rfq_id, q.c_rfq_topic_id
	, r.c_bpartner_id, r.c_bpartner_location_id, r.ad_user_id
	, r.c_rfqresponse_id
	, r.c_currency_id
	, r.dateresponse
	, r.dateworkstart, r.deliverydays, r.dateworkcomplete
	, r.price, r.ranking, r.isselfservice, r.description, r.help
	, ql.m_product_id, ql.m_attributesetinstance_id
	, ql.line, rl.dateworkstart AS linedateworkstart, rl.deliverydays AS linedeliverydays
	, rl.dateworkcomplete AS linedateworkcomplete
	, rl.description AS linedescription, rl.help AS linehelp
	, qlq.c_uom_id, qlq.qty, qlq.benchmarkprice
	, rlq.price - qlq.benchmarkprice AS benchmarkdifference
	, rlq.price AS qtyprice
	, rlq.discount
	, rlq.ranking AS qtyranking
FROM c_rfq q
JOIN c_rfqline ql ON q.c_rfq_id = ql.c_rfq_id
JOIN c_rfqlineqty qlq ON ql.c_rfqline_id = qlq.c_rfqline_id
JOIN c_rfqresponse r ON q.c_rfq_id = r.c_rfq_id
JOIN c_rfqresponseline rl ON r.c_rfqresponse_id = rl.c_rfqresponse_id AND ql.c_rfqline_id = rl.c_rfqline_id
JOIN c_rfqresponselineqty rlq ON rl.c_rfqresponseline_id = rlq.c_rfqresponseline_id AND qlq.c_rfqlineqty_id = rlq.c_rfqlineqty_id
WHERE r.iscomplete = 'Y'::bpchar AND q.processed = 'N'::bpchar;

