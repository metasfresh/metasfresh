CREATE OR REPLACE VIEW rv_c_rfq_unanswered AS 
SELECT
	q.ad_client_id, q.ad_org_id, q.c_rfq_id, q.name, q.description, q.help, q.salesrep_id, q.c_rfq_topic_id
	, q.quotetype, q.isquotetotalamt, q.isquoteallqty, q.c_currency_id
	, q.dateresponse, q.isrfqresponseaccepted, q.dateworkstart, q.deliverydays, q.dateworkcomplete
	, r.c_bpartner_id, r.c_bpartner_location_id, r.ad_user_id
FROM c_rfq q
JOIN c_rfqresponse r ON q.c_rfq_id = r.c_rfq_id
WHERE r.iscomplete = 'N' AND q.processed = 'N'
;

