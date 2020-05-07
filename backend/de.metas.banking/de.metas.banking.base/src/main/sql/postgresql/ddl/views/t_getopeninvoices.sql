-- View: t_getopeninvoices

-- DROP VIEW t_getopeninvoices;

CREATE OR REPLACE VIEW t_getopeninvoices AS 
SELECT
	NULL::numeric AS ad_org_id
	, NULL::numeric AS ad_client_id
	, NULL::numeric AS c_invoice_id
	, NULL::numeric AS c_bpartner_id
	, NULL::character(1) AS isprepayorder
	, NULL::character varying AS docno
	, NULL::timestamp without time zone AS invoicedate
	, NULL::character varying AS doctype
	, NULL::character varying AS bpartnername
	, NULL::character(3) AS iso_code
	, NULL::numeric AS orig_total
	, NULL::numeric AS conv_total
	, NULL::numeric AS conv_open
	, NULL::numeric AS discount
	, NULL::numeric AS multiplierap
	, NULL::numeric AS multiplier
	, NULL::character varying AS POReference
	, NULL::timestamp without time zone AS dateacct -- task 09643: separate transaction date form accounting date
;

COMMENT ON VIEW t_getopeninvoices
  IS 'Used as return type in the SQL-function getopeninvoices';

