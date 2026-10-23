-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/getC_BPartner_Product_Details.sql
-- Sets the planner row estimate to 1 (default 1000 makes the planner JIT-compile the delivery-note query). Body unchanged.
ALTER FUNCTION de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(numeric, numeric, numeric) ROWS 1;
