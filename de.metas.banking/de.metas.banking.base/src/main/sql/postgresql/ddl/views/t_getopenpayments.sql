-- View: t_getopenpayments

-- DROP VIEW t_getopenpayments;

CREATE OR REPLACE VIEW t_getopenpayments AS 
SELECT
	NULL::numeric AS ad_org_id
	, NULL::numeric AS ad_client_id
	, NULL::numeric AS c_payment_id
	, NULL::numeric AS c_bpartner_id
	, NULL::character varying AS docno
	, NULL::timestamp without time zone AS paymentdate
	, NULL::character varying AS doctype
	, NULL::character varying AS bpartnername
	, NULL::character(3) AS iso_code
	, NULL::numeric AS orig_total
	, NULL::numeric AS conv_total
	, NULL::numeric AS conv_open
	, NULL::numeric AS multiplierap
 ;

ALTER TABLE t_getopenpayments
  OWNER TO adempiere;
COMMENT ON VIEW t_getopenpayments
  IS 'Used as return type in the SQL-function getopenpayments';

