--
-- CalloutOrder does hardcoded SQL and joins this table to C_BPartner using an inner join.
-- Without any record, there is no result and CalloutOrder doesn't set anything (especially not C_PaymentTerm_ID).
--
INSERT INTO C_BPartner_Stats
(
  actuallifetimevalue, -- numeric,
  ad_client_id, -- numeric(10,0) NOT NULL,
  ad_org_id, -- numeric(10,0) NOT NULL,
  c_bpartner_stats_id, -- numeric(10,0) NOT NULL,
  created, -- timestamp with time zone NOT NULL,
  createdby, -- numeric(10,0) NOT NULL,
  isactive, -- character(1) NOT NULL,
  socreditstatus, -- character(1) DEFAULT NULL::bpchar,
  so_creditused, -- numeric,
  totalopenbalance, -- numeric,
  updated, -- timestamp with time zone NOT NULL,
  updatedby, -- numeric(10,0) NOT NULL,
  c_bpartner_id -- numeric(10,0) NOT NULL,
)
SELECT
  0 as actuallifetimevalue, -- numeric,
  bp.ad_client_id, -- numeric(10,0) NOT NULL,
  bp.ad_org_id, -- numeric(10,0) NOT NULL,
  nextval('c_bpartner_stats_seq'), -- numeric(10,0) NOT NULL,
  now() as created, -- timestamp with time zone NOT NULL,
  99 as createdby, -- numeric(10,0) NOT NULL,
  'Y' as isactive, -- character(1) NOT NULL,
  'X' as socreditstatus, -- character(1) DEFAULT NULL::bpchar, X = no check
  0 as so_creditused, -- numeric,
  0 as totalopenbalance, -- numeric,
  now() as updated, -- timestamp with time zone NOT NULL,
  99 as updatedby, -- numeric(10,0) NOT NULL,
  bp.c_bpartner_id -- numeric(10,0) NOT NULL,
FROM C_BPartner bp
    LEFT JOIN C_BPartner_Stats s ON s.C_BPartner_ID=bp.C_BPartner_ID
WHERE s.C_BPartner_Stats_ID IS NULL
;