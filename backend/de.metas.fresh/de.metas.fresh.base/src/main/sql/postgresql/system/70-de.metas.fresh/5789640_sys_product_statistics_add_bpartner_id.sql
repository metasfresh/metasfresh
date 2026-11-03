-- #18: Add C_BPartner_ID to product statistics report functions
-- so that fresh_product_statistics_non0_report can join by ID instead of Value

-- Drop functions first (they depend on the table types)
DROP FUNCTION IF EXISTS report.fresh_product_statistics_non0_report(numeric, character varying, numeric, numeric, numeric, numeric, numeric, numeric, numeric, character varying);
DROP FUNCTION IF EXISTS report.fresh_product_statistics_report(numeric, character varying, numeric, numeric, numeric, numeric, numeric, numeric, character varying);
DROP FUNCTION IF EXISTS report.fresh_statistics(numeric, character varying, numeric, numeric, numeric, numeric, numeric, numeric, character varying);
DROP FUNCTION IF EXISTS report.fresh_statistics(numeric, character varying, numeric, numeric, numeric, numeric, numeric, numeric);

-- Step 1: Recreate report.fresh_statistics table with C_BPartner_ID
DROP TABLE IF EXISTS report.fresh_statistics;

CREATE TABLE report.fresh_statistics
(
  bp_name character varying,
  bp_value character varying,
  pc_name character varying,
  p_name character varying,
  p_value character varying,
  uomsymbol character varying,
  col1 date,
  col2 date,
  col3 date,
  col4 date,
  col5 date,
  col6 date,
  col7 date,
  col8 date,
  col9 date,
  col10 date,
  col11 date,
  col12 date,
  period1sum numeric,
  period2sum numeric,
  period3sum numeric,
  period4sum numeric,
  period5sum numeric,
  period6sum numeric,
  period7sum numeric,
  period8sum numeric,
  period9sum numeric,
  period10sum numeric,
  period11sum numeric,
  period12sum numeric,
  totalsum numeric,
  totalamt numeric,
  startdate text,
  enddate text,
  param_bp character varying(60),
  param_activity character varying(60),
  param_product character varying(255),
  param_product_category character varying(60),
  param_attributes character varying(255),
  ad_org_id numeric,
  iso_code char(3),
  c_bpartner_id numeric
)
WITH (
  OIDS=FALSE
);

-- Step 2: Recreate report.fresh_product_statistics_report table with C_BPartner_ID
DROP TABLE IF EXISTS report.fresh_product_statistics_report;

CREATE TABLE report.fresh_product_statistics_report
(
  bp_name character varying,
  bp_value character varying,
  pc_name character varying,
  p_name character varying,
  p_value character varying,
  UOMSymbol character varying,
  col1 date,
  col2 date,
  col3 date,
  col4 date,
  col5 date,
  col6 date,
  col7 date,
  col8 date,
  col9 date,
  col10 date,
  col11 date,
  col12 date,
  period1sum numeric,
  period2sum numeric,
  period3sum numeric,
  period4sum numeric,
  period5sum numeric,
  period6sum numeric,
  period7sum numeric,
  period8sum numeric,
  period9sum numeric,
  period10sum numeric,
  period11sum numeric,
  period12sum numeric,
  totalsum numeric,
  totalamt numeric,
  startdate text,
  enddate text,
  param_bp character varying,
  param_activity character varying,
  param_product character varying,
  param_product_category character varying,
  param_attributes character varying,
  ad_org_id numeric,
  iso_code char(3),
  unionorder integer,
  c_bpartner_id numeric
)
WITH (
  OIDS=FALSE
);

-- Step 3: The DDL files will recreate the functions on next deployment.
-- No need to inline the full function bodies here since they are in DDL.
